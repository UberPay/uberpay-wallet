package com.mygeopay.core.wallet;

import com.mygeopay.core.coins.CoinType;
import com.mygeopay.core.coins.FeePolicy;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.InsufficientMoneyException;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.ScriptException;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionConfidence;
import org.bitcoinj.core.TransactionInput;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.core.VarInt;
import org.bitcoinj.script.Script;
import org.bitcoinj.signers.LocalTransactionSigner;
import org.bitcoinj.signers.MissingSigResolutionSigner;
import org.bitcoinj.signers.TransactionSigner;
import org.bitcoinj.wallet.CoinSelection;
import org.bitcoinj.wallet.CoinSelector;
import org.bitcoinj.wallet.DecryptingKeyBag;
import org.bitcoinj.wallet.KeyBag;
import org.bitcoinj.wallet.RedeemData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import static com.mygeopay.core.Preconditions.checkArgument;
import static com.mygeopay.core.Preconditions.checkNotNull;
import static com.mygeopay.core.Preconditions.checkState;

/**
 * @author John L. Jegutanis
 */
public class TransactionCreator {
    private static final Logger log = LoggerFactory.getLogger(TransactionCreator.class);
    private final WalletAccount account;
    private final CoinType coinType;

    private final CoinSelector coinSelector = new WalletCoinSelector();
    private final ReentrantLock lock; // TODO remove

    public TransactionCreator(AbstractWallet account) {
        this.account = account;
        lock = account.lock;
        coinType = account.coinType;
    }

    public TransactionCreator(AddressWallet account) {
        this.account = account;
        lock = account.lock;
        coinType = account.coinType;
    }

    private static class FeeCalculation {
        CoinSelection bestCoinSelection;
        TransactionOutput bestChangeOutput;
    }

    /**
     * Given a spend request containing an incomplete transaction, makes it valid by adding outputs and signed inputs
     * according to the instructions in the request. The transaction in the request is modified by this method.
     *
     * @param req a SendRequest that contains the incomplete transaction and details for how to make it valid.
     * @throws org.bitcoinj.core.InsufficientMoneyException if the request could not be completed due to not enough balance.
     * @throws IllegalArgumentException                     if you try and complete the same SendRequest twice
     */
    void completeTx(SendRequest req) throws InsufficientMoneyException {
        lock.lock();
        try {
            checkArgument(!req.completed, "Given SendRequest has already been completed.");
            // Calculate the amount of value we need to import.
            Coin value = Coin.ZERO;
            for (TransactionOutput output : req.tx.getOutputs()) {
                value = value.add(output.getValue());
            }

            log.info("Completing send tx with {} outputs totalling {} (not including fees)",
                    req.tx.getOutputs().size(), value.toFriendlyString());

            // If any inputs have already been added, we don't need to get their value from wallet
            Coin totalInput = Coin.ZERO;
            for (TransactionInput input : req.tx.getInputs())
                if (input.getConnectedOutput() != null)
                    totalInput = totalInput.add(input.getConnectedOutput().getValue());
                else
                    log.warn("SendRequest transaction already has inputs but we don't know how much they are worth - they will be added to fee.");
            value = value.subtract(totalInput);

            List<TransactionInput> originalInputs = new ArrayList<>(req.tx.getInputs());

            // We need to know if we need to add an additional fee because one of our values are smaller than 0.01 BTC
            int numberOfSoftDustOutputs = 0;
            if (req.ensureMinRequiredFee && !req.emptyWallet) { // min fee checking is handled later for emptyWallet
                for (TransactionOutput output : req.tx.getOutputs())
                    if (output.getValue().compareTo(coinType.getSoftDustLimit()) < 0) {
                        if (output.getValue().compareTo(coinType.getMinNonDust()) < 0)
                            throw new org.bitcoinj.core.Wallet.DustySendRequested();
                        numberOfSoftDustOutputs++;
                    }
            }

            // Calculate a list of ALL potential candidates for spending and then ask a coin selector to provide us
            // with the actual outputs that'll be used to gather the required amount of value. In this way, users
            // can customize coin selection policies.
            //
            // Note that this code is poorly optimized: the spend candidates only alter when transactions in the wallet
            // change - it could be pre-calculated and held in RAM, and this is probably an optimization worth doing.
            LinkedList<TransactionOutput> candidates = calculateAllSpendCandidates(true);
            CoinSelection bestCoinSelection;
            TransactionOutput bestChangeOutput = null;
            if (!req.emptyWallet) {
                // This can throw InsufficientMoneyException.
                FeeCalculation feeCalculation;
                feeCalculation = calculateFee(req, value, originalInputs, numberOfSoftDustOutputs, candidates);
                bestCoinSelection = feeCalculation.bestCoinSelection;
                bestChangeOutput = feeCalculation.bestChangeOutput;
            } else {
                // We're being asked to empty the wallet. What this means is ensuring "tx" has only a single output
                // of the total value we can currently spend as determined by the selector, and then subtracting the fee.
                checkState(req.tx.getOutputs().size() == 1, "Empty wallet TX must have a single output only.");
                CoinSelector selector = req.coinSelector == null ? coinSelector : req.coinSelector;
                bestCoinSelection = selector.select(NetworkParameters.MAX_MONEY, candidates);
                candidates = null;  // Selector took ownership and might have changed candidates. Don't access again.
                req.tx.getOutput(0).setValue(bestCoinSelection.valueGathered);
                log.info("  emptying {}", bestCoinSelection.valueGathered.toFriendlyString());
            }

            for (TransactionOutput output : bestCoinSelection.gathered)
                req.tx.addInput(output);

            if (req.ensureMinRequiredFee && req.emptyWallet) {
                final Coin baseFee = req.fee == null ? Coin.ZERO : req.fee;
                final Coin feePerKb = req.feePerKb == null ? Coin.ZERO : req.feePerKb;
                // If txfee is low (exp LTC 0.0014) it displays an error, A better way is needed to catch low fee value
                // prompt user LTC fee = 0.001
                // TODO a better option to catch (low balance - fee) error
                Transaction tx = req.tx;
                if (!adjustOutputDownwardsForFee(tx, bestCoinSelection, baseFee, feePerKb))
                    throw new org.bitcoinj.core.Wallet.CouldNotAdjustDownwards();
            }

            if (bestChangeOutput != null) {
                req.tx.addOutput(bestChangeOutput);
                log.info("  with {} change", bestChangeOutput.getValue().toFriendlyString());
            }

            // Now shuffle the outputs to obfuscate which is the change.
            if (req.shuffleOutputs)
                req.tx.shuffleOutputs();

            // Now sign the inputs, thus proving that we are entitled to redeem the connected outputs.
            if (req.signInputs) {
                signTransaction(req);
            }

            // Check size.
            int size = req.tx.bitcoinSerialize().length;
            if (size > Transaction.MAX_STANDARD_TX_SIZE)
                throw new org.bitcoinj.core.Wallet.ExceededMaxTransactionSize();

            final Coin calculatedFee = req.tx.getFee();
            if (calculatedFee != null) {
                log.info("  with a fee of {} {}", calculatedFee.toFriendlyString(), coinType.getSymbol());
            }

            // Label the transaction as being self created. We can use this later to spend its change output even before
            // the transaction is confirmed. We deliberately won't bother notifying listeners here as there's not much
            // point - the user isn't interested in a confidence transition they made themselves.
            req.tx.getConfidence().setSource(TransactionConfidence.Source.SELF);
            // Label the transaction as being a user requested payment. This can be used to render GUI wallet
            // transaction lists more appropriately, especially when the wallet starts to generate transactions itself
            // for internal purposes.
            req.tx.setPurpose(Transaction.Purpose.USER_PAYMENT);
            req.completed = true;
            req.fee = calculatedFee;
            log.info("  completed: {}", req.tx);
        } finally {
            lock.unlock();
        }
    }

    /**
     * <p>Given a send request containing transaction, attempts to sign it's inputs. This method expects transaction
     * to have all necessary inputs connected or they will be ignored.</p>
     * <p>Actual signing is done by pluggable {@link org.bitcoinj.signers.LocalTransactionSigner}
     * and it's not guaranteed that transaction will be complete in the end.</p>
     */
    void signTransaction(SendRequest req) {
        lock.lock();
        try {
            Transaction tx = req.tx;
            List<TransactionInput> inputs = tx.getInputs();
            List<TransactionOutput> outputs = tx.getOutputs();
            checkState(inputs.size() > 0);
            checkState(outputs.size() > 0);

            KeyBag maybeDecryptingKeyBag = new DecryptingKeyBag(account, req.aesKey);

            int numInputs = tx.getInputs().size();
            for (int i = 0; i < numInputs; i++) {
                TransactionInput txIn = tx.getInput(i);
                if (txIn.getConnectedOutput() == null) {
                    log.warn("Missing connected output, assuming input {} is already signed.", i);
                    continue;
                }

                try {
                    // We assume if its already signed, its hopefully got a SIGHASH type that will not invalidate when
                    // we sign missing pieces (to check this would require either assuming any signatures are signing
                    // standard output types or a way to get processed signatures out of script execution)
                    txIn.getScriptSig().correctlySpends(tx, i, txIn.getConnectedOutput().getScriptPubKey());
                    log.warn("Input {} already correctly spends output, assuming SIGHASH type used will be safe and skipping signing.", i);
                    continue;
                } catch (ScriptException e) {
                    // Expected.
                }

                Script scriptPubKey = txIn.getConnectedOutput().getScriptPubKey();
                RedeemData redeemData = txIn.getConnectedRedeemData(maybeDecryptingKeyBag);
                checkNotNull(redeemData, "Transaction exists in wallet that we cannot redeem: %s", txIn.getOutpoint().getHash());
                txIn.setScriptSig(scriptPubKey.createEmptyInputScript(redeemData.keys.get(0), redeemData.redeemScript));
            }

            TransactionSigner.ProposedTransaction proposal = new TransactionSigner.ProposedTransaction(tx);
            TransactionSigner signer = new LocalTransactionSigner();
            if (!signer.signInputs(proposal, maybeDecryptingKeyBag)) {
                log.info("{} returned false for the tx", signer.getClass().getName());
            }

            // resolve missing sigs if any
            new MissingSigResolutionSigner(req.missingSigsMode).signInputs(proposal, maybeDecryptingKeyBag);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns a list of all possible outputs we could possibly spend, potentially even including immature coinbases
     * (which the protocol may forbid us from spending). In other words, return all outputs that this wallet holds
     * keys for and which are not already marked as spent.
     */
    LinkedList<TransactionOutput> calculateAllSpendCandidates(boolean excludeImmatureCoinbases) {
        lock.lock();
        try {
            LinkedList<TransactionOutput> candidates = Lists.newLinkedList();
            for (Transaction tx : Iterables.concat(account.getUnspentTransactions().values(),
                    account.getPendingTransactions().values())) {
                // Do not try and spend coinbases that were mined too recently, the protocol forbids it.
                if (excludeImmatureCoinbases && !tx.isMature()) continue;
                for (TransactionOutput output : tx.getOutputs()) {
                    if (!output.isAvailableForSpending()) continue;
                    if (!output.isMine(account)) continue;
                    candidates.add(output);
                }
            }

            // If we have pending transactions, remove from candidates any future spent outputs
            for (Transaction pendingTx : account.getPendingTransactions().values()) {
                for (TransactionInput input : pendingTx.getInputs()) {
                    Transaction tx = account.getTransactions().get(input.getOutpoint().getHash());
                    if (tx == null) continue;
                    TransactionOutput pendingSpentOutput = tx.getOutput((int) input.getOutpoint().getIndex());
                    candidates.remove(pendingSpentOutput);
                }
            }
            return candidates;
        } finally {
            lock.unlock();
        }
    }

    private FeeCalculation calculateFee(SendRequest req, Coin value, List<TransactionInput> originalInputs,
                                        int numberOfSoftDustOutputs, LinkedList<TransactionOutput> candidates) throws InsufficientMoneyException {
        checkState(lock.isHeldByCurrentThread(), "Lock is held by another thread");
        FeeCalculation result = new FeeCalculation();
        // There are 3 possibilities for what adding change might do:
        // 1) No effect
        // 2) Causes increase in fee (change < 0.01 COINS)
        // 3) Causes the transaction to have a dust output or change < fee increase (ie change will be thrown away)
        // If we get either of the last 2, we keep note of what the inputs looked like at the time and try to
        // add inputs as we go up the list (keeping track of minimum inputs for each category).  At the end, we pick
        // the best input set as the one which generates the lowest total fee.
        Coin additionalValueForNextCategory = null;
        CoinSelection selection3 = null;
        CoinSelection selection2 = null;
        TransactionOutput selection2Change = null;
        CoinSelection selection1 = null;
        TransactionOutput selection1Change = null;
        // We keep track of the last size of the transaction we calculated but only if the act of adding inputs and
        // change resulted in the size crossing a 1000 byte boundary. Otherwise it stays at zero.
        int lastCalculatedSize = 0;
        Coin valueNeeded, valueMissing = null;
        while (true) {
            resetTxInputs(req, originalInputs);

            Coin fees = req.fee == null ? Coin.ZERO : req.fee;
            if (lastCalculatedSize > 0 && coinType.getFeePolicy() == FeePolicy.FEE_PER_KB) {
                // If the size is exactly 1000 bytes then we'll over-pay, but this should be rare.
                fees = fees.add(req.feePerKb.multiply((lastCalculatedSize / 1000) + 1));
            } else {
                fees = fees.add(req.feePerKb);  // First time around the loop.
            }

            if (numberOfSoftDustOutputs > 0) {
                switch (coinType.getSoftDustPolicy()) {
                    case AT_LEAST_BASE_FEE_IF_SOFT_DUST_TXO_PRESENT:
                        if (fees.compareTo(req.feePerKb) < 0) {
                            fees = req.feePerKb;
                        }
                        break;
                    case BASE_FEE_FOR_EACH_SOFT_DUST_TXO:
                        fees = fees.add(req.feePerKb.multiply(numberOfSoftDustOutputs));
                        break;
                    case NO_POLICY:
                        break;
                    default:
                        throw new RuntimeException("Unknown soft dust policy: " + coinType.getSoftDustPolicy());
                }
            }

            valueNeeded = value.add(fees);
            if (additionalValueForNextCategory != null)
                valueNeeded = valueNeeded.add(additionalValueForNextCategory);
            Coin additionalValueSelected = additionalValueForNextCategory;

            // Of the coins we could spend, pick some that we actually will spend.
            CoinSelector selector = req.coinSelector == null ? coinSelector : req.coinSelector;
            // selector is allowed to modify candidates list.
            CoinSelection selection = selector.select(valueNeeded, new LinkedList<>(candidates));
            // Can we afford this?
            if (selection.valueGathered.compareTo(valueNeeded) < 0) {
                valueMissing = valueNeeded.subtract(selection.valueGathered);
                break;
            }
            checkState(selection.gathered.size() > 0 || originalInputs.size() > 0);

            // We keep track of an upper bound on transaction size to calculate fees that need to be added.
            // Note that the difference between the upper bound and lower bound is usually small enough that it
            // will be very rare that we pay a fee we do not need to.
            //
            // We can't be sure a selection is valid until we check fee per kb at the end, so we just store
            // them here temporarily.
            boolean eitherCategory2Or3 = false;
            boolean isCategory3 = false;

            Coin change = selection.valueGathered.subtract(valueNeeded);
            if (additionalValueSelected != null)
                change = change.add(additionalValueSelected);

            // If change is a soft dust, we will need to have at least base fee to be accepted by the network
            if (req.ensureMinRequiredFee && !change.equals(Coin.ZERO) &&
                    change.compareTo(coinType.getSoftDustLimit()) < 0) {

                switch (coinType.getSoftDustPolicy()) {
                    case AT_LEAST_BASE_FEE_IF_SOFT_DUST_TXO_PRESENT:
                        if (fees.compareTo(req.feePerKb) < 0) {
                            // This solution may fit into category 2, but it may also be category 3, we'll check that later
                            eitherCategory2Or3 = true;
                            additionalValueForNextCategory = coinType.getSoftDustLimit();
                            // If the change is smaller than the fee we want to add, this will be negative
                            change = change.subtract(req.feePerKb.subtract(fees));
                        }
                        break;
                    case BASE_FEE_FOR_EACH_SOFT_DUST_TXO:
                        // This solution may fit into category 2, but it may also be category 3, we'll check that later
                        eitherCategory2Or3 = true;
                        additionalValueForNextCategory = coinType.getSoftDustLimit();
                        // If the change is smaller than the fee we want to add, this will be negative
                        change = change.subtract(req.feePerKb);
                        break;
                    case NO_POLICY:
                        break;
                    default:
                        throw new RuntimeException("Unknown soft dust policy: " + coinType.getSoftDustPolicy());
                }
            }

            int size = 0;
            TransactionOutput changeOutput = null;
            if (change.signum() > 0) {
                // The value of the inputs is greater than what we want to send. Just like in real life then,
                // we need to take back some coins ... this is called "change". Add another output that sends the change
                // back to us. The address comes either from the request or getChangeAddress() as a default.
                Address changeAddress = req.changeAddress;
                if (changeAddress == null)
                    changeAddress = account.getChangeAddress();
                changeOutput = new TransactionOutput(coinType, req.tx, change, changeAddress);
                // If the change output would result in this transaction being rejected as dust, just drop the change and make it a fee
                if (req.ensureMinRequiredFee && coinType.getMinNonDust().compareTo(change) >= 0) {
                    // This solution definitely fits in category 3
                    isCategory3 = true;
                    additionalValueForNextCategory = req.feePerKb.add(
                            coinType.getMinNonDust().add(Coin.SATOSHI));
                } else {
                    size += changeOutput.bitcoinSerialize().length + VarInt.sizeOf(req.tx.getOutputs().size()) - VarInt.sizeOf(req.tx.getOutputs().size() - 1);
                    // This solution is either category 1 or 2
                    if (!eitherCategory2Or3) // must be category 1
                        additionalValueForNextCategory = null;
                }
            } else {
                if (eitherCategory2Or3) {
                    // This solution definitely fits in category 3 (we threw away change because it was smaller than MIN_TX_FEE)
                    isCategory3 = true;
                    additionalValueForNextCategory = req.feePerKb.add(Coin.SATOSHI);
                }
            }

            // Now add unsigned inputs for the selected coins.
            for (TransactionOutput output : selection.gathered) {
                TransactionInput input = req.tx.addInput(output);
                // If the scriptBytes don't default to none, our size calculations will be thrown off.
                checkState(input.getScriptBytes().length == 0);
            }

            // Estimate transaction size and loop again if we need more fee per kb. The serialized tx doesn't
            // include things we haven't added yet like input signatures/scripts or the change output.
            size += req.tx.bitcoinSerialize().length;
            size += estimateBytesForSigning(selection);
            if (size / 1000 > lastCalculatedSize / 1000 && req.feePerKb.signum() > 0) {
                lastCalculatedSize = size;
                // We need more fees anyway, just try again with the same additional value
                additionalValueForNextCategory = additionalValueSelected;
                continue;
            }

            if (isCategory3) {
                if (selection3 == null)
                    selection3 = selection;
            } else if (eitherCategory2Or3) {
                // If we are in selection2, we will require at least CENT additional. If we do that, there is no way
                // we can end up back here because CENT additional will always get us to 1
                checkState(selection2 == null);
                checkState(additionalValueForNextCategory.equals(coinType.getSoftDustLimit()));
                selection2 = selection;
                selection2Change = checkNotNull(changeOutput); // If we get no change in category 2, we are actually in category 3
            } else {
                // Once we get a category 1 (change kept), we should break out of the loop because we can't do better
                checkState(selection1 == null);
                checkState(additionalValueForNextCategory == null);
                selection1 = selection;
                selection1Change = changeOutput;
            }

            if (additionalValueForNextCategory != null) {
                if (additionalValueSelected != null)
                    checkState(additionalValueForNextCategory.compareTo(additionalValueSelected) > 0);
                continue;
            }
            break;
        }

        resetTxInputs(req, originalInputs);

        if (selection3 == null && selection2 == null && selection1 == null) {
            checkNotNull(valueMissing);
            log.warn("Insufficient value in wallet for send: needed {} more", valueMissing.toFriendlyString());
            throw new InsufficientMoneyException(valueMissing);
        }

        Coin lowestFee = null;
        result.bestCoinSelection = null;
        result.bestChangeOutput = null;
        if (selection1 != null) {
            if (selection1Change != null)
                lowestFee = selection1.valueGathered.subtract(selection1Change.getValue());
            else
                lowestFee = selection1.valueGathered;
            result.bestCoinSelection = selection1;
            result.bestChangeOutput = selection1Change;
        }

        if (selection2 != null) {
            Coin fee = selection2.valueGathered.subtract(checkNotNull(selection2Change).getValue());
            if (lowestFee == null || fee.compareTo(lowestFee) < 0) {
                lowestFee = fee;
                result.bestCoinSelection = selection2;
                result.bestChangeOutput = selection2Change;
            }
        }

        if (selection3 != null) {
            if (lowestFee == null || selection3.valueGathered.compareTo(lowestFee) < 0) {
                result.bestCoinSelection = selection3;
                result.bestChangeOutput = null;
            }
        }
        return result;
    }

    /**
     * Reduce the value of the first output of a transaction to pay the given feePerKb as appropriate for its size.
     */
    private boolean adjustOutputDownwardsForFee(Transaction tx, CoinSelection coinSelection, Coin baseFee, Coin feePerKb) {
        TransactionOutput output = tx.getOutput(0);
        // Check if we need additional fee due to the transaction's size
        int size = tx.bitcoinSerialize().length;
        size += estimateBytesForSigning(coinSelection);
        Coin fee;
        switch (coinType.getFeePolicy()) {
            case FEE_PER_KB:
                fee = baseFee.add(feePerKb.multiply((size / 1000) + 1));
                break;
            case FLAT_FEE:
                fee = baseFee.add(feePerKb);
                break;
            default:
                throw new RuntimeException("Unknown fee policy: " + coinType.getFeePolicy());
        }
        output.setValue(output.getValue().subtract(fee));
        // Check if we need additional fee due to the output's value
        if (output.getValue().compareTo(coinType.getSoftDustLimit()) < 0) {
            switch (coinType.getSoftDustPolicy()) {
                case AT_LEAST_BASE_FEE_IF_SOFT_DUST_TXO_PRESENT:
                    if (fee.compareTo(feePerKb) < 0) {
                        output.setValue(output.getValue().subtract(feePerKb.subtract(fee)));
                    }
                    break;
                case BASE_FEE_FOR_EACH_SOFT_DUST_TXO:
                    output.setValue(output.getValue().subtract(feePerKb));
                    break;
                case NO_POLICY:
                    break;
                default:
                    throw new RuntimeException("Unknown soft dust policy: " + coinType.getSoftDustPolicy());
            }
        }

        return coinType.getMinNonDust().compareTo(output.getValue()) <= 0;
    }

    private int estimateBytesForSigning(CoinSelection selection) {
        int size = 0;
        for (TransactionOutput output : selection.gathered) {
            try {
                Script script = output.getScriptPubKey();
                ECKey key = null;
                Script redeemScript = null;
                if (script.isSentToAddress()) {
                    key = account.findKeyFromPubHash(script.getPubKeyHash());
                    if (key == null) {
                        log.error("output.getIndex {}", output.getIndex());
                        log.error("output.getAddressFromP2SH {}", output.getAddressFromP2SH(coinType));
                        log.error("output.getAddressFromP2PKHScript {}", output.getAddressFromP2PKHScript(coinType));
                        log.error("output.getParentTransaction().getHash() {}", output.getParentTransaction().getHash());
                    }
                    checkNotNull(key, "Coin selection includes unspendable outputs");
                } else if (script.isPayToScriptHash()) {
                    throw new ScriptException("Wallet does not currently support PayToScriptHash");
//                    redeemScript = keychain.findRedeemScriptFromPubHash(script.getPubKeyHash());
//                    checkNotNull(redeemScript, "Coin selection includes unspendable outputs");
                }
                size += script.getNumberOfBytesRequiredToSpend(key, redeemScript);
            } catch (ScriptException e) {
                // If this happens it means an output script in a wallet tx could not be understood. That should never
                // happen, if it does it means the wallet has got into an inconsistent state.
                throw new IllegalStateException(e);
            }
        }
        return size;
    }

    private static void resetTxInputs(SendRequest req, List<TransactionInput> originalInputs) {
        req.tx.clearInputs();
        for (TransactionInput input : originalInputs)
            req.tx.addInput(input);
    }
}

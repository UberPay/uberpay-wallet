package com.mygeopay.core.network.interfaces;

import com.mygeopay.core.network.AddressStatus;
import com.mygeopay.core.network.BlockHeader;
import com.mygeopay.core.network.ServerClient;
import org.bitcoinj.core.Transaction;

import java.util.List;

/**
 * @author John L. Jegutanis
 */
public interface TransactionEventListener {
    void onNewBlock(BlockHeader header);

    void onAddressStatusUpdate(AddressStatus status);

//    void onUnspentTransactionUpdate(AddressStatus status, List<ServerClient.UnspentTx> unspentTxes);

    void onTransactionHistory(AddressStatus status, List<ServerClient.HistoryTx> historyTxes);

    void onTransactionUpdate(Transaction tx);

    void onTransactionBroadcast(Transaction transaction);

    void onTransactionBroadcastError(Transaction tx);
}

package com.mygeopay.core.coins;

import com.mygeopay.core.coins.families.BitFamily;

import org.bitcoinj.core.Coin;

/**
 * @author John L. Jegutanis
 */
public class DogecoinDarkMain extends CoinType {
    private DogecoinDarkMain() {
        id = "dogecoindark.main";

        addressHeader = 30;
        p2shHeader = 33;
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader };
        dumpedPrivateKeyHeader = 158;
        spendableCoinbaseDepth = 500; // COINBASE_MATURITY_NEW

        family = BitFamily.get();
        name = "DOGED (α)";
        fullname = "DogecoinDark";
        symbol = "DOGED";
        uriScheme = "dogecoindark";
        bip44Index = 77;
        unitExponent = 6;
        feePerKb = value(100000L);
        minNonDust = value(1000);
        softDustLimit = value(1000000L); // TODO Verify values 1 DOGED
        softDustPolicy = SoftDustPolicy.BASE_FEE_FOR_EACH_SOFT_DUST_TXO;
    }

    private static DogecoinDarkMain instance = new DogecoinDarkMain();
    public static synchronized DogecoinDarkMain get() {
        return instance;
    }
}

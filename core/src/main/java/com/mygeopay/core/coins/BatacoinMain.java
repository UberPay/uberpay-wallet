package com.mygeopay.core.coins;

import com.mygeopay.core.coins.families.BitFamily;


/**
 * @author John L. Jegutanis
 */
public class BatacoinMain extends CoinType {
    private BatacoinMain() {
        id = "Batacoin.main";

        addressHeader = 25; // public key address
        p2shHeader = 5;    // script address
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader };
        spendableCoinbaseDepth = 60;
        dumpedPrivateKeyHeader = 153;

        family = BitFamily.get();
        name = "BATA (β)";
        fullname = "Batacoin";
        symbol = "BTA";
        uriScheme = "batacoin";
        bip44Index = 2;
        unitExponent = 8;
        feePerKb = value(1000);
        minNonDust = value(0); // 0.00000 Not used
        softDustLimit = value(1); // 0.001 LTC
        softDustPolicy = SoftDustPolicy.BASE_FEE_FOR_EACH_SOFT_DUST_TXO;
    }

    private static BatacoinMain instance = new BatacoinMain();
    public static synchronized BatacoinMain get() {
        return instance;
    }
}

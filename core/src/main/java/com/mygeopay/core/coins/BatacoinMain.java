package com.mygeopay.core.coins;


import com.mygeopay.core.coins.families.PeerFamily;


/**
 * @author Geopay.me
 */
public class BatacoinMain extends CoinType {
    private BatacoinMain() {
        id = "Batacoin.main";

        addressHeader = 25; // public key address
        p2shHeader = 5;    // script address
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader };
        spendableCoinbaseDepth = 60;
        dumpedPrivateKeyHeader = 153;

        family = PeerFamily.get();
        name = "BATA (α)";
        fullname = "Bata Money";
        symbol = "BTA";
        uriScheme = "batacoin"; // TODO confirm batacoin uriScheme
        bip44Index = 112;
        unitExponent = 8;
        feePerKb = value(100000);
        minNonDust = value(1000); // 0.00001 Not used
        softDustLimit = value(100000); // 0.001 LTC
        softDustPolicy = SoftDustPolicy.BASE_FEE_FOR_EACH_SOFT_DUST_TXO;
    }

    private static BatacoinMain instance = new BatacoinMain();
    public static synchronized BatacoinMain get() {
        return instance;
    }
}

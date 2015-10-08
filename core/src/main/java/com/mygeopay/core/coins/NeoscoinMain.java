package com.mygeopay.core.coins;

import com.mygeopay.core.coins.families.BitFamily;

/**
 * @author John L. Jegutanis
 */
public class NeoscoinMain extends CoinType {
    private NeoscoinMain() {
        id = "neoscoin.main";

        addressHeader = 53;
        p2shHeader = 5;
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader };
        spendableCoinbaseDepth = 100;
        dumpedPrivateKeyHeader = 128;

        family = BitFamily.get();
        name = "NEOS (β)";
        fullname = "Neoscoin";
        symbol = "NEOS";
        uriScheme = "neoscoin";
        bip44Index = 25;
        unitExponent = 8;
        feePerKb = value(10000);
        minNonDust = value(5460);
        softDustLimit = value(1000000); // 0.01 NEOS
        softDustPolicy = SoftDustPolicy.AT_LEAST_BASE_FEE_IF_SOFT_DUST_TXO_PRESENT;
    }

    private static NeoscoinMain instance = new NeoscoinMain();
    public static synchronized NeoscoinMain get() {
        return instance;
    }
}

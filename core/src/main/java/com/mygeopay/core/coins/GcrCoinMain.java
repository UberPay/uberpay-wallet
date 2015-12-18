package com.mygeopay.core.coins;

import com.mygeopay.core.coins.families.PeerFamily;

public class GcrCoinMain extends CoinType {
    private GcrCoinMain() {
        id = "gcrcoin.main";

        addressHeader = 38;
        p2shHeader = 97;
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader };
        spendableCoinbaseDepth = 30;

        family = PeerFamily.get();
        name = "GCR (α)";
        fullname = "GcrCoin";
        symbol = "GCR";
        uriScheme = "gcr";
        bip44Index = 49;
        unitExponent = 8;
        feePerKb = value(10000);
        minNonDust = value(1);
        softDustLimit = value(10000);
        softDustPolicy = SoftDustPolicy.AT_LEAST_BASE_FEE_IF_SOFT_DUST_TXO_PRESENT;
        signedMessageHeader = toBytes("GCR Signed Message:\n");
    }

    private static GcrCoinMain instance = new GcrCoinMain();
    public static synchronized GcrCoinMain get() {
        return instance;
    }
}

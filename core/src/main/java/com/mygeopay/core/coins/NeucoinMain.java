package com.mygeopay.core.coins;

import com.mygeopay.core.coins.families.PeerFamily;

/**
 * @author John L. Jegutanis
 */
public class NeucoinMain extends CoinType {
    private NeucoinMain() {
        id = "neucoin.main";

        addressHeader = 53;
        p2shHeader = 112;
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader };
        spendableCoinbaseDepth = 500;

        family = PeerFamily.get();
        name = "NEU (α)";
        fullname = "Neucoin";
        symbol = "NEU";
        uriScheme = "neucoin"; // TODO verify, could be ppcoin?
        bip44Index = 118;
        unitExponent = 6;
        feePerKb = value(10000); // 0.01PPC, careful Peercoin has 1000000 units per coin
        minNonDust = value(10000); // 0.01PPC
        softDustLimit = minNonDust;
        softDustPolicy = SoftDustPolicy.NO_POLICY;
    }

    private static NeucoinMain instance = new NeucoinMain();
    public static synchronized NeucoinMain get() {
        return instance;
    }
}

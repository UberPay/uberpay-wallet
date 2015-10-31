package com.mygeopay.core.coins;

import com.mygeopay.core.coins.families.PeerFamily;

/**
 * @author Thomas König
 */
public class FairCoinMain extends CoinType {
    private FairCoinMain() {
        id = "faircoin.main";

        addressHeader = 95;
        p2shHeader = 37;
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader };
        spendableCoinbaseDepth = 500;

        family = PeerFamily.get();
        name = "FAIR (α)";
        fullname = "FairCoin";
        symbol = "FAIR";
        uriScheme = "faircoin";
        bip44Index = 114;
        unitExponent = 6;
        feePerKb = value(1000); // 0.001 FAIR
        minNonDust = value(1000); // 0.001 FAIR
        softDustLimit = minNonDust;
        softDustPolicy = SoftDustPolicy.NO_POLICY;
    }

    private static FairCoinMain instance = new FairCoinMain();
    public static synchronized FairCoinMain get() {
        return instance;
    }
}

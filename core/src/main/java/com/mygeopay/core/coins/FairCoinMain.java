package com.mygeopay.core.coins;

import com.mygeopay.core.coins.families.PeerFamily;

/**
 * @author Adonis Valamontes
 */
public class FaircoinMain extends CoinType {
    private FaircoinMain() {
        id = "faircoin.main";

        addressHeader = 95;
        p2shHeader = 36;
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader };
        spendableCoinbaseDepth = 500;

        family = PeerFamily.get();
        name = "FAIR (α)";
        fullname = "FairCoin";
        symbol = "FAIR";
        uriScheme = "faircoin"; //
        bip44Index = 114;
        unitExponent = 6;
        feePerKb = value(10000); // 0.01
        minNonDust = value(10000); // 0.01
        softDustLimit = minNonDust;
        softDustPolicy = SoftDustPolicy.NO_POLICY;
    }

    private static FaircoinMain instance = new FaircoinMain();
    public static synchronized FaircoinMain get() {
        return instance;
    }
}

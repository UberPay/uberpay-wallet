package com.mygeopay.core.coins;

import com.mygeopay.core.coins.families.PeerFamily;

/**
 * @author Geopayme
 */
public class AsiacoinMain extends CoinType {
    private AsiacoinMain() {
        id = "asiacoin.main";

        addressHeader = 23;
        p2shHeader = 8;
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader };
        spendableCoinbaseDepth = 520;

        family = PeerFamily.get();
        name = "AC (α)";
        fullname = "AsiaCoin";
        symbol = "AC";
        uriScheme = "asiacoin"; // TODO verify, could be ppcoin?
        bip44Index = 51;
        unitExponent = 6;
        feePerKb = value(1000); // 0.001 AC
        minNonDust = value(1);
        softDustLimit = value(10000); // 0.0001 AC
        softDustPolicy = SoftDustPolicy.BASE_FEE_FOR_EACH_SOFT_DUST_TXO;
    }

    private static AsiacoinMain instance = new AsiacoinMain();
    public static synchronized AsiacoinMain get() {
        return instance;
    }
}

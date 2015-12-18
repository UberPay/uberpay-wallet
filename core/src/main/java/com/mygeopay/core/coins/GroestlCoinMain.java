package com.mygeopay.core.coins;

import com.mygeopay.core.coins.families.GroestlFamily;


/**
 * @author Geopay.me
 */
public class GroestlCoinMain extends CoinType {
    private GroestlCoinMain() {
        id = "groestlcoin.main";

        addressHeader = 36;
        p2shHeader = 5;
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader };
        spendableCoinbaseDepth = 120;

        family = GroestlFamily.get();
        name = "GRS (α)";
        fullname = "GroestlCoin";
        symbol = "GRS";
        uriScheme = "groestlcoin";
        bip44Index = 17;
        unitExponent = 8;
        feePerKb = value(10000);
        minNonDust = value(1000); // 0.00001 GRS mininput
        softDustLimit = value(10000); // 0.00001 GRS
        softDustPolicy = SoftDustPolicy.BASE_FEE_FOR_EACH_SOFT_DUST_TXO;
    }

    private static GroestlCoinMain instance = new GroestlCoinMain();
    public static synchronized GroestlCoinMain get() {
        return instance;
    }
}

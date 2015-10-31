package com.mygeopay.core.coins;

import com.mygeopay.core.coins.families.BitFamily;

/**
 *
 * @author Adonis Valamontes
 */
public class MazacoinMain extends CoinType {
    private MazacoinMain() {
        id = "mazacoin.main"; // Do not change this id as wallets serialize this string

        addressHeader = 50;
        p2shHeader = 9;
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader };
        spendableCoinbaseDepth = 100;
        dumpedPrivateKeyHeader = 224;

        family = BitFamily.get();
        name = "MZC (β)";
        fullname = "Mazacoin";
        symbol = "MZC";
        uriScheme = "mazacoin";
        bip44Index = 13;
        unitExponent = 8;
        feePerKb = value(100000);
        minNonDust = value(1000); // 0.00001 MAZACOIN mininput
        softDustLimit = value(100000); // 0.001 MZC
        softDustPolicy = SoftDustPolicy.BASE_FEE_FOR_EACH_SOFT_DUST_TXO;
    }

    private static MazacoinMain instance = new MazacoinMain();
    public static synchronized MazacoinMain get() {
        return instance;
    }
}

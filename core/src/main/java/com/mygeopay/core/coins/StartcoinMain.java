package com.mygeopay.core.coins;

import com.mygeopay.core.coins.families.BitFamily;

/**
 *
 * @author Adonis Valamontes
 */
public class StartcoinMain extends CoinType {
    private StartcoinMain() {
        id = "startcoin.main"; // Do not change this id as wallets serialize this string

        addressHeader = 125;
        p2shHeader = 5;
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader };
        spendableCoinbaseDepth = 100;

        family = BitFamily.get();
        name = "START (α)";
        symbol = "START";
        uriScheme = "startcoin";
        bip44Index = 253;
        unitExponent = 8;
        feePerKb = value(100000);
        minNonDust = value(1000); // 0.00001 MAZACOIN mininput
        softDustLimit = value(100000); // 0.001 MZC
        softDustPolicy = SoftDustPolicy.BASE_FEE_FOR_EACH_SOFT_DUST_TXO;
    }

    private static StartcoinMain instance = new StartcoinMain();
    public static synchronized StartcoinMain get() {
        return instance;
    }
}

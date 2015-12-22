package com.mygeopay.core.coins;

import com.mygeopay.core.coins.families.BitFamily;

/**
 * @author Geopay.me
 */
public class BolivarcoinMain extends CoinType {
    private BolivarcoinMain() {
        id = "bolivarcoin.main";

        addressHeader = 85; // public key address
        p2shHeader = 5;    // script address
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader };
        spendableCoinbaseDepth = 100;
        dumpedPrivateKeyHeader = 213;

        family = BitFamily.get();
        name = "BOLI (α)";
        fullname = "Bolivar";
        symbol = "BOLI";
        uriScheme = "bolivarcoin";
        bip44Index = 114;
        unitExponent = 8;
        feePerKb = value(10000);
        minNonDust = value(5460);
        softDustLimit = value(1000000); // 0.01 BTC
        softDustPolicy = SoftDustPolicy.AT_LEAST_BASE_FEE_IF_SOFT_DUST_TXO_PRESENT;
    }

    private static BolivarcoinMain instance = new BolivarcoinMain();
    public static synchronized BolivarcoinMain get() {
        return instance;
    }
}

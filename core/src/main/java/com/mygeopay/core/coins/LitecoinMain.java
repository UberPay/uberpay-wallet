package com.mygeopay.core.coins;

import com.mygeopay.core.coins.families.BitFamily;

/**
 * @author John L. Jegutanis
 */
public class LitecoinMain extends CoinType {
    private LitecoinMain() {
        id = "litecoin.main";

        addressHeader = 48; // public key address
        p2shHeader = 5;    // script address
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader };
        spendableCoinbaseDepth = 100;
        dumpedPrivateKeyHeader = 176;

        family = BitFamily.get();
        name = "LTC";
        fullname = "Litecoin";
        symbol = "LTC";
        uriScheme = "litecoin";
        bip44Index = 2;
        unitExponent = 8;
        feePerKb = value(100000);
        minNonDust = value(1000); // 0.00001 LTC mininput
        softDustLimit = value(100000); // 0.001 LTC
        softDustPolicy = SoftDustPolicy.BASE_FEE_FOR_EACH_SOFT_DUST_TXO;
    }

    private static LitecoinMain instance = new LitecoinMain();
    public static synchronized LitecoinMain get() {
        return instance;
    }
}

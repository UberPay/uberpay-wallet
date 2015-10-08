package com.mygeopay.core.coins;

import com.mygeopay.core.coins.families.BitFamily;

/**
 * @author John L. Jegutanis
 */
public class DogecoinDarkMain extends CoinType {
    private DogecoinDarkMain() {
        id = "dogecoindark.main";

        addressHeader = 30;
        p2shHeader = 33;
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader };
        spendableCoinbaseDepth = 158; // COINBASE_MATURITY_NEW

        family = BitFamily.get();
        name = "DOGED (α)";
        fullname = "DogecoinDark";
        symbol = "DOGED";
        uriScheme = "dogecoindark";
        bip44Index = 77;
        unitExponent = 8;
        feePerKb = value(100000000);
        minNonDust = value(1);
        softDustLimit = value(10000000); // TODO Verify values
        softDustPolicy = SoftDustPolicy.BASE_FEE_FOR_EACH_SOFT_DUST_TXO;
    }

    private static DogecoinDarkMain instance = new DogecoinDarkMain();
    public static synchronized DogecoinDarkMain get() {
        return instance;
    }
}

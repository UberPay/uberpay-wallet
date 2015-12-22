package com.mygeopay.core.coins;

import com.mygeopay.core.coins.families.BitFamily;
import com.mygeopay.core.coins.families.PeerFamily;

import org.bitcoinj.core.Coin;

/**
 * @author Geopay.me
 */
public class DogecoinDarkMain extends CoinType {
    private DogecoinDarkMain() {
        id = "dogecoindark.main";

        addressHeader = 30;
        p2shHeader = 33;
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader };
        dumpedPrivateKeyHeader = 158;
        spendableCoinbaseDepth = 500; // COINBASE_MATURITY_NEW

        family = PeerFamily.get();
        name = "DOGED (β)";
        fullname = "DogecoinDark";
        symbol = "DOGED";
        uriScheme = "dogecoindark";
        bip44Index = 77;
        unitExponent = 6; // value displays correct digits [2.014602] / [0.02146] only when 6 not 8 ??
        feePerKb = value(100000L);
        minNonDust = value(1000);
        softDustLimit = value(1000000L); // TODO Verify values 1 DOGED
        softDustPolicy = SoftDustPolicy.BASE_FEE_FOR_EACH_SOFT_DUST_TXO;
    }

    private static DogecoinDarkMain instance = new DogecoinDarkMain();
    public static synchronized DogecoinDarkMain get() {
        return instance;
    }
}

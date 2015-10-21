package com.mygeopay.core.coins;

import com.mygeopay.core.coins.families.PeerFamily;

/**
 * @author John L. Jegutanis
 */
public class ClamcoinMain extends CoinType {
    private ClamcoinMain() {
        id = "clamcoin.main";

        addressHeader = 137;
        p2shHeader = 5;
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader };
        spendableCoinbaseDepth = 265; // COINBASE_MATURITY_NEW

        family = PeerFamily.get();
        name = "CLAM (α)";
        fullname = "Clam";
        symbol = "CLAM";
        uriScheme = "clam";
        bip44Index = 23;
        unitExponent = 8;
        feePerKb = value(10000);
        minNonDust = value(1);
        softDustLimit = value(10000000); // TODO Verify values
        softDustPolicy = SoftDustPolicy.BASE_FEE_FOR_EACH_SOFT_DUST_TXO;
    }

    private static ClamcoinMain instance = new ClamcoinMain();
    public static synchronized ClamcoinMain get() {
        return instance;
    }
}

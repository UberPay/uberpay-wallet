package com.mygeopay.core.coins;

import com.mygeopay.core.coins.families.ClamsFamily;

/**
 * @author Geopay.me
 */
public class ClamcoinMain extends CoinType {
    private ClamcoinMain() {
        id = "clamcoin.main";

        addressHeader = 137;
        p2shHeader = 5;
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader };
        spendableCoinbaseDepth = 500;
        transactionVersion =2;

        family = ClamsFamily.get();
        name = "CLAM (α)";
        fullname = "Clam";
        symbol = "CLAM";
        uriScheme = "clams";
        bip44Index = 23;
        unitExponent = 8;
        feePerKb = value(10000);
        minNonDust = value(1);
        softDustLimit = value(1000000); // TODO Verify values
        softDustPolicy = SoftDustPolicy.BASE_FEE_FOR_EACH_SOFT_DUST_TXO;
        signedMessageHeader = toBytes("Clam Signed Message:\n");
    }

    private static ClamcoinMain instance = new ClamcoinMain();
    public static synchronized ClamcoinMain get() {
        return instance;
    }
}

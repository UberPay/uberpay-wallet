package com.mygeopay.core.coins;

import com.mygeopay.core.coins.families.PeerFamily;

/**
 * @author Geopay
 */
public class OkCashMain extends CoinType {
    private OkCashMain() {
        id = "okcash.main";

        addressHeader = 55;
        p2shHeader = 28;
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader };
        spendableCoinbaseDepth = 500;

        family = PeerFamily.get();
        name = "OK (α)";
        fullname = "OKCash";
        symbol = "OK";
        uriScheme = "okcash"; //
        bip44Index = 69;
        unitExponent = 8;
        feePerKb = value(10000); // 0.0001OK,
        minNonDust = value(1); //
        softDustLimit = value(1000000); // 0.01OK;
        softDustPolicy = SoftDustPolicy.NO_POLICY;
        signedMessageHeader = toBytes("OKCash Signed Message:\n");
    }

    private static OkCashMain instance = new OkCashMain();
    public static synchronized OkCashMain get() {
        return instance;
    }
}

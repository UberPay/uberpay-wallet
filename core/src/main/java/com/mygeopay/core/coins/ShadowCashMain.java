package com.mygeopay.core.coins;

import com.mygeopay.core.coins.families.BitFamily;
import com.mygeopay.core.coins.families.PeerFamily;

/**
 * @author John L. Jegutanis
 */
public class ShadowCashMain extends CoinType {
    private ShadowCashMain() {
        id = "shadowcash.main";

        addressHeader = 63;
        p2shHeader = 125;
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader };
        spendableCoinbaseDepth = 500;

        family = PeerFamily.get();
        name = "SDC (β)"; // The original name is "ShadowCash"
        fullname = "ShadowCash";
        symbol = "SDC";
        uriScheme = "shadowcash";
        bip44Index = 35;
        unitExponent = 8;
        feePerKb = value(10000); // 0.0001 SDC
        minNonDust = value(1);
        softDustLimit = value(1000000); // 0.01 SDC
        softDustPolicy = SoftDustPolicy.AT_LEAST_BASE_FEE_IF_SOFT_DUST_TXO_PRESENT;
        signedMessageHeader = toBytes("ShadowCash Signed Message:\n");
    }

    private static ShadowCashMain instance = new ShadowCashMain();
    public static synchronized ShadowCashMain get() {
        return instance;
    }
}

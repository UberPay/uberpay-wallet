package com.mygeopay.core.coins;

import com.mygeopay.core.coins.families.BitFamily;
import com.mygeopay.core.coins.families.PeerFamily;

/**
 * @author John L. Jegutanis
 */
public class StepscoinMain extends CoinType {
    private StepscoinMain() {
        id = "stepscoin.main";

        addressHeader = 63; // public key address
        p2shHeader = 85;    // script address
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader };
        spendableCoinbaseDepth = 100;
        // dumpedPrivateKeyHeader = 128;

        family = PeerFamily.get();
        name = "Steps (β)";
        fullname = "Steps";
        symbol = "STEPS";
        uriScheme = "steps";
        bip44Index = 113;
        unitExponent = 8;
        feePerKb = value(100000);
        minNonDust = value(1000000);
        softDustLimit = value(1000000); // 0.01 BTC
        softDustPolicy = SoftDustPolicy.AT_LEAST_BASE_FEE_IF_SOFT_DUST_TXO_PRESENT;
    }

    private static StepscoinMain instance = new StepscoinMain();
    public static synchronized StepscoinMain get() {
        return instance;
    }
}

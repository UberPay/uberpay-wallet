package com.mygeopay.core.coins;

import com.mygeopay.core.coins.families.PeerFamily;

/**
 * @author John L. Jegutanis
 */
public class EuropecoinMain extends CoinType {
    private EuropecoinMain() {
        id = "europecoin.main";

        addressHeader = 33;
        p2shHeader = 5;
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader };
        spendableCoinbaseDepth = 500;

        family = PeerFamily.get();
        name = "ERC (α)";
        fullname = "EuropeCoin";
        symbol = "ERC";
        uriScheme = "europecoin"; // TODO verify
        bip44Index = 116;
        unitExponent = 8;
        feePerKb = value(100000); // 0.00001
        minNonDust = value(1000); // 0.001
        softDustLimit = value(100000);
        softDustPolicy = SoftDustPolicy.BASE_FEE_FOR_EACH_SOFT_DUST_TXO;
    }

    private static EuropecoinMain instance = new EuropecoinMain();
    public static synchronized EuropecoinMain get() {
        return instance;
    }
}

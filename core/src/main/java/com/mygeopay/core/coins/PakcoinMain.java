package com.mygeopay.core.coins;

import com.mygeopay.core.coins.families.BitFamily;

/**
 * @author John L. Jegutanis
 */
public class PakcoinMain extends CoinType {
    private PakcoinMain() {
        id = "pakcoin.main";

        addressHeader = 55; // public key address
        p2shHeader = 5;    // script address
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader };
        spendableCoinbaseDepth = 100;
        dumpedPrivateKeyHeader = 176;

        family = BitFamily.get();
        name = "PAK (α)";
        fullname = "Pakcoin";
        symbol = "PAK";
        uriScheme = "pakcoin";
        bip44Index = 120; // TODO Need confirmation in Satoshi Labs
        unitExponent = 8;
        feePerKb = value(100000);
        minNonDust = value(1000);
        softDustLimit = value(100000); // 0.001 PAK
        softDustPolicy = SoftDustPolicy.AT_LEAST_BASE_FEE_IF_SOFT_DUST_TXO_PRESENT; // Fee adjusts according to TX size
    }

    private static PakcoinMain instance = new PakcoinMain();
    public static synchronized PakcoinMain get() {
        return instance;
    }
}

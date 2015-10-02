package com.mygeopay.core.coins;

import com.mygeopay.core.coins.families.BitFamily;

/**
 *
 * @author Adonis Valamontes
 */
public class ViacoinMain extends CoinType {
    private ViacoinMain() {
        id = "viacoin.main"; // Do not change this id as wallets serialize this string


        addressHeader = 71; // P2PKSH VERSION
        p2shHeader = 33;    // P2SH VERSION
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader };
        spendableCoinbaseDepth = 3600; // COINBASE MATURITY
        dumpedPrivateKeyHeader = 199; // TODO check value (128 + 71)

        family = BitFamily.get();
        name = "VIA (β)";
        symbol = "VIA";
        uriScheme = "viacoin";
        bip44Index = 14; // BIP0044 COIN TYPE 0x8000000e
        unitExponent = 8;
        feePerKb = value(100000); // MIN RELAY TX FEE
        minNonDust = value(56000); // 0.000056
        softDustLimit = value(10000); // 0.00001
        softDustPolicy = SoftDustPolicy.BASE_FEE_FOR_EACH_SOFT_DUST_TXO;
    }

    private static ViacoinMain instance = new ViacoinMain();
    public static synchronized ViacoinMain get() {
        return instance;
    }
}

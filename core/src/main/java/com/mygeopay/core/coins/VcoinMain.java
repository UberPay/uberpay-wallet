package com.mygeopay.core.coins;

import com.mygeopay.core.coins.families.BitFamily;

/**
 *
 * @author Adonis Valamontes
 */
public class VcoinMain extends CoinType {
    private VcoinMain() {
        id = "vcoin.main"; // Do not change this id as wallets serialize this string

        addressHeader = 70; // public key address
        p2shHeader = 30;   // script address
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader };
        spendableCoinbaseDepth = 100;
        dumpedPrivateKeyHeader = 198; // TODO check value (70 + 128)= 198 / 224?

        family = BitFamily.get();
        name = "VCN (α)";
        fullname = "VCoin";
        symbol = "VCN";
        uriScheme = "vcoin";
        bip44Index = 37;  // TODO BIP0044 Coin Type ? = 0x80000025
        unitExponent = 8;
        feePerKb = value(10000);
        minNonDust = value(1000); // 0.00001
        softDustLimit = value(100000); // 0.001
        softDustPolicy = SoftDustPolicy.BASE_FEE_FOR_EACH_SOFT_DUST_TXO;
    }

    private static VcoinMain instance = new VcoinMain();
    public static synchronized VcoinMain get() {
        return instance;
    }
}

package com.mygeopay.core.coins;

import com.mygeopay.core.coins.families.BitFamily;

import org.bitcoinj.core.Coin;

/**
 * @author John L. Jegutanis
 */
public class CryptoescudoMain extends CoinType {
    private CryptoescudoMain() {
        id = "cryptoescudo.main";

        addressHeader = 28;
        p2shHeader = 88;
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader };
        spendableCoinbaseDepth = 40; // COINBASE_MATURITY = 40 - cryptoescudo/src/main.h
        dumpedPrivateKeyHeader = 156; // 128 + addressHeader[28] = 156

        family = BitFamily.get();
        name = "CESC (α)"; // A Portuguese Cryptocoin
        fullname = "CryptoEscudo";
        symbol = "CESC";
        uriScheme = "cryptoescudo";
        bip44Index = 111; // TODO Need confirmation in Satoshi Labs
        unitExponent = 8;
        feePerKb = value(100000000L); // 0 Zerado para testes normal 100 000
        minNonDust = value(1000); // 0.00001 CESC mininput
        softDustLimit = value(100000000L); // 0 CESC
        softDustPolicy = SoftDustPolicy.BASE_FEE_FOR_EACH_SOFT_DUST_TXO;
    }

    private static CryptoescudoMain instance = new CryptoescudoMain();
    public static synchronized CryptoescudoMain get() {
        return instance;
    }
}

package com.mygeopay.core.coins;

import com.mygeopay.core.coins.families.BitFamily;
import com.mygeopay.core.coins.families.PeerFamily;

import org.bitcoinj.core.Coin;

/**
 * @author John L. Jegutanis
 */
public class PeercoinTest extends CoinType {
    private PeercoinTest() {
        id = "peercoin.test";

        addressHeader = 111;
        p2shHeader = 196;
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader };
        spendableCoinbaseDepth = 500;

        family = PeerFamily.get();
        name = "PPCτ";
        fullname ="PEER Testnet";
        symbol = "PPCTEST";
        uriScheme = "peercoin"; //
        bip44Index = 1;
        unitExponent = 6;
        feePerKb = value(10000); // 0.01PPC, careful Peercoin has 1000000 units per coin
        minNonDust = value(10000); // 0.01PPC
        softDustLimit = minNonDust;
        softDustPolicy = SoftDustPolicy.NO_POLICY;
    }

    private static PeercoinTest instance = new PeercoinTest();
    public static synchronized PeercoinTest get() {
        return instance;
    }
}

package com.mygeopay.core.coins;

import com.mygeopay.core.coins.families.PeerFamily;
import com.mygeopay.core.coins.families.VpncoinFamily;

/**
 * @author John L. Jegutanis
 */
public class VpncoinMain extends CoinType {
    private VpncoinMain() {
        id = "vpncoin.main";

        addressHeader = 71;
        p2shHeader = 5;
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader };
        spendableCoinbaseDepth = 100;

        family = VpncoinFamily.get();
        name = "VPN (β)";
        fullname = "Vpncoin";
        symbol = "VPN";
        uriScheme = "vpncoin";
        bip44Index = 33;
        unitExponent = 8;
        feePerKb = value(10000000); // 0.01VPN
        minNonDust = feePerKb;
        softDustLimit = feePerKb;
        softDustPolicy = SoftDustPolicy.NO_POLICY;
    }

    private static VpncoinMain instance = new VpncoinMain();
    public static synchronized VpncoinMain get() {
        return instance;
    }
}

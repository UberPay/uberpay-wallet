package com.mygeopay.core.network.interfaces;

import com.mygeopay.core.coins.CoinType;

/**
 * @author John L. Jegutanis
 */
public interface ConnectionEventListener {
    void onConnection(BlockchainConnection blockchainConnection);
    void onDisconnect();
}

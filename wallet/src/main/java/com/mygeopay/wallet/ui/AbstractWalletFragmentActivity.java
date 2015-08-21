package com.mygeopay.wallet.ui;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;

import com.mygeopay.wallet.WalletApplication;

/**
 * @author John L. Jegutanis
 */
abstract public class AbstractWalletFragmentActivity extends FragmentActivity {

    protected WalletApplication getWalletApplication() {
        return (WalletApplication) getApplication();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWalletApplication().touchLastResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getWalletApplication().touchLastStop();
    }
}

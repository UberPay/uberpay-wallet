package com.mygeopay.wallet.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.mygeopay.core.coins.Value;
import com.mygeopay.core.wallet.WalletAccount;
import com.mygeopay.wallet.Constants;
import com.mygeopay.wallet.R;

import org.bitcoinj.core.Address;

import javax.annotation.Nullable;


public class TradeStatusActivity extends BaseWalletActivity implements TradeStatusFragment.Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_status);

        if (savedInstanceState == null) {
            Fragment fragment = new TradeStatusFragment();
            fragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }

    @Override
    public void onFinish() {
        finish();
    }
}

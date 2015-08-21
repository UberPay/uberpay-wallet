package com.mygeopay.wallet.ui;

import android.os.Bundle;

import com.mygeopay.wallet.R;

/**
 * @author John L. Jegutanis
 */
public class ExchangeRatesActivity extends BaseWalletActivity {
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exchange_rates_content);

        if (savedInstanceState == null) {
            ExchangeRatesFragment fragment = new ExchangeRatesFragment();
            fragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
    }
}

package com.bblinkout.bboutandroid.activity;

import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.bblinkout.bboutandroid.R;

public class OrderConfirmationActivity extends BaseActivity {
    View orderConfirmationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderConfirmationView=this.getLayoutInflater().inflate(R.layout.activity_order_confirmation,null, true);
        drawer.addView(orderConfirmationView, 0);
        setTitle("Order Confirmation");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dafaultBackPressed();
            }
        });
    }

}

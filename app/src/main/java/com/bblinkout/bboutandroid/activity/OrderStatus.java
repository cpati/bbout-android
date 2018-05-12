package com.bblinkout.bboutandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bblinkout.bboutandroid.R;

public class OrderStatus extends BaseActivity {
    View orderStatusView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderStatusView=this.getLayoutInflater().inflate(R.layout.activity_order_status,null, true);
        drawer.addView(orderStatusView, 0);
        setTitle("Order Status");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dafaultBackPressed();
            }
        });
        TextView orderNo = (TextView)findViewById(R.id.order_num);
        TextView message = (TextView)findViewById(R.id.order_confirm);
        message.setText( getString(R.string.order_placed_message));
        Intent intent= getIntent();
        orderNo.setText(intent.getStringExtra("orderId"));
    }

}

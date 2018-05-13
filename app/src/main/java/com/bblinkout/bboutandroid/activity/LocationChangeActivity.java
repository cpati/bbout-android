package com.bblinkout.bboutandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bblinkout.bboutandroid.R;
import com.bblinkout.bboutandroid.activity.BaseActivity;

public class LocationChangeActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView textView;
    Button refresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_change);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Store Not Found");
        textView=findViewById(R.id.location_text);
        refresh=findViewById(R.id.refresh);
        textView.setText("There is no registered store at your current location, please click refresh or relaunch app when at a registered store.");
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
    }

}

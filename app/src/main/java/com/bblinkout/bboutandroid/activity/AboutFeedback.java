package com.bblinkout.bboutandroid.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bblinkout.bboutandroid.R;

public class AboutFeedback extends BaseActivity {

  Button btn_feedback;
  RatingBar ratingBar;
  EditText email;
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View cartView = this.getLayoutInflater().inflate(R.layout.about, null, true);
        drawer.addView(cartView, 0);
        setTitle("Feedback");

        btn_feedback = (Button)findViewById(R.id.btn_feedback);
        ratingBar =(RatingBar)findViewById(R.id.rb_rating);
        email=(EditText) findViewById(R.id.tx_email_feedback);


        btn_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutFeedback.this,"Thank you for Rating us "+ ratingBar.getRating(),Toast.LENGTH_LONG).show();
            }
        });
    }
}

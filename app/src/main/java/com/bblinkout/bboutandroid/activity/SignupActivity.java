package com.bblinkout.bboutandroid.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bblinkout.bboutandroid.R;

public class SignupActivity extends AppCompatActivity {

    EditText name;
    EditText address;
    EditText email;
    EditText mobile;
    EditText password;
    EditText reEnterPassword;
    Button signup;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name=(EditText)findViewById(R.id.name);
        address=(EditText)findViewById(R.id.address);
        email=(EditText)findViewById(R.id.email);
        mobile=(EditText)findViewById(R.id.mobile);
        password=(EditText)findViewById(R.id.password);
        reEnterPassword=(EditText)findViewById(R.id.reEnterPassword);
        signup=(Button)findViewById(R.id.signup);
        login=(TextView)findViewById(R.id.login);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("MYPREFS",MODE_PRIVATE);
                String newUser  = name.getText().toString();
                String newPassword = password.getText().toString();
                String newEmail = email.getText().toString();
                String newaddress = address.getText().toString();

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(newEmail + newPassword +"data", newEmail +"/n" + newUser);
                editor.commit();

                signup();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void signup() {

        if (!validate()) {
            onSignupFailed();
            return;
        }

        signup.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_PopupOverlay);

        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String nameText = name.getText().toString();
        String addressText = address.getText().toString();
        String emailText = email.getText().toString();
        String mobileText = mobile.getText().toString();
        String passwordText = password.getText().toString();
        String reEnterPasswordText = reEnterPassword.getText().toString();


        onSignupSuccess();
        progressDialog.dismiss();
        finish();
    }


    public void onSignupSuccess() {
        signup.setEnabled(true);
        Toast pass = Toast.makeText(SignupActivity.this, "Successfully signed up!", Toast.LENGTH_SHORT);
        pass.show();

    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        signup.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String nameText = name.getText().toString();
        String addressText = address.getText().toString();
        String emailText = email.getText().toString();
        String mobileText = mobile.getText().toString();
        String passwordText = password.getText().toString();
        String reEnterPasswordText = reEnterPassword.getText().toString();


        if (nameText.isEmpty() || name.length() < 3) {
            name.setError("at least 3 characters");
            valid = false;
        } else {
            name.setError(null);
        }

        if (addressText.isEmpty()) {
            address.setError("Enter Valid Address");
            valid = false;
        } else {
            address.setError(null);
        }


        if (emailText.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            email.setError("Enter a valid email address");
            valid = false;
        } else {
            email.setError(null);
        }

        if (mobileText.isEmpty() || mobileText.length()!=10) {
            mobile.setError("Enter a valid mobile number");
            valid = false;
        } else {
            mobile.setError(null);
        }

        if (passwordText.isEmpty() || passwordText.length() <6 ) {
            password.setError("Password must be at least 6 alphanumeric charaters");
            valid = false;
        } else {
            password.setError(null);
        }

        if (passwordText.isEmpty() || !(reEnterPasswordText.equals(passwordText))) {
            reEnterPassword.setError("Passwords do not match");
            valid = false;
        } else {
            reEnterPassword.setError(null);
        }

        return valid;
    }
}

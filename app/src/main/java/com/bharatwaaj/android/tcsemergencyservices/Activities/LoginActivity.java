package com.bharatwaaj.android.tcsemergencyservices.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.bharatwaaj.android.tcsemergencyservices.R;
import com.bharatwaaj.android.tcsemergencyservices.Widgets.TEditText;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends TBaseActivity implements GoogleApiClient.OnConnectionFailedListener{

    // Firebase Components
    private FirebaseAuth mAuth;

    // Google Components

    // UI Components
    private TEditText emailIdEditText;
    private TEditText passwordEditText;

    // Other Components
    private static String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();
    }

    private void initUI() {

        //TAG Setup
        TAG = getPackageName();

        // Toolbar Setup
        initToolBar();
        setToolBarTitle(getResources().getString(R.string.login_activity));

        // UI Setup
        emailIdEditText = (TEditText) findViewById(R.id.login_email_id_et);
        passwordEditText = (TEditText) findViewById(R.id.login_password_et);

        // Firebase Setup
        mAuth = setUpFirebase();
    }

    private boolean validateLogin() {
        if(emailIdEditText.getText().toString().equals("")){
            emailIdEditText.setError(getString(R.string.required));
            return false;
        }else if(passwordEditText.getText().toString().equals("")){
            passwordEditText.setError(getString(R.string.required));
            return false;
        }else {
            return true;
        }
    }

    // Button OnClicks
    public void handleLoginBtnClick(View view) {
        if(validateLogin()) {
            showProgressDialog();
            mAuth.signInWithEmailAndPassword(emailIdEditText.getText().toString(), passwordEditText.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                hideProgressDialog();
                                Toast.makeText(LoginActivity.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                            } else {
                                hideProgressDialog();
                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            }
                        }
                    });
        }
    }

    public void handleForgotPassBtnClick(View view) {
        startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));
    }

    public void handleSignUpBtnClick(View view) {
        startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

package com.bharatwaaj.android.tcsemergencyservices.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.bharatwaaj.android.tcsemergencyservices.R;
import com.bharatwaaj.android.tcsemergencyservices.Widgets.TEditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends TBaseActivity {

    // Firebase Components
    private FirebaseAuth mAuth;

    // UI Components
    private TEditText emailIdSignUpTextview;
    private TEditText passwordSignUpTextview;
    private TEditText phoneSignUpTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initUI();
    }

    private void initUI() {
        initToolBar();
        setToolBarTitle(getResources().getString(R.string.sign_up_activity));
        emailIdSignUpTextview = (TEditText) findViewById(R.id.signup_email_id_et);
        passwordSignUpTextview = (TEditText) findViewById(R.id.signup_password_et);
        phoneSignUpTextview = (TEditText) findViewById(R.id.signup_phone_et);
        mAuth = setUpFirebase();
    }

    public void handleSignUpBtnClick(View view) {
        if (validateSignUp()) {
            mAuth.createUserWithEmailAndPassword(emailIdSignUpTextview.getText().toString(), passwordSignUpTextview.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, "Servers are unavailable!", Toast.LENGTH_SHORT).show();
                                hideProgressDialog();
                            } else {
                                hideProgressDialog();
                                Toast.makeText(SignUpActivity.this, "Signed In Successfully!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                            }
                        }
                    });
        }
    }

    private boolean validateSignUp() {
        if (emailIdSignUpTextview.getText().toString().equals("")){
            emailIdSignUpTextview.setError("Required.");
        }else if(passwordSignUpTextview.getText().toString().equals("")){
            passwordSignUpTextview.setError("Required.");
        }else if(phoneSignUpTextview.getText().toString().equals("")){
            phoneSignUpTextview.setError("Required.");
        } else {
            return true;
        }
        return false;
    }

    public void handleLoginBtnClick(View view) {
        onBackPressed();
    }
}

package com.bharatwaaj.android.tcsemergencyservices.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.bharatwaaj.android.tcsemergencyservices.R;
import com.bharatwaaj.android.tcsemergencyservices.Widgets.TEditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends TBaseActivity {

        // Firebase Components
        private FirebaseAuth mAuth;

        // Tags
        private static final String FORGOT_PASSWORD_TAG = "forgotpw";

        // UI Components
        private TEditText emailEditText;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_forgot_password);
            initUI();
        }

        private void initUI() {
            initToolBar();
            setToolBarTitle(getResources().getString(R.string.forgot_password_activity));
            emailEditText = (TEditText) findViewById(R.id.forgotpw_email_id_et);
            mAuth = setUpFirebase();
        }

        public void handleLoginBtnClick(View view) {
            onBackPressed();
        }

        public void handleFrgtPwResendBtnClick(View view) {
            if (getValidEmailId() != null) {
                showProgressDialog();
                mAuth.sendPasswordResetEmail(getValidEmailId())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    hideProgressDialog();
                                    Toast.makeText(ForgotPasswordActivity.this, "Password Email Sent!", Toast.LENGTH_SHORT).show();
                                } else {
                                    hideProgressDialog();
                                    Toast.makeText(ForgotPasswordActivity.this, "Currently our servers are under maintenance!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }

        public String getValidEmailId() {
            String text = emailEditText.getText().toString();
            if (!text.equals("")) {
                return text;
            } else {
                emailEditText.setError("Required.");
                return null;
            }
        }

}

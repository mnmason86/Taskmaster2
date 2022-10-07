package com.mnmason86.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.mnmason86.taskmaster.R;

public class VerifyActivity extends AppCompatActivity {
    public static final String TAG = "VerifyActivity";
    public static final String VERIFY_ACCOUNT_EMAIL_TAG = "Verify_Account_Email_Tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        setUpVerificationForm();

    }
    public void setUpVerificationForm() {
        Intent callingIntent = getIntent();
        String userEmail = callingIntent.getStringExtra(SignUpActivity.SIGNUP_EMAIL_TAG);
        findViewById(R.id.verifySubmitButton).setOnClickListener(view -> {
            String verificationCode = ((EditText) findViewById(R.id.verifyActivityEditText)).getText().toString();

            Amplify.Auth.confirmSignUp(
                    userEmail,
                    verificationCode,
                    success -> {
                        Log.i(TAG, "Verification succeeded: " + success.toString());
                        Intent goToLoginPage = new Intent(VerifyActivity.this, LoginActivity.class);
                        goToLoginPage.putExtra(VERIFY_ACCOUNT_EMAIL_TAG, userEmail);
                        startActivity(goToLoginPage);
                    },
                    failure -> {
                        Log.i(TAG, "Verification failed with username: " + userEmail + " with this message: " + failure.toString());
                        runOnUiThread(() -> {
                            Toast.makeText(VerifyActivity.this, "Verify account failed", Toast.LENGTH_SHORT).show();
                        });
                    }
            );
        });


        Amplify.Auth.confirmSignUp("mnmason86@gmail.com",
                "547534",
                success -> Log.i(TAG, "Verification succeeded: " + success),
                failure -> Log.i(TAG, "Verification failed: " + failure)
        );
    }
}
package com.mnmason86.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.mnmason86.taskmaster.R;

public class SignUpActivity extends AppCompatActivity {

    public static final String TAG = "SignUpActivity";
    public static final String SIGNUP_EMAIL_TAG = "Signup_Email_Tag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setUpSignUpForm();
    }

    private void setUpSignUpForm(){

        findViewById(R.id.signUpSubmitButton).setOnClickListener(view -> {
            String userEmail = ((EditText)findViewById(R.id.signUpEmailEditText)).getText().toString();
            String userPassword = ((EditText) findViewById(R.id.signUpPasswordEditText)).getText().toString();

            Amplify.Auth.signUp(userEmail,
                    userPassword,
                    AuthSignUpOptions.builder()
                            .userAttribute(AuthUserAttributeKey.email(), userEmail)
                            .build(),
                    success -> {
                        Log.i(TAG, "Signup successful! " + success);
                        Intent goToVerifyActivity = new Intent(SignUpActivity.this, VerifyActivity.class);
                        goToVerifyActivity.putExtra(SIGNUP_EMAIL_TAG, userEmail);
                        startActivity(goToVerifyActivity);
                    },
                    failure -> {
                        Log.i(TAG, "Signup failed with email " + userEmail + " with message: " + failure);

                        runOnUiThread(() -> Toast.makeText(SignUpActivity.this, "Signup Failed", Toast.LENGTH_SHORT).show());
                    }
            );
        });
    }
}
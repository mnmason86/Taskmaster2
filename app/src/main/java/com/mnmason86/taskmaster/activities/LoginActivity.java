package com.mnmason86.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

//import com.amplifyframework.core.Amplify;
import com.mnmason86.taskmaster.R;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        setUpLoginForm();
    }

//    public void setUpLoginForm(){
//        Intent callingIntent = getIntent();
//        String userEmail = callingIntent.getStringExtra(VerifyActivity.VERIFY_ACCOUNT_EMAIL_TAG);
//        findViewById(R.id.loginActivitySubmitButton).setOnClickListener(view -> {
//            String userPassword = ((EditText) findViewById(R.id.loginActivityPasswordEditText)).getText().toString();
//
//            Amplify.Auth.signIn(
//                    userEmail,
//                    userPassword,
//                    success -> {
//                        Log.i(TAG, "Login succeeded: " + success);
//                        Intent goToMainActivity = new Intent(LoginActivity.this, MainActivity.class);
//                        startActivity(goToMainActivity);
//                    },
//                    failure -> {
//                        Log.i(TAG, "Login failed: " + failure);
//                        runOnUiThread(() -> {
//                            Toast.makeText(LoginActivity.this, "Login failed!", Toast.LENGTH_SHORT).show();
//                        });
//                    }
//            );
//        });
//    }
}
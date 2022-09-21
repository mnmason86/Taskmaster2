package com.mnmason86.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    public static final String USER_NAME_TAG = "userName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userName = sharedPreferences.getString(USER_NAME_TAG, "");

        if(!userName.isEmpty()){
            EditText userNameEdited = findViewById(R.id.settingsActivityEditUsernameInput);
            userNameEdited.setText(userName);
        }

        createSaveButton();
    }
    private void createSaveButton() {
        Button saveButton = SettingsActivity.this.findViewById(R.id.settingsActivitySaveButton);

        saveButton.setOnClickListener(view -> {
            SharedPreferences.Editor preferenceEditor = sharedPreferences.edit();
            String nameInput = ((EditText) findViewById(R.id.settingsActivityEditUsernameInput)).getText().toString();
            preferenceEditor.putString(USER_NAME_TAG, nameInput);
            preferenceEditor.apply();

            Toast.makeText(SettingsActivity.this, "Username saved", Toast.LENGTH_SHORT).show();
        });
    }
}
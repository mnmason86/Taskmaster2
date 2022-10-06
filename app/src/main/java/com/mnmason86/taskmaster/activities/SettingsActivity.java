package com.mnmason86.taskmaster.activities;

import static com.mnmason86.taskmaster.activities.AddTaskActivity.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


//import com.amplifyframework.api.graphql.model.ModelQuery;
//import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Team;
import com.mnmason86.taskmaster.R;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    public static final String USER_NAME_TAG = "userName";
    public static final String USER_TEAM_TAG = "userTeam";


    List<String> teamNames = null;
    ArrayAdapter<String> adapter;

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

        teamNames = new ArrayList<>();
        setUpTeamSpinner();
        createSaveButton();

    }

    @Override
    protected void onResume(){
        super.onResume();

//        Amplify.API.query(
//                ModelQuery.list(Team.class),
//                success -> {
//                    Log.i(TAG, "Teams read successfully!");
//                    teamNames.clear();
//                    for (Team dataBaseTeam : success.getData()){
//                        teamNames.add(dataBaseTeam.getName());
//                    }
//                    runOnUiThread(() -> {
//                        adapter.notifyDataSetChanged();
//                    });
//                },
//                failure -> Log.i(TAG, "Did not read team successfully")
//        );
    }
    private void createSaveButton() {
        Button saveButton = SettingsActivity.this.findViewById(R.id.settingsActivitySaveButton);
        Spinner userTeamSpinner = findViewById(R.id.settingsActivityTeamSpinner);

        saveButton.setOnClickListener(view -> {
            SharedPreferences.Editor preferenceEditor = sharedPreferences.edit();
            String nameInput = ((EditText) findViewById(R.id.settingsActivityEditUsernameInput)).getText().toString();
            preferenceEditor.putString(USER_NAME_TAG, nameInput);
            String teamChoice = ((String) userTeamSpinner.getSelectedItem());
            preferenceEditor.putString(USER_TEAM_TAG, teamChoice);
            preferenceEditor.apply();

            Intent goToMainActivity = new Intent(SettingsActivity.this, MainActivity.class);
            goToMainActivity.putExtra(SettingsActivity.USER_NAME_TAG, nameInput);
            startActivity(goToMainActivity);
        });
    }

    private void setUpTeamSpinner(){
        Spinner userTeamSpinner = findViewById(R.id.settingsActivityTeamSpinner);
        adapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                teamNames);
        userTeamSpinner.setAdapter(adapter);
    }
}
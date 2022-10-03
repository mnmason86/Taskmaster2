package com.mnmason86.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.TaskStateEnum;
import com.mnmason86.taskmaster.R;
import com.amplifyframework.datastore.generated.model.*;
import com.amplifyframework.core.model.temporal.Temporal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AddTaskActivity extends AppCompatActivity {
    public static final String TAG = "AddTaskActivity";
    Team selectedTeam;
    List<String> teamNames = null;
    ArrayAdapter<String> adapter;
    CompletableFuture<List<Team>> teamFuture = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        teamNames = new ArrayList<>();

        setUpTaskStateSpinner();
        setUpTeamSpinner();
        setUpSubmitBttn();

    }

    @Override
    protected void onResume(){
        super.onResume();

        Amplify.API.query(
                ModelQuery.list(Team.class),
                success -> {
                    Log.i(TAG, "Teams read successfully");
                    teamNames.clear();
                    for (Team dataBaseTeam : success.getData()){
                        teamNames.add(dataBaseTeam.getName());
                    }
                    runOnUiThread(() -> {
                        adapter.notifyDataSetChanged();
                    });
                },
                failure -> {
                    Log.i(TAG, "Teams not read");
                }
        );
    }

    private void setUpTeamSpinner(){
        Spinner addTaskTeamSpinner = findViewById(R.id.addTaskTeamSpinner);
        adapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, teamNames);
        addTaskTeamSpinner.setAdapter(adapter);
    }

    private void setUpTaskStateSpinner(){
        Spinner taskStateSpinner = findViewById(R.id.addTaskStateSpinner);
        taskStateSpinner.setAdapter(new ArrayAdapter<>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                TaskStateEnum.values()
        ));
    }
    private void setUpSubmitBttn(){

        Spinner taskStateSpinner = findViewById(R.id.addTaskStateSpinner);
        Button addTaskSubmitButton = findViewById(R.id.addTaskSubmitButton);
        Spinner teamSpinner = findViewById(R.id.addTaskTeamSpinner);
        addTaskSubmitButton.setOnClickListener(view -> {

            String taskTitle = ((EditText) findViewById(R.id.addTaskInputTitle)).getText().toString();
            String taskBody = ((EditText) findViewById(R.id.addTaskInputBody)).getText().toString();
            String currentDateString = com.amazonaws.util.DateUtils.formatISO8601Date(new Date());

            Amplify.API.query(
                    ModelQuery.list(Team.class),
                    success ->  {
                        Log.i(TAG, "Successfully read teams " + success);
                        for (Team dataBaseTeam : success.getData()){
                            if(dataBaseTeam.getName().equals(teamSpinner.getSelectedItem())){
                                selectedTeam = dataBaseTeam;
                            }
                        }
                        runOnUiThread(() -> {
                            adapter.notifyDataSetChanged();
                        });
                    },
                    failure -> Log.i(TAG, "Did not read teams successfully")
            );

            Task newTask = Task.builder()
                    .name(taskTitle)
                    .body(taskBody)
                    .state((TaskStateEnum) taskStateSpinner.getSelectedItem())
                    .dateCreated(new Temporal.DateTime(currentDateString))
                    .team(selectedTeam)
                    .build();

        Amplify.API.mutate(
                ModelMutation.create(newTask),
                success -> Log.i(TAG, "Task built"),
                failure -> Log.i(TAG, "Task not built. " + failure)
        );

            Intent goToMainActivity = new Intent(AddTaskActivity.this, MainActivity.class);
            startActivity(goToMainActivity);

        });
    }
}
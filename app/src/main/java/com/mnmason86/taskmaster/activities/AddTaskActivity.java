package com.mnmason86.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.TaskStateEnum;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.mnmason86.taskmaster.R;
import com.amplifyframework.datastore.generated.model.*;

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
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        teamFuture = new CompletableFuture<>();
        teamNames = new ArrayList<>();

        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());

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
                    ArrayList<Team> teams = new ArrayList<>();
                    for (Team dataBaseTeam : success.getData()){
                        teamNames.add(dataBaseTeam.getName());
                        teams.add(dataBaseTeam);
                    }
                    teamFuture.complete(teams);
                    runOnUiThread(() -> {
                        adapter.notifyDataSetChanged();
                    });
                },
                failure -> {
                    Log.i(TAG, "Teams not read");
                    teamFuture.complete(null);
                }
        );
    }

    private void setUpTeamSpinner(){
        Spinner addTaskTeamSpinner = findViewById(R.id.addTaskTeamSpinner);
        adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, teamNames);
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

            String selectedTeamString = teamSpinner.getSelectedItem().toString();
            List<Team> teams = null;
            try {
                teams = teamFuture.get();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
                Thread.currentThread().interrupt();
            } catch (ExecutionException ee) {
                ee.printStackTrace();
            }

            selectedTeam = teams.stream().filter(t -> t.getName().equals(selectedTeamString)).findAny().orElseThrow(RuntimeException::new);

            String taskTitle = ((EditText) findViewById(R.id.addTaskInputTitle)).getText().toString();
            String taskBody = ((EditText) findViewById(R.id.addTaskInputBody)).getText().toString();
            String currentDateString = com.amazonaws.util.DateUtils.formatISO8601Date(new Date());

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
package com.mnmason86.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.Geocoder;
import android.os.Bundle;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.TaskStateEnum;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.mnmason86.taskmaster.R;
import com.amplifyframework.datastore.generated.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class AddTaskActivity extends AppCompatActivity {
    public static final String TAG = "AddTaskActivity";
    Team selectedTeam;
    List<String> teamNames = null;

    ArrayAdapter<String> adapter;
    CompletableFuture<List<Team>> teamFuture = null;
    private FusedLocationProviderClient fusedLocationClient;
    Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        teamFuture = new CompletableFuture<>();
        teamNames = new ArrayList<>();

        setUpLocation();

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

            String taskTitle = ((EditText) findViewById(R.id.addTaskInputTitle)).getText().toString();
            String taskBody = ((EditText) findViewById(R.id.addTaskInputBody)).getText().toString();
            String currentDateString = com.amazonaws.util.DateUtils.formatISO8601Date(new Date());

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

            //Save Location

            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                Log.e(TAG, "No permissions for fine or coarse location");
                return;
            }
            fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                if(location == null){
                    Log.e(TAG, "Location callback was null");
                }

                String lat = Double.toString(location.getLatitude());
                String lon = Double.toString(location.getLongitude());
                String taskLocation = "";
                Log.i(TAG, "Latitude: " + location.getLatitude());
                Log.i(TAG, "Longitude: " + location.getLongitude())

                if (!lat.equals("") && !lon.equals("")){
                    try {
                        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                        taskLocation = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),1).get(0).getAddressLine(0);
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                    Log.i(TAG, "Location: " + taskLocation);
                } else {
                    taskLocation = "Location not available";
                    saveNewTask(taskTitle, taskBody, (TaskStateEnum)taskStateSpinner.getSelectedItem(), selectedTeam, lat, lon, taskLocation);
                }
            });

            Intent goToMainActivity = new Intent(AddTaskActivity.this, MainActivity.class);
            startActivity(goToMainActivity);

        });
    }

    private void saveNewTask(String title, String body, TaskStateEnum state, Team team, String lat, String lon, String location){


        Task newTask = Task.builder()
                .name(title)
                .body(body)
                .state(state)
                .team(team)
                .latitude(lat)
                .longitude(lon)
                .location(location)
                .build();

        Amplify.API.mutate(
                ModelMutation.create(newTask),
                success -> Log.i(TAG, "Task built"),
                failure -> Log.i(TAG, "Task not built. " + failure)
        );
    }

    private void setUpLocation(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "This app does not have permission to access fine or coarse location");
            return;
        }
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        fusedLocationClient.flushLocations();
    }
}
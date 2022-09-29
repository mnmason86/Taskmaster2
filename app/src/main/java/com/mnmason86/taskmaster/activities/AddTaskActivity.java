package com.mnmason86.taskmaster.activities;

import static com.mnmason86.taskmaster.TaskAmplifyApplication.Tag;

import androidx.appcompat.app.AppCompatActivity;

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
import com.amplifyframework.datastore.generated.model.TaskStateEnum;
import com.mnmason86.taskmaster.R;
import com.amplifyframework.datastore.generated.model.*;
import com.amplifyframework.core.model.temporal.Temporal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public class AddTaskActivity extends AppCompatActivity {
    public static final String TAG = "AddTaskActivity";

    Spinner teamSpinner = null;
    CompletableFuture<List<Team>> teamFuture = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        setUpTaskStateSpinner();
        setUpSubmitBttn();

    }

    private void setUpTeamSpinner(){
        Amplify.API.query(
                ModelQuery.list(Team.class),
                success -> {
                    Log.i(TAG, "Read teams successfully");
                    ArrayList<String> teamNames = new ArrayList<>();
                    ArrayList<Team> teams = new ArrayList<>();
                    for (Team team : success.getData()){
                        teams.add(team);
                        teamNames.add(team.getName());
                    }
                    teamFuture.complete(teams);
                    runOnUiThread(() -> {
                        teamSpinner.setAdapter(new ArrayAdapter<>(
                                this,
                                android.R.layout.simple_spinner_item,
                                teamNames));
                    });
                },
                failure -> {
                    teamFuture.complete(null);
                    Log.i(TAG, "Did not read teams successfully");
                }
        );
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
        addTaskSubmitButton.setOnClickListener(view -> {

            String taskTitle = ((EditText) findViewById(R.id.addTaskInputTitle)).getText().toString();
            String taskBody = ((EditText) findViewById(R.id.addTaskInputBody)).getText().toString();
            String currentDateString = com.amazonaws.util.DateUtils.formatISO8601Date(new Date());

            Task newTask = Task.builder()
                    .name(taskTitle)
                    .body(taskBody)
                    .state((TaskStateEnum) taskStateSpinner.getSelectedItem())
                    .dateCreated(new Temporal.DateTime(currentDateString))
                    .build();

            Amplify.API.mutate(
                    ModelMutation.create(newTask),
                    success -> Log.i(Tag, "AddTaskActivity: created task successfully"),
                    failure -> Log.i(Tag, "AddTaskActivity: failed with this response: " + failure)
            );

            Intent goToMainActivity = new Intent(AddTaskActivity.this, MainActivity.class);
            startActivity(goToMainActivity);

        });
    }
}
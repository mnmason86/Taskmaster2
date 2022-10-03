package com.mnmason86.taskmaster.activities;

import static com.mnmason86.taskmaster.TaskAmplifyApplication.Tag;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.TaskStateEnum;
import com.amplifyframework.datastore.generated.model.Team;
import com.mnmason86.taskmaster.R;
import com.mnmason86.taskmaster.adapters.TaskListRecyclerViewAdapter;
import com.amplifyframework.datastore.generated.model.Task;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    public static final String TASK_NAME_EXTRA_TAG = "taskName";
    public static final String TASK_BODY_EXTRA_TAG = "taskBody";
    public static final String TASK_STATE_EXTRA_TAG = "taskState";
    public static final String TAG = "MainActivity";

    SharedPreferences sharedPreferences;
    TextView userTasksTV;
    TextView userTeamTV;
    String userTeam;
    String userName;

    List<Task> taskList = null;
    TaskListRecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        taskList = new ArrayList<>();
        setUpTaskRecyclerView();
        createAddTaskButton();
        createAllTaskButton();
        createSettingsButton();

        //Hardcode Teams
//        String currentDateString = com.amazonaws.util.DateUtils.formatISO8601Date(new Date());
//        Team teamOne = Team.builder()
//                .name("Toonts")
//                .build();
//        Amplify.API.mutate(
//                ModelMutation.create(teamOne),
//                success -> Log.i(TAG, "Team One built"),
//                failure -> Log.i(TAG, "Team One not built")
//        );
//
//        Team teamTwo = Team.builder()
//                .name("TimeZoneBandits")
//                .build();
//        Amplify.API.mutate(
//                ModelMutation.create(teamTwo),
//                success -> Log.i(TAG, "Team Two built"),
//                failure -> Log.i(TAG, "Team Two not built")
//        );
//
//        Team teamThree = Team.builder()
//                .name("JavaNauts")
//                .build();
//        Amplify.API.mutate(
//                ModelMutation.create(teamThree),
//                success -> Log.i(TAG, "Team Three built"),
//                failure -> Log.i(TAG, "Team Three not built")
//        );
//
//        Task taskOne = Task.builder()
//                .name("Dishes")
//                .body("Unload dishwasher, reload")
//                .state(TaskStateEnum.Assigned)
//                .dateCreated(new Temporal.DateTime(currentDateString))
//                .team(teamOne)
//                .build();
//        Amplify.API.mutate(
//                ModelMutation.create(taskOne),
//                success -> Log.i(TAG, "Task One built"),
//                failure -> Log.i(TAG, "Task One not built")
//        );
//
//        Task taskTwo = Task.builder()
//                .name("Trash")
//                .body("Take out, replace bag")
//                .state(TaskStateEnum.New)
//                .dateCreated(new Temporal.DateTime(currentDateString))
//                .team(teamTwo)
//                .build();
//        Amplify.API.mutate(
//                ModelMutation.create(taskTwo),
//                success -> Log.i(TAG, "Task Two built"),
//                failure -> Log.i(TAG, "Task Two not built")
//        );
//
//        Task taskThree = Task.builder()
//                .name("Vacuum")
//                .body("Living room rugs, bedroom rug, office rug")
//                .state(TaskStateEnum.Assigned)
//                .dateCreated(new Temporal.DateTime(currentDateString))
//                .team(teamThree)
//                .build();
//        Amplify.API.mutate(
//                ModelMutation.create(taskThree),
//                success -> Log.i(TAG, "Task Three built"),
//                failure -> Log.i(TAG, "Task Three not built")
//        );

 }

    @Override
    protected void onResume(){
        super.onResume();

        Amplify.API.query(
                ModelQuery.list(Task.class),
                success -> {
                    Log.i(Tag, "Read Tasks successfully!");
                    taskList.clear();
                    for(Task databaseTask : success.getData()){
                        if(databaseTask.getTeam().getName().equals(userTeam)){
                            taskList.add(databaseTask);
                        }
                    }
                    runOnUiThread(() -> {
                        adapter.notifyDataSetChanged();
                    });
                },
                failure -> Log.i(Tag, "Did not read Tasks successfully :(")
        );

        userName = sharedPreferences.getString(SettingsActivity.USER_NAME_TAG,"userName");
        userTeam = sharedPreferences.getString(SettingsActivity.USER_TEAM_TAG, "Choose a team!");
        userTasksTV = (TextView) findViewById(R.id.activityMainUsernameTextView);
        userTeamTV = (TextView) findViewById(R.id.activityMainUserTeamTextView);
        userTasksTV.setText(userName + "'s Tasks:");
        userTeamTV.setText(userTeam);

    }

    private void setUpTaskRecyclerView(){

        RecyclerView taskRecyclerView = findViewById(R.id.taskRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        taskRecyclerView.setLayoutManager(layoutManager);
        adapter = new TaskListRecyclerViewAdapter(taskList, this);
        taskRecyclerView.setAdapter(adapter);
    }

    private void createAddTaskButton() {
        Button addTaskButton = MainActivity.this.findViewById(R.id.mainActivityAddTaskButton);

        addTaskButton.setOnClickListener(view -> {
            Intent goToAddTaskPage = new Intent(MainActivity.this, AddTaskActivity.class);
            startActivity(goToAddTaskPage);
        });
    }

    private void createAllTaskButton(){
        Button allTaskButton = MainActivity.this.findViewById(R.id.mainActivityAllTasksButton);

        allTaskButton.setOnClickListener(view -> {
            Intent goToAllTaskPage = new Intent(MainActivity.this, AllTaskActivity.class);
            startActivity(goToAllTaskPage);
        });
    }

    private void createSettingsButton(){
        Button settingsButton = MainActivity.this.findViewById(R.id.mainActivitySettingsButton);

        settingsButton.setOnClickListener(view -> {
            Intent goToSettingsPage = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(goToSettingsPage);
        });
    }
}
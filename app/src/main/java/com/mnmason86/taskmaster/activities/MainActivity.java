package com.mnmason86.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.TextView;

import com.mnmason86.taskmaster.R;
import com.mnmason86.taskmaster.adapters.TaskListRecyclerViewAdapter;
import com.mnmason86.taskmaster.database.ToDoDatabase;
import com.mnmason86.taskmaster.models.Task;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    public static final String DATABASE_NAME = "taskmasterDb";
    public static final String TASK_NAME_EXTRA_TAG = "taskName";
    SharedPreferences sharedPreferences;
    ToDoDatabase toDoDatabase;
    List<Task> taskList = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        toDoDatabase = Room.databaseBuilder(
                //put in separate method and call
                getApplicationContext(),
                ToDoDatabase.class,
                DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build(); //not implicit, MUST tell to build

        taskList = toDoDatabase.taskDao().findAll();

        setUpTaskRecyclerView();
        createAddTaskButton();
        createAllTaskButton();
        createSettingsButton();
    }
    private void setUpTaskRecyclerView(){

        RecyclerView taskRecyclerView = findViewById(R.id.taskRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        taskRecyclerView.setLayoutManager(layoutManager);

        List<Task> taskList = toDoDatabase.taskDao().findAll();

        TaskListRecyclerViewAdapter adapter = new TaskListRecyclerViewAdapter(taskList, this);
        taskRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume(){
        super.onResume();
        String userName = sharedPreferences.getString(SettingsActivity.USER_NAME_TAG, "No username");
        TextView userNameEdited = findViewById(R.id.activityMainUsernameTextView);
        userNameEdited.setText(userName + "'s Tasks");

        taskList.clear();
        taskList.addAll(toDoDatabase.taskDao().findAll());

        setUpTaskRecyclerView();
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
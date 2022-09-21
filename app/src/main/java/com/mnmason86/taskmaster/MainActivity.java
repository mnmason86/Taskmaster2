package com.mnmason86.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.prefs.PreferenceChangeEvent;


public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        createAddTaskButton();
        createAllTaskButton();
        createTaskOneButton();
        createTaskTwoButton();
        createTaskThreeButton();
        createSettingsButton();
    }

    @Override
    protected void onResume(){
        super.onResume();
        String userName = sharedPreferences.getString(SettingsActivity.USER_NAME_TAG, "No username");
        TextView userNameEdited = findViewById(R.id.activityMainUsernameTextView);
        userNameEdited.setText(userName + "'s Tasks");
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

    private void createTaskOneButton(){
        Button taskOneButton = MainActivity.this.findViewById(R.id.mainActivityTaskOneButton);

        taskOneButton.setOnClickListener(view -> {
            Intent goToTaskDetailPage = new Intent(MainActivity.this, TaskDetailActivity.class);
            String buttonText = ((Button)view).getText().toString();
            goToTaskDetailPage.putExtra("taskButtonName", buttonText);
            startActivity(goToTaskDetailPage);
        });
    }

    private void createTaskTwoButton(){
        Button taskTwoButton = MainActivity.this.findViewById(R.id.mainActivityTaskTwoButton);

        taskTwoButton.setOnClickListener(view -> {
            Intent goToTaskDetailPage = new Intent(MainActivity.this, TaskDetailActivity.class);
            String buttonText = ((Button)view).getText().toString();
            goToTaskDetailPage.putExtra("taskButtonName", buttonText);
            startActivity(goToTaskDetailPage);
        });
    }

    private void createTaskThreeButton(){
        Button taskThreeButton = MainActivity.this.findViewById(R.id.mainActivityTaskThreeButton);

        taskThreeButton.setOnClickListener(view -> {
            Intent goToTaskDetailPage = new Intent(MainActivity.this, TaskDetailActivity.class);
            String buttonText = ((Button)view).getText().toString();
            goToTaskDetailPage.putExtra("taskButtonName", buttonText);
            startActivity(goToTaskDetailPage);
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
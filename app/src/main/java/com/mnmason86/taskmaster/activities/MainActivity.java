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
    SharedPreferences sharedPreferences;
    public static final String TASK_NAME_EXTRA_TAG = "taskName";
    ToDoDatabase toDoDatabase;


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

        toDoDatabase.taskDao().findAll();

        createAddTaskButton();
        createAllTaskButton();
        createSettingsButton();
        setUpTaskRecyclerView();
    }
    private void setUpTaskRecyclerView(){

        RecyclerView taskRecyclerView = findViewById(R.id.taskRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        taskRecyclerView.setLayoutManager(layoutManager);

        List<Task> taskList = new ArrayList<>();

//        taskList.add(new Task("Laundry", "Wash, dry, fold, and put away laundry", "in progress"));
//        taskList.add(new Task("Dishes", "Put away clean dishes, re-load dishwasher", "assigned"));
//        taskList.add(new Task("Mail", "Check mail", "complete"));

        TaskListRecyclerViewAdapter adapter = new TaskListRecyclerViewAdapter(taskList, this);
        taskRecyclerView.setAdapter(adapter);
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

//    private void createTaskOneButton(){
//        Button taskOneButton = MainActivity.this.findViewById(R.id.mainActivityTaskOneButton);
//
//        taskOneButton.setOnClickListener(view -> {
//            Intent goToTaskDetailPage = new Intent(MainActivity.this, TaskDetailActivity.class);
//            String buttonText = ((Button)view).getText().toString();
//            goToTaskDetailPage.putExtra("taskButtonName", buttonText);
//            startActivity(goToTaskDetailPage);
//        });
//    }
//
//    private void createTaskTwoButton(){
//        Button taskTwoButton = MainActivity.this.findViewById(R.id.mainActivityTaskTwoButton);
//
//        taskTwoButton.setOnClickListener(view -> {
//            Intent goToTaskDetailPage = new Intent(MainActivity.this, TaskDetailActivity.class);
//            String buttonText = ((Button)view).getText().toString();
//            goToTaskDetailPage.putExtra("taskButtonName", buttonText);
//            startActivity(goToTaskDetailPage);
//        });
//    }
//
//    private void createTaskThreeButton(){
//        Button taskThreeButton = MainActivity.this.findViewById(R.id.mainActivityTaskThreeButton);
//
//        taskThreeButton.setOnClickListener(view -> {
//            Intent goToTaskDetailPage = new Intent(MainActivity.this, TaskDetailActivity.class);
//            String buttonText = ((Button)view).getText().toString();
//            goToTaskDetailPage.putExtra("taskButtonName", buttonText);
//            startActivity(goToTaskDetailPage);
//        });
//    }

    private void createSettingsButton(){
        Button settingsButton = MainActivity.this.findViewById(R.id.mainActivitySettingsButton);

        settingsButton.setOnClickListener(view -> {
            Intent goToSettingsPage = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(goToSettingsPage);
        });
    }
}
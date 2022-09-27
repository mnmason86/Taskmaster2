package com.mnmason86.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mnmason86.taskmaster.R;
import com.mnmason86.taskmaster.database.ToDoDatabase;
import com.mnmason86.taskmaster.models.Task;

import java.util.Date;


public class AddTaskActivity extends AppCompatActivity {
    public static final String DATABASE_NAME = "taskmasterDb";
    ToDoDatabase toDoDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        toDoDatabase = Room.databaseBuilder(
                getApplicationContext(),
                ToDoDatabase.class,
                DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        setUpTaskStateSpinner();
        setUpSubmitBttn();

    }
    private void setUpTaskStateSpinner(){
        Spinner taskStateSpinner = findViewById(R.id.addTaskStateSpinner);
        taskStateSpinner.setAdapter(new ArrayAdapter<>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                Task.TaskStateEnum.values()
        ));
    }
    private void setUpSubmitBttn(){
        Spinner taskStateSpinner = findViewById(R.id.addTaskStateSpinner);
        Button addTaskSubmitButton = findViewById(R.id.addTaskSubmitButton);
        addTaskSubmitButton.setOnClickListener(view -> {

            String taskTitle = ((EditText) findViewById(R.id.addTaskInputTitle)).getText().toString();
            String taskBody = ((EditText) findViewById(R.id.addTaskInputBody)).getText().toString();
            java.util.Date newDate = new Date();
            Task.TaskStateEnum taskStateEnum = Task.TaskStateEnum.fromString(taskStateSpinner.getSelectedItem().toString());

            Task newTask = new Task(taskTitle, taskBody, taskStateEnum, newDate);

            toDoDatabase.taskDao().insertTask(newTask);

            Intent goToMainActivity = new Intent(AddTaskActivity.this, MainActivity.class);
            startActivity(goToMainActivity);

        });
    }
}
package com.mnmason86.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mnmason86.taskmaster.R;
import com.mnmason86.taskmaster.models.Task;

import java.util.Date;


public class AddTaskActivity extends AppCompatActivity {
    public static final String DATABASE_NAME = "taskmasterDb";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);


        Button addTaskSubButton = AddTaskActivity.this.findViewById(R.id.addTaskSubmitButton);
        addTaskSubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context submitted = getApplicationContext();
                CharSequence text = "Task Submitted!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(submitted, text, duration);
                toast.show();
            }
        });

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

        String taskTitle = ((EditText) findViewById(R.id.taskDetailTitleTextView)).getText().toString();
        String taskBody = ((EditText) findViewById(R.id.taskDetailDescriptionPlainText)).getText().toString();
        java.util.Date newDate = new Date();

        Task.TaskStateEnum taskStateEnum = Task.TaskStateEnum.fromString(taskStateSpinner).getSelectedItem().toString());

        Task newTask = new Task(taskTitle, taskBody, taskStateEnum, newDate);

        taskmasterDB.TaskDao().insertNewTask(newTask);

        Intent goToTaskDetailActivity

        });
    }
}
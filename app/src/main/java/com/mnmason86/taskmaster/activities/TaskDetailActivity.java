package com.mnmason86.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.mnmason86.taskmaster.R;



public class TaskDetailActivity extends AppCompatActivity {
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
    }

    @Override
    protected void onResume(){
        super.onResume();

        String taskName = getIntent().getStringExtra("taskName");
        TextView taskNameEdited = findViewById(R.id.taskDetailTitleTextView);
        taskNameEdited.setText(taskName);

        String taskBody = getIntent().getStringExtra("taskBody");
        TextView taskBodyEdited = findViewById(R.id.taskDetailBodyPlainText);
        taskBodyEdited.setText(taskBody);

        String taskState = getIntent().getStringExtra("taskState");
        TextView taskStateEdited = findViewById(R.id.taskDetailStateTextView);
        taskStateEdited.setText(taskState);
    }
}
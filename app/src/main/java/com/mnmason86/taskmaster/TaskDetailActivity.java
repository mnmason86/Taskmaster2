package com.mnmason86.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

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

        String taskName = getIntent().getStringExtra("taskButtonName");
        TextView taskNameEdited = findViewById(R.id.taskDetailTitleTextView);
        taskNameEdited.setText(taskName);
    }
}
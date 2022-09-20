package com.mnmason86.taskmaster;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class AllTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_task);

        Button goToMainActivityButton = AllTaskActivity.this.findViewById(R.id.allTasksBackButton);

        goToMainActivityButton.setOnClickListener(view -> {
            Intent goToActivityMain = new Intent(AllTaskActivity.this, MainActivity.class);
            startActivity(goToActivityMain);
        });
    }
}
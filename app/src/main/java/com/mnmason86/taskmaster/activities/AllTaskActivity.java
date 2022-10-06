package com.mnmason86.taskmaster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.mnmason86.taskmaster.R;
import com.mnmason86.taskmaster.adapters.TaskListRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class AllTaskActivity extends AppCompatActivity {
    public static final String TAG = "AllTaskActivity";
    List<Task> taskList = null;
    TaskListRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_task);

        taskList = new ArrayList<>();

        setUpTaskRecyclerView();
        createBackButton();
    }
    @Override
    protected void onResume() {
        super.onResume();

        Amplify.API.query(
                ModelQuery.list(Task.class),
                success -> {
                    Log.i(TAG, "Read Tasks successfully!");
                    taskList.clear();
                    for (Task databaseTask : success.getData()) {
                        taskList.add(databaseTask);
                    }
                    runOnUiThread(() -> {
                        adapter.notifyDataSetChanged();
                    });
                },
                failure -> Log.i(TAG, "Did not read Tasks successfully :(")
        );
    }
    private void createBackButton(){
            Button goToMainActivityButton = AllTaskActivity.this.findViewById(R.id.allTasksBackButton);

            goToMainActivityButton.setOnClickListener(view -> {
                Intent goToActivityMain = new Intent(AllTaskActivity.this, MainActivity.class);
                startActivity(goToActivityMain);
            });
        }

    private void setUpTaskRecyclerView(){

        RecyclerView taskRecyclerView = findViewById(R.id.allTaskRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        taskRecyclerView.setLayoutManager(layoutManager);
        adapter = new TaskListRecyclerViewAdapter(taskList, this);
        taskRecyclerView.setAdapter(adapter);
    }

}
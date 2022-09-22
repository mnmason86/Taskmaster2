package com.mnmason86.taskmaster.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mnmason86.taskmaster.R;
import com.mnmason86.taskmaster.activities.MainActivity;
import com.mnmason86.taskmaster.activities.TaskDetailActivity;
import com.mnmason86.taskmaster.models.Task;

import java.util.List;

public class TaskListRecyclerViewAdapter extends RecyclerView.Adapter<TaskListRecyclerViewAdapter.TaskListViewHolder> {

    List<Task> taskList;
    Context callingActivity;

    public TaskListRecyclerViewAdapter(List<Task> taskList, Context callingActivity){
        this.taskList = taskList;
        this.callingActivity = callingActivity;
    }

    @NonNull
    @Override
    public TaskListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View taskFragment = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task, parent, false);
        return new TaskListViewHolder(taskFragment);
    }


    @Override
    public void onBindViewHolder(@NonNull TaskListRecyclerViewAdapter.TaskListViewHolder holder, int position) {
        TextView taskFragmentNameTextView = holder.itemView.findViewById(R.id.taskFragmentName);
        String taskName = taskList.get(position).getName();
        taskFragmentNameTextView.setText((position + 1) + ". " + taskName);

        TextView taskFragmentTextViewBody = holder.itemView.findViewById(R.id.taskFragmentBody);
        String taskBody = taskList.get(position).getBody();
        taskFragmentTextViewBody.setText(taskBody);

        TextView taskFragmentTextViewState = holder.itemView.findViewById(R.id.taskFragmentState);
        String taskState = taskList.get(position).getState();
        taskFragmentTextViewState.setText(taskState);

        View taskViewHolder = holder.itemView;
        taskViewHolder.setOnClickListener(view -> {
            Intent goToTaskDetailPage = new Intent(callingActivity, TaskDetailActivity.class);
            goToTaskDetailPage.putExtra(MainActivity.TASK_NAME_EXTRA_TAG, taskName);
            callingActivity.startActivity(goToTaskDetailPage);
        });
    }

    @Override
    public int getItemCount() {

        return taskList.size();
    }

    public static class TaskListViewHolder extends RecyclerView.ViewHolder {
        public TaskListViewHolder(@NonNull View itemView){
            super(itemView);
        }
    }
}

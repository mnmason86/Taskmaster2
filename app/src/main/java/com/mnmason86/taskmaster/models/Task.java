package com.mnmason86.taskmaster.models;

import androidx.annotation.NonNull;

import java.util.Date;


public class Task {

    public Long id;
    private String name;
    private String body;
    private TaskStateEnum taskState;
    private java.util.Date dateCreated;

    public Task(String name, String body, TaskStateEnum taskState, Date dateCreated) {
        this.name = name;
        this.body = body;
        this.taskState = taskState;
        this.dateCreated = dateCreated;
    }

    public Task(){
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public TaskStateEnum getTaskState() {
        return taskState;
    }

    public void setTaskState(TaskStateEnum taskState) {
        this.taskState = taskState;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public enum TaskStateEnum {
        NEW("new"),
        ASSIGNED("assigned"),
        IN_PROGRESS("in progress"),
        COMPLETED("completed");

        private final String taskState;

        TaskStateEnum(String taskState){
            this.taskState = taskState;
        }

        public String getTaskState() {
            return taskState;
        }

        public static TaskStateEnum fromString(String taskStateOption){
            for (TaskStateEnum state : TaskStateEnum.values()){
                if(state.taskState.equals(taskStateOption)){
                    return state;
                }
            }
            return null;
        }

        @NonNull
        @Override
        public String toString(){
            if(taskState == null){
                return "not assigned";
            }
            return taskState;
        }
    }
}

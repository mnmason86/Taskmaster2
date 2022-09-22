package com.mnmason86.taskmaster.models;

public class Task {

    private String name;
    private String body;
    private String state;

    public Task(String name, String body, String state) {
        this.name = name;
        this.body = body;
        this.state = "Status: " + state;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

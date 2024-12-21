package com.example.taskmanager;

public class Task {
    private int id;
    private String title;
    private String description;
    private String deadline;
    private String status;

    public Task(int id, String title, String description, String deadline, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.status = status;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getDeadline() { return deadline; }
    public String getStatus() { return status; }
}
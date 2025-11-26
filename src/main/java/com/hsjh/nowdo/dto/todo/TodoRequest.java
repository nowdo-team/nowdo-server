package com.hsjh.nowdo.dto.todo;

public class TodoRequest {

    private String content;
    private String dueDate;
    private String priority;
    private String category;

    public TodoRequest() {}

    public String getContent() {
        return content;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public String getCategory() {
        return category;
    }
}

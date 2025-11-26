package com.hsjh.nowdo.dto.todo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TodoRequest {

    private String content;       

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dueDate;
    private String priority;
    private String category;

    public TodoRequest() {}

    public String getContent() {
        return content;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public String getCategory() {
        return category;
    }
}

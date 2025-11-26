package com.hsjh.nowdo.dto.todo;

import java.time.LocalDateTime;

import org.springframework.cglib.core.Local;

import com.hsjh.nowdo.domain.todo.Todo;

public class TodoResponse {

    private Long id;
    private String content;
    private LocalDateTime dueDate;
    private boolean completed;
    private String priority;
    private String category;

    public TodoResponse(Todo todo) {
        this.id = todo.getId();
        this.content = todo.getContent();
        this.dueDate = todo.getDueDate();
        this.completed = todo.isCompleted();
        this.priority = todo.getPriority().name();
        this.category = todo.getCategory();
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getPriority() {
        return priority;
    }

    public String getCategory() {
        return category;
    }
}

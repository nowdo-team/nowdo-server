package com.hsjh.nowdo.controller;

import com.hsjh.nowdo.dto.todo.TodoRequest;
import com.hsjh.nowdo.dto.todo.TodoResponse;
import com.hsjh.nowdo.service.TodoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public TodoResponse createTodo(
            @RequestHeader("userId") Long userId,
            @RequestBody TodoRequest dto) {
        return todoService.createTodo(userId, dto);
    }

    @GetMapping
    public List<TodoResponse> getTodos(
            @RequestHeader("userId") Long userId) {
        return todoService.getTodos(userId);
    }

    @PutMapping("/{todoId}")
    public TodoResponse updateTodo(
            @RequestHeader("userId") Long userId,
            @PathVariable Long todoId,
            @RequestBody TodoRequest dto) {
        return todoService.updateTodo(userId, todoId, dto);
    }

    @DeleteMapping("/{todoId}")
    public String deleteTodo(
            @RequestHeader("userId") Long userId,
            @PathVariable Long todoId) {
        todoService.deleteTodo(userId, todoId);
        return "삭제 완료";
    }
}

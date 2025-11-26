package com.hsjh.nowdo.controller;

import com.hsjh.nowdo.common.exception.UnauthorizedException;
import com.hsjh.nowdo.dto.todo.TodoRequest;
import com.hsjh.nowdo.dto.todo.TodoResponse;
import com.hsjh.nowdo.service.TodoService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
            HttpServletRequest request,
            @RequestBody TodoRequest dto) {

        Long userId = getUserIdFromSession(request);
        return todoService.createTodo(userId, dto);
    }

    @GetMapping
    public List<TodoResponse> getTodos(
            HttpServletRequest request) {
        Long userId = getUserIdFromSession(request);        
        return todoService.getTodos(userId);
    }

    @PutMapping("/{todoId}")
    public TodoResponse updateTodo(
            HttpServletRequest request,
            @PathVariable Long todoId,
            @RequestBody TodoRequest dto) {
        Long userId = getUserIdFromSession(request);        
        return todoService.updateTodo(userId, todoId, dto);
    }

    @DeleteMapping("/{todoId}")
    public String deleteTodo(
            HttpServletRequest request,
            @PathVariable Long todoId) {
        Long userId = getUserIdFromSession(request);
        todoService.deleteTodo(userId, todoId);
        return "삭제 완료";
    }

    private Long getUserIdFromSession(
        HttpServletRequest request) { 
            HttpSession session = request.getSession(false); 
            if (session == null || session.getAttribute("userId") == null) { 
                throw new UnauthorizedException("로그인이 필요합니다.");
            } 
        return (Long) session.getAttribute("userId"); 
    }
}


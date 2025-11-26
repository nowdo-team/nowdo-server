package com.hsjh.nowdo.service;

import com.hsjh.nowdo.dto.todo.TodoRequest;
import com.hsjh.nowdo.dto.todo.TodoResponse;

import java.util.List;

public interface TodoService {

    TodoResponse createTodo(Long userId, TodoRequest requestDto);
    List<TodoResponse> getTodos(Long userId);
    TodoResponse updateTodo(Long userId, Long todoId, TodoRequest dto);
    void deleteTodo(Long userId, Long todoId);
}

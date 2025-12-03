package com.hsjh.nowdo.service;

import com.hsjh.nowdo.common.exception.NotFoundException;
import com.hsjh.nowdo.common.exception.UnauthorizedException;
import com.hsjh.nowdo.domain.todo.Todo;
import com.hsjh.nowdo.domain.todo.TodoPriority;
import com.hsjh.nowdo.domain.user.User;
import com.hsjh.nowdo.dto.todo.TodoRequest;
import com.hsjh.nowdo.dto.todo.TodoResponse;
import com.hsjh.nowdo.repository.TodoRepository;
import com.hsjh.nowdo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public TodoServiceImpl(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TodoResponse createTodo(Long userId, TodoRequest requestDto) {
        if (userId == null) {
            throw new UnauthorizedException("userId가 없습니다, 세션 확인");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("유저 없음"));

        LocalDateTime dueDate = requestDto.getDueDate();
        TodoPriority priority = parsePriority(requestDto.getPriority(), TodoPriority.MEDIUM);

        Todo todo = new Todo(
                user,
                requestDto.getContent(),
                dueDate,
                priority,
                requestDto.getCategory()
        );
        if (requestDto.isCompleted()) {
            todo.toggleCompleted();
        }

        return new TodoResponse(todoRepository.save(todo));
    }

    @Override
    public List<TodoResponse> getTodos(Long userId) {
        if (userId == null) {
            throw new UnauthorizedException("userId가 없습니다, 세션 확인");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("유저 없음"));

        return todoRepository.findAllByUser(user)
                .stream()
                .map(TodoResponse::new)
                .toList();
    }

    @Override
    public TodoResponse updateTodo(Long userId, Long todoId, TodoRequest dto) {
        if (todoId == null) {
            throw new UnauthorizedException("todoId가 없습니다, 세션 확인");
        }
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new NotFoundException("TODO 없음"));

        if (!todo.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("권한 없음");
        }

        String content = dto.getContent() != null ? dto.getContent() : todo.getContent();
        LocalDateTime dueDate = dto.getDueDate() != null ? dto.getDueDate() : todo.getDueDate();
        TodoPriority priority = parsePriority(dto.getPriority(), todo.getPriority());
        String category = dto.getCategory() != null ? dto.getCategory() : todo.getCategory();

        todo.update(
                content,
                dueDate,
                priority,
                category,
                dto.isCompleted()
        );

        return new TodoResponse(todoRepository.save(todo));
    }

    @Override
    public void deleteTodo(Long userId, Long todoId) {
        if (todoId == null) {
            throw new UnauthorizedException("userId가 없습니다, 세션 확인");
        }
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new NotFoundException("TODO 없음"));

        if (!todo.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("권한 없음");
        }

        todoRepository.delete(todo);
    }

    private TodoPriority parsePriority(String priority, TodoPriority defaultPriority) {
        if (priority == null || priority.isBlank()) {
            return defaultPriority;
        }
        try {
            return TodoPriority.valueOf(priority);
        } catch (IllegalArgumentException ex) {
            return defaultPriority;
        }
    }
}

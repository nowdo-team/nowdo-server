package com.hsjh.nowdo.repository;

import com.hsjh.nowdo.domain.todo.Todo;
import com.hsjh.nowdo.domain.user.User;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findAllByUser(User user);
}

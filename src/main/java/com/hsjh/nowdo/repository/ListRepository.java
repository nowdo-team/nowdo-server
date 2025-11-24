package com.hsjh.nowdo.repository;

import com.hsjh.nowdo.domain.list.TodoList;
import com.hsjh.nowdo.domain.user.User;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ListRepository extends JpaRepository<TodoList, Long> {
    List<TodoList> findByUser(User user);
}

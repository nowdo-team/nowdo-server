package com.hsjh.nowdo.repository;

<<<<<<< HEAD:src/main/java/com/hsjh/nowdo/repository/ListRepository.java
import com.hsjh.nowdo.domain.list.TodoList;
import com.hsjh.nowdo.domain.user.User;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ListRepository extends JpaRepository<TodoList, Long> {
    List<TodoList> findByUser(User user);
=======
public interface TodoRepository {
>>>>>>> c40a8dacbba391ca2892e00044d10cb925c760a3:src/main/java/com/hsjh/nowdo/repository/TodoRepository.java
}

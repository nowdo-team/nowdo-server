package com.hsjh.nowdo.domain.todo;

import com.hsjh.nowdo.domain.user.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity // 데이터베이스 테이블과 매핑되는 Entity Class, JPA/Hibernate가 이 객체를 관리하고 DB에 저장/조회할 수 있게함. 
@Table(name = "todos") // DB 테이블 이름
public class Todo {

    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  // 여러 Todo는 한 명의 User (N : 1), fetch = Lazy : 지연 로딩, 필요한 데이터만 dto로 선택적 조회
    @JoinColumn(name = "user_id", nullable = false) // db column name, NOT NULL
    private User user;

    @Column(nullable = false, length = 255)
    private String content;

    @Column(nullable = false)
    private LocalDateTime dueDate;

    @Column(nullable = false)
    private boolean completed = false;

    @Enumerated(EnumType.STRING)    // Enum을 문자열로 DB에 저장
    @Column(nullable = false)
    private TodoPriority priority = TodoPriority.MEDIUM;

    @Column(length = 50)
    private String category;

    protected Todo() {} // (JPA 필수) 일반 코드에서 쓰이지 않고, JPA만 호출 가능하게 함

    // 생성자
    public Todo(User user, String content, LocalDateTime dueDate,
                TodoPriority priority, String category) {
        this.user = user;
        this.content = content;
        this.dueDate = dueDate;
        this.priority = priority != null ? priority : TodoPriority.MEDIUM;
        this.category = category;
    }

    // Getter
    public Long getId() { return id; }
    public User getUser() { return user; }
    public String getContent() { return content; }
    public LocalDateTime getDueDate() { return dueDate; }
    public boolean isCompleted() { return completed; }
    public TodoPriority getPriority() { return priority; }
    public String getCategory() { return category; }

    // Setter
    public void toggleCompleted() {
        this.completed = !this.completed;
    }

    public void update(String content, LocalDateTime dueDate,
                       TodoPriority priority, String category) {
        this.content = content;
        this.dueDate = dueDate;
        this.priority = priority != null ? priority : this.priority;
        this.category = category;
    }
}

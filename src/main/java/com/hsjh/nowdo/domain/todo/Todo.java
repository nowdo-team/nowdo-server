package com.hsjh.nowdo.domain.todo;

import com.hsjh.nowdo.domain.user.User;
import jakarta.persistence.*;
// import lombok.Getter;
// import lombok.Setter;
import java.time.LocalDateTime;

@Entity
// @Getter @Setter
public class Todo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // List 내용 구조체 구성
    @Column(nullable = false)
    private String title;

    private String content;

    private boolean completed = false;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    //User와의 연결 (N : 1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

    //Getter & Setter

    public String getTitle() {return title;}
    public void setTitle(String title){
        this.title = title;
    }

    public String getContent() {return content;}
    public void setContent (String content){
        this.content = content;
    }

    public boolean getCompleted(){return completed;}
    public void setCompleted(boolean completed){
        this.completed = completed;
    }

    public LocalDateTime getCreatedAt(){ return createdAt; }
    public LocalDateTime getUpdatedAt(){ return updatedAt; }

}

package com.todoapp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Todo实体类
 * 对应数据库todos表
 */
@Entity
@Table(name = "todos", indexes = {
    @Index(name = "idx_todos_completed", columnList = "completed"),
    @Index(name = "idx_todos_created_at", columnList = "created_at"),
    @Index(name = "idx_todos_title", columnList = "title")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "title", nullable = false, length = 255)
    private String title;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "completed", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean completed = false;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    /**
     * 构造方法：创建新的Todo
     */
    public Todo(String title, String description) {
        this.title = title;
        this.description = description;
        this.completed = false;
    }
    
    /**
     * 切换完成状态
     */
    public void toggleCompleted() {
        this.completed = !this.completed;
    }
}

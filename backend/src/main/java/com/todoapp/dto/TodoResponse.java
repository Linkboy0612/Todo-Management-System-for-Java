package com.todoapp.dto;

import com.todoapp.entity.Todo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * Todo响应DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoResponse {
    
    private Long id;
    private String title;
    private String description;
    private Boolean completed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    /**
     * 从Entity转换为DTO
     */
    public static TodoResponse fromEntity(Todo todo) {
        if (todo == null) {
            return null;
        }
        return new TodoResponse(
            todo.getId(),
            todo.getTitle(),
            todo.getDescription(),
            todo.getCompleted(),
            todo.getCreatedAt(),
            todo.getUpdatedAt()
        );
    }
}

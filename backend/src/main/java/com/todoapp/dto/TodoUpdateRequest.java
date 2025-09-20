package com.todoapp.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 更新Todo请求DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoUpdateRequest {
    
    @Size(min = 1, max = 255, message = "标题长度必须在1-255个字符之间")
    private String title;
    
    @Size(max = 1000, message = "描述长度不能超过1000个字符")
    private String description;
    
    private Boolean completed;
}

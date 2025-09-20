package com.todoapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 创建Todo请求DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoCreateRequest {
    
    @NotBlank(message = "标题不能为空")
    @Size(max = 255, message = "标题长度不能超过255个字符")
    private String title;
    
    @Size(max = 1000, message = "描述长度不能超过1000个字符")
    private String description;
}

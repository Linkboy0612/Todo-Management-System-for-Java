package com.todoapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 删除操作响应DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteResponse {
    
    private Integer deletedCount;
    
    /**
     * 创建删除响应
     */
    public static DeleteResponse of(int count) {
        return new DeleteResponse(count);
    }
}

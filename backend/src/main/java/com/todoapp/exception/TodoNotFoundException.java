package com.todoapp.exception;

/**
 * Todo未找到异常
 */
public class TodoNotFoundException extends RuntimeException {
    
    public TodoNotFoundException(Long id) {
        super("Todo not found with id: " + id);
    }
    
    public TodoNotFoundException(String message) {
        super(message);
    }
}

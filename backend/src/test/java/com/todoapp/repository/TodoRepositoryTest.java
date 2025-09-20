package com.todoapp.repository;

import com.todoapp.entity.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TodoRepository单元测试
 */
@DataJpaTest
@ActiveProfiles("test")
class TodoRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private TodoRepository todoRepository;
    
    private Todo completedTodo;
    private Todo pendingTodo;
    
    @BeforeEach
    void setUp() {
        completedTodo = new Todo("Completed Todo", "This is completed");
        completedTodo.setCompleted(true);
        
        pendingTodo = new Todo("Pending Todo", "This is pending");
        pendingTodo.setCompleted(false);
        
        entityManager.persistAndFlush(completedTodo);
        entityManager.persistAndFlush(pendingTodo);
        entityManager.clear();
    }
    
    @Test
    void shouldFindByCompleted() {
        // When
        List<Todo> completedTodos = todoRepository.findByCompleted(true);
        List<Todo> pendingTodos = todoRepository.findByCompleted(false);
        
        // Then
        assertEquals(1, completedTodos.size());
        assertEquals("Completed Todo", completedTodos.get(0).getTitle());
        assertTrue(completedTodos.get(0).getCompleted());
        
        assertEquals(1, pendingTodos.size());
        assertEquals("Pending Todo", pendingTodos.get(0).getTitle());
        assertFalse(pendingTodos.get(0).getCompleted());
    }
    
    @Test
    void shouldFindByCompletedOrderByCreatedAtDesc() {
        // When
        List<Todo> allTodos = todoRepository.findAllByOrderByCreatedAtDesc();
        
        // Then
        assertEquals(2, allTodos.size());
        // 最新创建的应该在前面
    }
    
    @Test
    void shouldCountByCompleted() {
        // When
        long completedCount = todoRepository.countByCompleted(true);
        long pendingCount = todoRepository.countByCompleted(false);
        
        // Then
        assertEquals(1L, completedCount);
        assertEquals(1L, pendingCount);
    }
    
    @Test
    void shouldDeleteByCompletedTrue() {
        // When
        int deletedCount = todoRepository.deleteByCompletedTrue();
        entityManager.flush();
        entityManager.clear();
        
        // Then
        assertEquals(1, deletedCount);
        
        List<Todo> remainingTodos = todoRepository.findAll();
        assertEquals(1, remainingTodos.size());
        assertEquals("Pending Todo", remainingTodos.get(0).getTitle());
        assertFalse(remainingTodos.get(0).getCompleted());
    }
    
    @Test
    void shouldFindByTitleContainingIgnoreCase() {
        // When
        List<Todo> foundTodos = todoRepository.findByTitleContainingIgnoreCase("completed");
        
        // Then
        assertEquals(1, foundTodos.size());
        assertEquals("Completed Todo", foundTodos.get(0).getTitle());
    }
}

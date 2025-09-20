package com.todoapp.service;

import com.todoapp.dto.TodoCreateRequest;
import com.todoapp.dto.TodoResponse;
import com.todoapp.dto.TodoUpdateRequest;
import com.todoapp.entity.Todo;
import com.todoapp.exception.TodoNotFoundException;
import com.todoapp.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * TodoService单元测试
 */
@ExtendWith(MockitoExtension.class)
class TodoServiceTest {
    
    @Mock
    private TodoRepository todoRepository;
    
    @InjectMocks
    private TodoService todoService;
    
    private Todo sampleTodo;
    
    @BeforeEach
    void setUp() {
        sampleTodo = new Todo();
        sampleTodo.setId(1L);
        sampleTodo.setTitle("Test Todo");
        sampleTodo.setDescription("Test Description");
        sampleTodo.setCompleted(false);
        sampleTodo.setCreatedAt(LocalDateTime.now());
        sampleTodo.setUpdatedAt(LocalDateTime.now());
    }
    
    @Test
    void shouldCreateTodoSuccessfully() {
        // Given
        TodoCreateRequest request = new TodoCreateRequest("New Todo", "Description");
        Todo todoToSave = new Todo("New Todo", "Description");
        Todo savedTodo = new Todo("New Todo", "Description");
        savedTodo.setId(1L);
        savedTodo.setCreatedAt(LocalDateTime.now());
        savedTodo.setUpdatedAt(LocalDateTime.now());
        
        when(todoRepository.save(any(Todo.class))).thenReturn(savedTodo);
        
        // When
        TodoResponse result = todoService.createTodo(request);
        
        // Then
        assertNotNull(result);
        assertEquals("New Todo", result.getTitle());
        assertEquals("Description", result.getDescription());
        assertEquals(false, result.getCompleted());
        verify(todoRepository, times(1)).save(any(Todo.class));
    }
    
    @Test
    void shouldGetTodoByIdSuccessfully() {
        // Given
        when(todoRepository.findById(1L)).thenReturn(Optional.of(sampleTodo));
        
        // When
        TodoResponse result = todoService.getTodoById(1L);
        
        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Todo", result.getTitle());
        verify(todoRepository, times(1)).findById(1L);
    }
    
    @Test
    void shouldThrowExceptionWhenTodoNotFound() {
        // Given
        when(todoRepository.findById(anyLong())).thenReturn(Optional.empty());
        
        // When & Then
        assertThrows(TodoNotFoundException.class, () -> {
            todoService.getTodoById(999L);
        });
        verify(todoRepository, times(1)).findById(999L);
    }
    
    @Test
    void shouldGetAllTodosWithoutFilter() {
        // Given
        List<Todo> todos = Arrays.asList(sampleTodo);
        when(todoRepository.findAllByOrderByCreatedAtDesc()).thenReturn(todos);
        
        // When
        List<TodoResponse> result = todoService.getAllTodos(null);
        
        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Todo", result.get(0).getTitle());
        verify(todoRepository, times(1)).findAllByOrderByCreatedAtDesc();
    }
    
    @Test
    void shouldGetAllTodosWithCompletedFilter() {
        // Given
        List<Todo> todos = Arrays.asList(sampleTodo);
        when(todoRepository.findByCompletedOrderByCreatedAtDesc(false)).thenReturn(todos);
        
        // When
        List<TodoResponse> result = todoService.getAllTodos(false);
        
        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(todoRepository, times(1)).findByCompletedOrderByCreatedAtDesc(false);
    }
    
    @Test
    void shouldUpdateTodoSuccessfully() {
        // Given
        TodoUpdateRequest request = new TodoUpdateRequest("Updated Title", "Updated Description", true);
        Todo updatedTodo = new Todo();
        updatedTodo.setId(1L);
        updatedTodo.setTitle("Updated Title");
        updatedTodo.setDescription("Updated Description");
        updatedTodo.setCompleted(true);
        updatedTodo.setCreatedAt(LocalDateTime.now());
        updatedTodo.setUpdatedAt(LocalDateTime.now());
        
        when(todoRepository.findById(1L)).thenReturn(Optional.of(sampleTodo), Optional.of(updatedTodo));
        when(todoRepository.saveAndFlush(any(Todo.class))).thenReturn(updatedTodo);
        
        // When
        TodoResponse result = todoService.updateTodo(1L, request);
        
        // Then
        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        verify(todoRepository, times(2)).findById(1L); // 调用两次：第一次获取，第二次重新加载
        verify(todoRepository, times(1)).saveAndFlush(any(Todo.class));
    }
    
    @Test
    void shouldToggleTodoStatusSuccessfully() {
        // Given
        Todo toggledTodo = new Todo();
        toggledTodo.setId(1L);
        toggledTodo.setTitle("Test Todo");
        toggledTodo.setDescription("Test Description");
        toggledTodo.setCompleted(true); // 切换后的状态
        toggledTodo.setCreatedAt(LocalDateTime.now());
        toggledTodo.setUpdatedAt(LocalDateTime.now());
        
        when(todoRepository.findById(1L)).thenReturn(Optional.of(sampleTodo), Optional.of(toggledTodo));
        when(todoRepository.saveAndFlush(any(Todo.class))).thenReturn(toggledTodo);
        
        // When
        TodoResponse result = todoService.toggleTodoStatus(1L);
        
        // Then
        assertNotNull(result);
        assertEquals(true, result.getCompleted());
        verify(todoRepository, times(2)).findById(1L); // 调用两次：第一次获取，第二次重新加载
        verify(todoRepository, times(1)).saveAndFlush(any(Todo.class));
    }
    
    @Test
    void shouldDeleteTodoSuccessfully() {
        // Given
        when(todoRepository.existsById(1L)).thenReturn(true);
        
        // When
        todoService.deleteTodo(1L);
        
        // Then
        verify(todoRepository, times(1)).existsById(1L);
        verify(todoRepository, times(1)).deleteById(1L);
    }
    
    @Test
    void shouldThrowExceptionWhenDeletingNonExistentTodo() {
        // Given
        when(todoRepository.existsById(999L)).thenReturn(false);
        
        // When & Then
        assertThrows(TodoNotFoundException.class, () -> {
            todoService.deleteTodo(999L);
        });
        verify(todoRepository, times(1)).existsById(999L);
        verify(todoRepository, never()).deleteById(anyLong());
    }
    
    @Test
    void shouldDeleteCompletedTodosSuccessfully() {
        // Given
        when(todoRepository.deleteByCompletedTrue()).thenReturn(3);
        
        // When
        int result = todoService.deleteCompletedTodos();
        
        // Then
        assertEquals(3, result);
        verify(todoRepository, times(1)).deleteByCompletedTrue();
    }
    
    @Test
    void shouldDeleteAllTodosSuccessfully() {
        // Given
        when(todoRepository.count()).thenReturn(5L);
        
        // When
        int result = todoService.deleteAllTodos();
        
        // Then
        assertEquals(5, result);
        verify(todoRepository, times(1)).count();
        verify(todoRepository, times(1)).deleteAll();
    }
    
    @Test
    void shouldGetStatsSuccessfully() {
        // Given
        when(todoRepository.count()).thenReturn(10L);
        when(todoRepository.countByCompleted(true)).thenReturn(6L);
        when(todoRepository.countByCompleted(false)).thenReturn(4L);
        
        // When
        TodoService.TodoStatsResponse result = todoService.getStats();
        
        // Then
        assertNotNull(result);
        assertEquals(10L, result.getTotal());
        assertEquals(6L, result.getCompleted());
        assertEquals(4L, result.getPending());
        verify(todoRepository, times(1)).count();
        verify(todoRepository, times(1)).countByCompleted(true);
        verify(todoRepository, times(1)).countByCompleted(false);
    }
}

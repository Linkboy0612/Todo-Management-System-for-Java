package com.todoapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todoapp.dto.TodoCreateRequest;
import com.todoapp.dto.TodoUpdateRequest;
import com.todoapp.entity.Todo;
import com.todoapp.repository.TodoRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * TodoController集成测试
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
class TodoControllerIntegrationTest {
    
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @Autowired
    private TodoRepository todoRepository;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private MockMvc mockMvc;
    
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        todoRepository.deleteAll();
    }
    
    @Test
    @Order(1)
    void shouldCreateTodo() throws Exception {
        // Given
        TodoCreateRequest request = new TodoCreateRequest("Test Todo", "Test Description");
        
        // When & Then
        mockMvc.perform(post("/api/v1/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value(201))
                .andExpect(jsonPath("$.message").value("Todo created successfully"))
                .andExpect(jsonPath("$.data.title").value("Test Todo"))
                .andExpect(jsonPath("$.data.description").value("Test Description"))
                .andExpect(jsonPath("$.data.completed").value(false))
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.createdAt").exists())
                .andExpect(jsonPath("$.data.updatedAt").exists());
    }
    
    @Test
    @Order(2)
    void shouldGetAllTodos() throws Exception {
        // Given
        Todo todo1 = new Todo("Todo 1", "Description 1");
        Todo todo2 = new Todo("Todo 2", "Description 2");
        todo2.setCompleted(true);
        
        todoRepository.save(todo1);
        todoRepository.save(todo2);
        
        // When & Then - 获取所有Todo
        mockMvc.perform(get("/api/v1/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data", hasSize(2)));
        
        // When & Then - 根据状态过滤
        mockMvc.perform(get("/api/v1/todos").param("completed", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].completed").value(true));
        
        mockMvc.perform(get("/api/v1/todos").param("completed", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].completed").value(false));
    }
    
    @Test
    @Order(3)
    void shouldGetTodoById() throws Exception {
        // Given
        Todo todo = new Todo("Test Todo", "Test Description");
        Todo savedTodo = todoRepository.save(todo);
        
        // When & Then
        mockMvc.perform(get("/api/v1/todos/{id}", savedTodo.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.id").value(savedTodo.getId()))
                .andExpect(jsonPath("$.data.title").value("Test Todo"))
                .andExpect(jsonPath("$.data.description").value("Test Description"));
    }
    
    @Test
    @Order(4)
    void shouldReturnNotFoundForNonExistentTodo() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/todos/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Todo not found with id: 999"));
    }
    
    @Test
    @Order(5)
    void shouldUpdateTodo() throws Exception {
        // Given
        Todo todo = new Todo("Original Title", "Original Description");
        Todo savedTodo = todoRepository.save(todo);
        
        TodoUpdateRequest request = new TodoUpdateRequest("Updated Title", "Updated Description", true);
        
        // When & Then
        mockMvc.perform(put("/api/v1/todos/{id}", savedTodo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Todo updated successfully"))
                .andExpect(jsonPath("$.data.id").value(savedTodo.getId()))
                .andExpect(jsonPath("$.data.title").value("Updated Title"))
                .andExpect(jsonPath("$.data.description").value("Updated Description"))
                .andExpect(jsonPath("$.data.completed").value(true));
    }
    
    @Test
    @Order(6)
    void shouldToggleTodoStatus() throws Exception {
        // Given
        Todo todo = new Todo("Test Todo", "Test Description");
        Todo savedTodo = todoRepository.save(todo);
        
        // When & Then
        mockMvc.perform(patch("/api/v1/todos/{id}/toggle", savedTodo.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Todo status toggled successfully"))
                .andExpect(jsonPath("$.data.completed").value(true));
    }
    
    @Test
    @Order(7)
    void shouldDeleteTodo() throws Exception {
        // Given
        Todo todo = new Todo("Test Todo", "Test Description");
        Todo savedTodo = todoRepository.save(todo);
        
        // When & Then
        mockMvc.perform(delete("/api/v1/todos/{id}", savedTodo.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Todo deleted successfully"));
        
        // 验证Todo已被删除
        mockMvc.perform(get("/api/v1/todos/{id}", savedTodo.getId()))
                .andExpect(status().isNotFound());
    }
    
    @Test
    @Order(8)
    void shouldDeleteCompletedTodos() throws Exception {
        // Given
        Todo todo1 = new Todo("Todo 1", "Description 1");
        Todo todo2 = new Todo("Todo 2", "Description 2");
        todo2.setCompleted(true);
        Todo todo3 = new Todo("Todo 3", "Description 3");
        todo3.setCompleted(true);
        
        todoRepository.save(todo1);
        todoRepository.save(todo2);
        todoRepository.save(todo3);
        
        // When & Then
        mockMvc.perform(delete("/api/v1/todos/completed"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Completed todos deleted successfully"))
                .andExpect(jsonPath("$.data.deletedCount").value(2));
        
        // 验证只剩下未完成的Todo
        mockMvc.perform(get("/api/v1/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].completed").value(false));
    }
    
    @Test
    @Order(9)
    void shouldDeleteAllTodos() throws Exception {
        // Given
        todoRepository.save(new Todo("Todo 1", "Description 1"));
        todoRepository.save(new Todo("Todo 2", "Description 2"));
        todoRepository.save(new Todo("Todo 3", "Description 3"));
        
        // When & Then
        mockMvc.perform(delete("/api/v1/todos/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("All todos deleted successfully"))
                .andExpect(jsonPath("$.data.deletedCount").value(3));
        
        // 验证所有Todo都被删除
        mockMvc.perform(get("/api/v1/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(0)));
    }
    
    @Test
    @Order(10)
    void shouldGetStats() throws Exception {
        // Given
        Todo todo1 = new Todo("Todo 1", "Description 1");
        Todo todo2 = new Todo("Todo 2", "Description 2");
        todo2.setCompleted(true);
        Todo todo3 = new Todo("Todo 3", "Description 3");
        
        todoRepository.save(todo1);
        todoRepository.save(todo2);
        todoRepository.save(todo3);
        
        // When & Then
        mockMvc.perform(get("/api/v1/todos/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.total").value(3))
                .andExpect(jsonPath("$.data.completed").value(1))
                .andExpect(jsonPath("$.data.pending").value(2));
    }
    
    @Test
    @Order(11)
    void shouldValidateCreateRequest() throws Exception {
        // Given - 空标题
        TodoCreateRequest request = new TodoCreateRequest("", "Test Description");
        
        // When & Then
        mockMvc.perform(post("/api/v1/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("Validation failed"));
    }
}

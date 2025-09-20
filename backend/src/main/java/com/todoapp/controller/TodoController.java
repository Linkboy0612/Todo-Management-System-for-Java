package com.todoapp.controller;

import com.todoapp.dto.*;
import com.todoapp.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Todo API控制器
 */
@RestController
@RequestMapping("/api/v1/todos")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Todo Management", description = "待办事项管理API")
public class TodoController {
    
    private final TodoService todoService;
    
    /**
     * 获取所有待办事项
     */
    @GetMapping
    @Operation(summary = "获取所有待办事项", description = "获取待办事项列表，可选择按完成状态过滤")
    public ApiResponse<List<TodoResponse>> getAllTodos(
            @Parameter(description = "过滤条件：true=已完成，false=未完成，不传=全部")
            @RequestParam(required = false) Boolean completed) {
        
        log.info("GET /api/v1/todos - completed: {}", completed);
        
        List<TodoResponse> todos = todoService.getAllTodos(completed);
        return ApiResponse.success(todos);
    }
    
    /**
     * 根据ID获取待办事项
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取单个待办事项", description = "根据ID获取待办事项详情")
    public ApiResponse<TodoResponse> getTodoById(
            @Parameter(description = "待办事项ID")
            @PathVariable Long id) {
        
        log.info("GET /api/v1/todos/{}", id);
        
        TodoResponse todo = todoService.getTodoById(id);
        return ApiResponse.success(todo);
    }
    
    /**
     * 创建待办事项
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "创建待办事项", description = "创建新的待办事项")
    public ApiResponse<TodoResponse> createTodo(
            @Valid @RequestBody TodoCreateRequest request) {
        
        log.info("POST /api/v1/todos - title: {}", request.getTitle());
        
        TodoResponse todo = todoService.createTodo(request);
        return ApiResponse.created("Todo created successfully", todo);
    }
    
    /**
     * 更新待办事项
     */
    @PutMapping("/{id}")
    @Operation(summary = "更新待办事项", description = "更新待办事项信息")
    public ApiResponse<TodoResponse> updateTodo(
            @Parameter(description = "待办事项ID")
            @PathVariable Long id,
            @Valid @RequestBody TodoUpdateRequest request) {
        
        log.info("PUT /api/v1/todos/{} - title: {}", id, request.getTitle());
        
        TodoResponse todo = todoService.updateTodo(id, request);
        return ApiResponse.success("Todo updated successfully", todo);
    }
    
    /**
     * 切换待办事项状态
     */
    @PatchMapping("/{id}/toggle")
    @Operation(summary = "切换待办事项状态", description = "切换待办事项的完成状态")
    public ApiResponse<TodoResponse> toggleTodoStatus(
            @Parameter(description = "待办事项ID")
            @PathVariable Long id) {
        
        log.info("PATCH /api/v1/todos/{}/toggle", id);
        
        TodoResponse todo = todoService.toggleTodoStatus(id);
        return ApiResponse.success("Todo status toggled successfully", todo);
    }
    
    /**
     * 删除待办事项
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除待办事项", description = "删除指定的待办事项")
    public ApiResponse<Void> deleteTodo(
            @Parameter(description = "待办事项ID")
            @PathVariable Long id) {
        
        log.info("DELETE /api/v1/todos/{}", id);
        
        todoService.deleteTodo(id);
        return ApiResponse.success("Todo deleted successfully");
    }
    
    /**
     * 批量删除已完成的待办事项
     */
    @DeleteMapping("/completed")
    @Operation(summary = "删除已完成的待办事项", description = "批量删除所有已完成的待办事项")
    public ApiResponse<DeleteResponse> deleteCompletedTodos() {
        
        log.info("DELETE /api/v1/todos/completed");
        
        int deletedCount = todoService.deleteCompletedTodos();
        return ApiResponse.success("Completed todos deleted successfully", 
                DeleteResponse.of(deletedCount));
    }
    
    /**
     * 删除所有待办事项
     */
    @DeleteMapping("/all")
    @Operation(summary = "删除所有待办事项", description = "删除所有待办事项")
    public ApiResponse<DeleteResponse> deleteAllTodos() {
        
        log.info("DELETE /api/v1/todos/all");
        
        int deletedCount = todoService.deleteAllTodos();
        return ApiResponse.success("All todos deleted successfully", 
                DeleteResponse.of(deletedCount));
    }
    
    /**
     * 获取统计信息
     */
    @GetMapping("/stats")
    @Operation(summary = "获取统计信息", description = "获取待办事项统计数据")
    public ApiResponse<TodoService.TodoStatsResponse> getStats() {
        
        log.info("GET /api/v1/todos/stats");
        
        TodoService.TodoStatsResponse stats = todoService.getStats();
        return ApiResponse.success(stats);
    }
}

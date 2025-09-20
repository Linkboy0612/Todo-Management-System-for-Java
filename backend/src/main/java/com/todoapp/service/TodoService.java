package com.todoapp.service;

import com.todoapp.dto.TodoCreateRequest;
import com.todoapp.dto.TodoResponse;
import com.todoapp.dto.TodoUpdateRequest;
import com.todoapp.entity.Todo;
import com.todoapp.exception.TodoNotFoundException;
import com.todoapp.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Todo业务逻辑服务
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TodoService {
    
    private final TodoRepository todoRepository;
    
    /**
     * 获取所有待办事项
     * @param completed 过滤条件，null表示获取全部
     * @return Todo列表
     */
    @Transactional(readOnly = true)
    public List<TodoResponse> getAllTodos(Boolean completed) {
        log.debug("Getting all todos with completed filter: {}", completed);
        
        List<Todo> todos;
        if (completed == null) {
            todos = todoRepository.findAllByOrderByCreatedAtDesc();
        } else {
            todos = todoRepository.findByCompletedOrderByCreatedAtDesc(completed);
        }
        
        return todos.stream()
                .map(TodoResponse::fromEntity)
                .collect(Collectors.toList());
    }
    
    /**
     * 根据ID获取待办事项
     * @param id 待办事项ID
     * @return Todo详情
     * @throws TodoNotFoundException 当Todo不存在时
     */
    @Transactional(readOnly = true)
    public TodoResponse getTodoById(Long id) {
        log.debug("Getting todo by id: {}", id);
        
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
        
        return TodoResponse.fromEntity(todo);
    }
    
    /**
     * 创建新的待办事项
     * @param request 创建请求
     * @return 创建的Todo
     */
    public TodoResponse createTodo(TodoCreateRequest request) {
        log.debug("Creating new todo with title: {}", request.getTitle());
        
        Todo todo = new Todo(request.getTitle(), request.getDescription());
        Todo savedTodo = todoRepository.save(todo);
        
        log.info("Todo created successfully with id: {}", savedTodo.getId());
        return TodoResponse.fromEntity(savedTodo);
    }
    
    /**
     * 更新待办事项
     * @param id 待办事项ID
     * @param request 更新请求
     * @return 更新后的Todo
     * @throws TodoNotFoundException 当Todo不存在时
     */
    public TodoResponse updateTodo(Long id, TodoUpdateRequest request) {
        log.debug("Updating todo with id: {}", id);
        
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
        
        // 更新字段（只更新非null字段）
        if (request.getTitle() != null) {
            todo.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            todo.setDescription(request.getDescription());
        }
        if (request.getCompleted() != null) {
            todo.setCompleted(request.getCompleted());
        }

        // 强制更新时间，避免字段未变化时Hibernate不触发脏检查
        todo.setUpdatedAt(LocalDateTime.now());

        // 使用saveAndFlush确保@UpdateTimestamp立即生效
        Todo updatedTodo = todoRepository.saveAndFlush(todo);
        log.info("Todo updated successfully with id: {}", updatedTodo.getId());
        
        // 重新加载以拿到数据库生成的最新时间戳
        Todo reloaded = todoRepository.findById(updatedTodo.getId())
                .orElse(updatedTodo);
        return TodoResponse.fromEntity(reloaded);
    }
    
    /**
     * 切换待办事项完成状态
     * @param id 待办事项ID
     * @return 更新后的Todo
     * @throws TodoNotFoundException 当Todo不存在时
     */
    public TodoResponse toggleTodoStatus(Long id) {
        log.debug("Toggling todo status with id: {}", id);
        
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
        
        todo.toggleCompleted();
        // 使用saveAndFlush确保更新时间立即回传
        Todo updatedTodo = todoRepository.saveAndFlush(todo);
        
        log.info("Todo status toggled successfully with id: {}, new status: {}", 
                updatedTodo.getId(), updatedTodo.getCompleted());
        
        // 重新加载以拿到数据库生成的最新时间戳
        Todo reloaded = todoRepository.findById(updatedTodo.getId())
                .orElse(updatedTodo);
        return TodoResponse.fromEntity(reloaded);
    }
    
    /**
     * 删除待办事项
     * @param id 待办事项ID
     * @throws TodoNotFoundException 当Todo不存在时
     */
    public void deleteTodo(Long id) {
        log.debug("Deleting todo with id: {}", id);
        
        if (!todoRepository.existsById(id)) {
            throw new TodoNotFoundException(id);
        }
        
        todoRepository.deleteById(id);
        log.info("Todo deleted successfully with id: {}", id);
    }
    
    /**
     * 批量删除已完成的待办事项
     * @return 删除的数量
     */
    public int deleteCompletedTodos() {
        log.debug("Deleting all completed todos");
        
        int deletedCount = todoRepository.deleteByCompletedTrue();
        log.info("Deleted {} completed todos", deletedCount);
        
        return deletedCount;
    }
    
    /**
     * 删除所有待办事项
     * @return 删除的数量
     */
    public int deleteAllTodos() {
        log.debug("Deleting all todos");
        
        long totalCount = todoRepository.count();
        todoRepository.deleteAll();
        
        log.info("Deleted all {} todos", totalCount);
        return (int) totalCount;
    }
    
    /**
     * 获取统计信息
     * @return 统计数据
     */
    @Transactional(readOnly = true)
    public TodoStatsResponse getStats() {
        log.debug("Getting todo statistics");
        
        long totalCount = todoRepository.count();
        long completedCount = todoRepository.countByCompleted(true);
        long pendingCount = todoRepository.countByCompleted(false);
        
        return new TodoStatsResponse(totalCount, completedCount, pendingCount);
    }
    
    /**
     * 统计信息响应类
     */
    public static class TodoStatsResponse {
        private final long total;
        private final long completed;
        private final long pending;
        
        public TodoStatsResponse(long total, long completed, long pending) {
            this.total = total;
            this.completed = completed;
            this.pending = pending;
        }
        
        public long getTotal() { return total; }
        public long getCompleted() { return completed; }
        public long getPending() { return pending; }
    }
}

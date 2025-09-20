package com.todoapp.repository;

import com.todoapp.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Todo数据访问接口
 */
@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    
    /**
     * 根据完成状态查找Todo列表
     * @param completed 完成状态
     * @return Todo列表
     */
    List<Todo> findByCompleted(Boolean completed);
    
    /**
     * 根据完成状态查找Todo列表，按创建时间倒序排列
     * @param completed 完成状态
     * @return Todo列表
     */
    List<Todo> findByCompletedOrderByCreatedAtDesc(Boolean completed);
    
    /**
     * 查找所有Todo，按创建时间倒序排列
     * @return Todo列表
     */
    List<Todo> findAllByOrderByCreatedAtDesc();
    
    /**
     * 批量删除已完成的Todo
     * @return 删除的记录数
     */
    @Modifying
    @Query("DELETE FROM Todo t WHERE t.completed = true")
    int deleteByCompletedTrue();
    
    /**
     * 统计已完成的Todo数量
     * @return 数量
     */
    long countByCompleted(Boolean completed);
    
    /**
     * 根据标题模糊查询
     * @param title 标题关键字
     * @return Todo列表
     */
    @Query("SELECT t FROM Todo t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%')) ORDER BY t.createdAt DESC")
    List<Todo> findByTitleContainingIgnoreCase(@Param("title") String title);
}

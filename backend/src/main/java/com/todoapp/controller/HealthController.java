package com.todoapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 健康检查控制器
 */
@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Health Check", description = "应用健康检查API")
public class HealthController {
    
    /**
     * 健康检查接口
     */
    @GetMapping("/health")
    @Operation(summary = "健康检查", description = "检查应用运行状态")
    public Map<String, Object> health() {
        log.debug("Health check endpoint accessed");
        
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("timestamp", ZonedDateTime.now());
        response.put("version", "1.0.0");
        
        return response;
    }
}

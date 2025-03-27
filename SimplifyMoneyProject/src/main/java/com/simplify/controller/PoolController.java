package com.simplify.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;

@RestController
public class PoolController {
    
    @Autowired
    private HikariDataSource dataSource;
    
    @GetMapping("/pool-info")
    public Map<String, Object> poolInfo() {
        HikariPoolMXBean pool = dataSource.getHikariPoolMXBean();
        return Map.of(
            "activeConnections", pool.getActiveConnections(),
            "idleConnections", pool.getIdleConnections(),
            "totalConnections", pool.getTotalConnections(),
            "threadsAwaiting", pool.getThreadsAwaitingConnection()
        );
    }
}
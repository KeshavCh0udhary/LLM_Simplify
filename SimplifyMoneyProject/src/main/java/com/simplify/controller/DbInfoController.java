package com.simplify.controller;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zaxxer.hikari.HikariDataSource;

@RestController
@RequestMapping("/api/db-info")
public class DbInfoController {

    @Autowired
    private DataSource dataSource;

    @GetMapping
    public Map<String, Object> getDbInfo() throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData meta = conn.getMetaData();
            return Map.of(
                "database", meta.getDatabaseProductName(),
                "version", meta.getDatabaseProductVersion(),
                "driver", meta.getDriverName(),
                "driverVersion", meta.getDriverVersion(),
                "autocommit", conn.getAutoCommit(),
                "isolationLevel", getIsolationLevelName(conn.getTransactionIsolation()),
                "hikariConfig", ((HikariDataSource) dataSource).getHikariConfigMXBean()
            );
        }
    }

    private String getIsolationLevelName(int level) {
        return switch (level) {
            case Connection.TRANSACTION_NONE -> "NONE";
            case Connection.TRANSACTION_READ_UNCOMMITTED -> "READ_UNCOMMITTED";
            case Connection.TRANSACTION_READ_COMMITTED -> "READ_COMMITTED";
            case Connection.TRANSACTION_REPEATABLE_READ -> "REPEATABLE_READ";
            case Connection.TRANSACTION_SERIALIZABLE -> "SERIALIZABLE";
            default -> "UNKNOWN";
        };
    }
}
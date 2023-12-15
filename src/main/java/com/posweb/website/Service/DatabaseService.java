package com.posweb.website.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void creatUserIfNotExists() {
        String sql = "INSERT INTO user (username, password, is_enable) SELECT 'admin', 'admin', 1 WHERE NOT EXISTS (SELECT * FROM user WHERE username='admin')";
        jdbcTemplate.execute(sql);
    }
}
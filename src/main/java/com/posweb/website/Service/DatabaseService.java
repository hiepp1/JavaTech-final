package com.posweb.website.Service;
import org.mindrot.jbcrypt.BCrypt;

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
        String password = BCrypt.hashpw("admin", BCrypt.gensalt(10));
//        String sql = "INSERT INTO user (username, password, is_enable, role) SELECT 'admin', , 1, 'ADMIN' WHERE NOT EXISTS (SELECT * FROM user WHERE username='admin')";
        String sql = "INSERT INTO user (username, password, is_enable, role) SELECT 'admin', ?, 1, 'ADMIN' WHERE NOT EXISTS (SELECT * FROM user WHERE username='admin')";

        jdbcTemplate.update(sql, password);
    }
}
package com.example.demo.repository;

import com.example.demo.domain.Memo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcMemoRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcMemoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Memo 저장 (id 제외, DB에서 자동 증가)
    public Memo save(Memo memo) {
        String sql = "INSERT INTO memo (text) VALUES (?)"; // id는 자동 증가
        jdbcTemplate.update(sql, memo.getText());
        return memo;
    }

    // 모든 Memo 조회
    public List<Memo> findAll() {
        String sql = "SELECT * FROM memo";
        return jdbcTemplate.query(sql, memoRowMapper());
    }

    // ID로 Memo 조회
    public Memo findById(int id) {
        String sql = "SELECT * FROM memo WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, memoRowMapper(), id);
    }

    // RowMapper 구현
    private RowMapper<Memo> memoRowMapper() {
        return (rs, rowNum) -> new Memo(
            rs.getInt("id"),
            rs.getString("text")
        );
    }
}

package com.github.ignacy123.projectvocabulary.web.repository;

import com.github.ignacy123.projectvocabulary.web.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ignacy on 16.11.16.
 */
@Repository
public class StudentJdbcRepository implements StudentRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public StudentJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User findById(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbcTemplate.queryForObject("SELECT * FROM `student` s WHERE s.id=:id", params, (ResultSet rs, int rowNum) -> {
            User user = new User();
            user.setType(User.Type.STUDENT);
            user.setId(rs.getLong("id"));
            user.setEmail(rs.getString("email"));
            return user;
        });
    }
}

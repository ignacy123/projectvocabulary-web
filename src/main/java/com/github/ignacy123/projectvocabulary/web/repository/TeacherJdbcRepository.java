package com.github.ignacy123.projectvocabulary.web.repository;

import com.github.ignacy123.projectvocabulary.web.domain.Role;
import com.github.ignacy123.projectvocabulary.web.domain.Teacher;
import com.github.ignacy123.projectvocabulary.web.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by ignacy on 30.11.16.
 */
public class TeacherJdbcRepository implements TeacherRepository {

    private static final RowMapper<Teacher> TEACHER_ROW_MAPPER = new RowMapper<Teacher>() {
        @Override
        public Teacher mapRow(ResultSet rs, int rowNum) throws SQLException {
            Teacher teacher = new Teacher();
            Set<Role> roles = new HashSet<>();
            roles.add(Role.TEACHER);
            teacher.setId(rs.getLong("id"));
            teacher.setUserId(rs.getLong("user_id"));
            return teacher;
        }
    };
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public TeacherJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Teacher findById(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbcTemplate.queryForObject("SELECT * FROM `teacher` s WHERE s.id=:id", params, TEACHER_ROW_MAPPER);
    }

    @Override
    public List<Teacher> findAll() {
        return jdbcTemplate.query("SELECT * FROM `teacher`", TEACHER_ROW_MAPPER);
    }



    @Override
    public Teacher save(Teacher teacher) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", teacher.getId());
        params.addValue("user_id", teacher.getUserId());
        final String INSERT_SQL = "insert into `teacher` (id, user_id) " +
                "values (:id, :user_id)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(INSERT_SQL, params, keyHolder);
        teacher.setId((Long) keyHolder.getKey());
        return teacher;


    }
}

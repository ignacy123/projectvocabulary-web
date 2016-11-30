package com.github.ignacy123.projectvocabulary.web.repository;

import com.github.ignacy123.projectvocabulary.web.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ignacy on 30.11.16.
 */
public class TeacherJdbcRepository implements TeacherRepository {
    private static final RowMapper<User> TEACHER_ROW_MAPPER = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setType(User.Type.TEACHER);
            user.setId(rs.getLong("id"));
            user.setEmail(rs.getString("email"));
            return user;
        }
    };
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public TeacherJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User findById(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbcTemplate.queryForObject("SELECT * FROM `teacher` s WHERE s.id=:id", params, TEACHER_ROW_MAPPER);
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM `teacher`", TEACHER_ROW_MAPPER);
    }

    @Override
    public User findByEmail(String email) {
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        return jdbcTemplate.queryForObject("SELECT * FROM `teacher` s WHERE s.email=:email", params, TEACHER_ROW_MAPPER);

    }

    @Override
    public User save(User user) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("email", user.getEmail());
        params.addValue("password", user.getPassword());
        params.addValue("firstName", user.getFirstName());
        params.addValue("lastName", user.getLastName());
        final String INSERT_SQL = "insert into `teacher` (email, password, first_name, last_name) " +
                "values (:email, :password, :firstName, :lastName)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(INSERT_SQL, params, keyHolder);
        user.setId((Long) keyHolder.getKey());
        return user;


    }
}

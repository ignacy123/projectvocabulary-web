package com.github.ignacy123.projectvocabulary.web.repository;

import com.github.ignacy123.projectvocabulary.web.domain.Role;
import com.github.ignacy123.projectvocabulary.web.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ignacy on 11.01.17.
 */
public class RolesJdbcRepository implements RolesRepository{
    Map<Long, Role> role;

    private static final RowMapper<Role> ROLE_ROW_MAPPER = (rs, rowNum) -> {
        Role role = Role.valueOf(rs.getString("role"));
        return role;
    };

    private final NamedParameterJdbcOperations jdbcTemplate;
    @Autowired
    public RolesJdbcRepository(NamedParameterJdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Role> getRoles(Long userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", userId);

        return jdbcTemplate.queryForList("SELECT role FROM `roles` s WHERE s.user_id=:id", params, Role.class );
    }
}

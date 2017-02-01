package com.github.ignacy123.projectvocabulary.web.repository;

import com.github.ignacy123.projectvocabulary.web.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created by ignacy on 11.01.17.
 */
@Repository
public class RolesJdbcRepository implements RolesRepository {

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
	public Set<Role> getRoles(Long userId) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", userId);

		final List<Role> roleList = jdbcTemplate.query("SELECT role FROM `roles` s WHERE s.user_id=:id", params, ROLE_ROW_MAPPER);

		return new HashSet<>(roleList);
	}
}

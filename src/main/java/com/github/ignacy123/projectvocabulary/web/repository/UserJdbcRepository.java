package com.github.ignacy123.projectvocabulary.web.repository;

import com.github.ignacy123.projectvocabulary.web.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ignacy on 16.11.16.
 */
@Repository
public class UserJdbcRepository implements UserRepository {

	private static final RowMapper<User> USER_ROW_MAPPER = (rs, rowNum) -> {
		User user = new User();
		user.setId(rs.getLong("id"));
		user.setEmail(rs.getString("email"));
		user.setEncodedPassword(rs.getString("password"));
		user.setFirstName(rs.getString("first_name"));
		user.setLastName(rs.getString("last_name"));
		return user;
	};
	private final NamedParameterJdbcOperations jdbcTemplate;

	@Autowired
	public UserJdbcRepository(NamedParameterJdbcOperations jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public User findById(Long id) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		return jdbcTemplate.queryForObject("SELECT * FROM `user` s WHERE s.id=:id", params, USER_ROW_MAPPER);
	}

	@Override
	public List<User> findAll() {
		return jdbcTemplate.query("SELECT * FROM `user`", USER_ROW_MAPPER);
	}

	@Override
	public User findByEmail(String email) {
		Map<String, Object> params = new HashMap<>();
		params.put("email", email);
		return jdbcTemplate.queryForObject("SELECT * FROM `user` s WHERE s.email=:email", params, USER_ROW_MAPPER);

	}

	@Override
	public User insert(User user) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("email", user.getEmail());
		params.addValue("password", user.getPassword());
		params.addValue("firstName", user.getFirstName());
		params.addValue("lastName", user.getLastName());
		final String INSERT_SQL =
				"insert into `user` (email, password, first_name, last_name) " + "values (:email, :password, :firstName, :lastName)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(INSERT_SQL, params, keyHolder);
		user.setId((Long) keyHolder.getKey());
		return user;

	}

	@Override
	public void update(User user) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("email", user.getEmail());
		params.addValue("password", user.getPassword());
		params.addValue("firstName", user.getFirstName());
		params.addValue("lastName", user.getLastName());
		params.addValue("id", user.getId());
		final String INSERT_SQL =
				"update `user` set email = :email, password = :password, first_name = :first_name, last_name = :last_name where id = :id";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(INSERT_SQL, params, keyHolder);
		user.setId((Long) keyHolder.getKey());

	}
}

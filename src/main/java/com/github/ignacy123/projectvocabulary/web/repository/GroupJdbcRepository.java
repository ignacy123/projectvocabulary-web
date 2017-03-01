package com.github.ignacy123.projectvocabulary.web.repository;

import com.github.ignacy123.projectvocabulary.web.domain.Group;
import com.github.ignacy123.projectvocabulary.web.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ignacy on 31.01.17.
 */
@Repository
public class GroupJdbcRepository implements GroupRepository {

    private static final RowMapper<Group> GROUP_ROW_MAPPER = (rs, rowNum) -> {
        Group group = new Group();
        group.setId(rs.getLong("id"));
        group.setName(rs.getString("name"));
        group.setTeacherId(rs.getLong("teacher_id"));
        return group;
    };
    private final NamedParameterJdbcOperations jdbcTemplate;

    @Autowired
    public GroupJdbcRepository(NamedParameterJdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Group createNew(Group group) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", group.getId());
        params.addValue("teacherId", group.getTeacherId());
        params.addValue("name", group.getName());
        final String INSERT_SQL =
                "insert into `groups` (Id, teacher_id, name) " + "values (:id, :teacherId, :name)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(INSERT_SQL, params, keyHolder);
        group.setId((Long) keyHolder.getKey());
        return group;

    }

    @Override
    public void update() {

    }

   @Override
    public List<Group> findByTeacherId(Long teacherId) {
        Map<String, Object> params = new HashMap<>();
        params.put("teacher_id", teacherId);
      return jdbcTemplate.query("SELECT * FROM `groups` s WHERE s.teacher_id=:teacher_id", params, GROUP_ROW_MAPPER);
   }

    @Override
    public Group findById(Long id) {

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        Group group = jdbcTemplate.queryForObject("SELECT * FROM `groups` s WHERE s.id=:id", params, GROUP_ROW_MAPPER);
        return group;
    }

    @Override
    public void addToGroup(Long studentId, Long groupId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("student_id", studentId);
        params.addValue("group_id", groupId);
        final String INSERT_SQL =
                "insert into `student_group` (student_id, group_id) " + "values (:student_id, :group_id)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(INSERT_SQL, params, keyHolder);
    }
}

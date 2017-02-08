package com.github.ignacy123.projectvocabulary.web.repository;

import com.github.ignacy123.projectvocabulary.web.domain.Group;
import com.github.ignacy123.projectvocabulary.web.domain.Invitation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ignacy on 31.01.17.
 */
public class InvitationJdbcRepository implements InvitationRepository {



    private static final RowMapper<Invitation> INVITATION_ROW_MAPPER = (rs, rowNum) -> {
        Invitation invitation = new Invitation();
        invitation.setEmail(rs.getString("email"));
        invitation.setName(rs.getString("name"));
        invitation.setUid(rs.getString("uid"));
        invitation.setGroupId(rs.getLong("groupId"));
        return invitation;
    };
    private final NamedParameterJdbcOperations jdbcTemplate;

    @Autowired
    public InvitationJdbcRepository(NamedParameterJdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Invitation save(Invitation invitation) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", invitation.getName());
        params.addValue("uid", invitation.getUid());
        params.addValue("groupId", invitation.getGroupId());
        params.addValue("email", invitation.getEmail());
        final String INSERT_SQL =
                "insert into `invitation` (uid, email, groupId, studentName) " + "values (:uid, :email, :groupId, :name)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(INSERT_SQL, params, keyHolder);
        return invitation;
    }


    @Override
    public Invitation findByUid(String invitationUid) {
        Map<String, Object> params = new HashMap<>();
        params.put("uid", invitationUid);
        return jdbcTemplate.queryForObject("SELECT * FROM `invitation` s WHERE s.uid=:uid", params, INVITATION_ROW_MAPPER);
    }

    @Override
    public void delete(String invitationUid) {
        Map<String, Object> params = new HashMap<>();
        params.put("uid", invitationUid);
        jdbcTemplate.update("DELETE FROM `invitation` WHERE uid=:uid", params);

    }
}

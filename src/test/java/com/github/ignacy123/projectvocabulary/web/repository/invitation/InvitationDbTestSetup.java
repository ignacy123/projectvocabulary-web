package com.github.ignacy123.projectvocabulary.web.repository.invitation;

import com.github.ignacy123.projectvocabulary.web.domain.Invitation;
import com.github.ignacy123.projectvocabulary.web.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * Created by tokruzel on 30/11/16.
 */
@Configuration
@Import(DbTestConfig.class)
public class InvitationDbTestSetup{

    @Bean
    public InvitationRepository invitationRepository(NamedParameterJdbcOperations jdbcTemplate) {
        return new InvitationJdbcRepository(jdbcTemplate);
    }

}

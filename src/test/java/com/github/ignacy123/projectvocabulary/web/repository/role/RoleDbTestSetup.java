package com.github.ignacy123.projectvocabulary.web.repository.user;

import com.github.ignacy123.projectvocabulary.web.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

/**
 * Created by tokruzel on 30/11/16.
 */
@Configuration
@Import(DbTestConfig.class)
public class RoleDbTestSetup{

    @Bean
    public RolesRepository studentRepository(NamedParameterJdbcOperations jdbcTemplate) {
        return new RolesJdbcRepository(jdbcTemplate);
    }

}

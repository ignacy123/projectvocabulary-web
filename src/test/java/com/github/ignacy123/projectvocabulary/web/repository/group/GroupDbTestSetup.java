package com.github.ignacy123.projectvocabulary.web.repository.group;

import com.github.ignacy123.projectvocabulary.web.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

/**
 * Created by ignacy on 30/11/16.
 */
@Configuration
@Import(DbTestConfig.class)
public class GroupDbTestSetup{

    @Bean
    public GroupRepository groupRepository(NamedParameterJdbcOperations jdbcTemplate) {
        return new GroupJdbcRepository(jdbcTemplate);
    }

}
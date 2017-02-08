package com.github.ignacy123.projectvocabulary.web.repository.teacher;


import com.github.ignacy123.projectvocabulary.web.domain.Teacher;
import com.github.ignacy123.projectvocabulary.web.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * Created by ignacy on 30/11/16.
 */
@Configuration
@Import(DbTestConfig.class)
public class TeacherDbTestSetup{

    @Bean
    public TeacherRepository teacherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        return new TeacherJdbcRepository(jdbcTemplate);
    }

}
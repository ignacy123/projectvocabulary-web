package com.github.ignacy123.projectvocabulary.web.repository.student;

import com.github.ignacy123.projectvocabulary.web.repository.DbTestConfig;
import com.github.ignacy123.projectvocabulary.web.repository.StudentJdbcRepository;
import com.github.ignacy123.projectvocabulary.web.repository.StudentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by tokruzel on 30/11/16.
 */
@Configuration
@Import(DbTestConfig.class)
public class StudentRepositoryDbTestConfig {

	@Bean
	public StudentRepository studentRepository(NamedParameterJdbcOperations jdbcTemplate) {
		return new StudentJdbcRepository(jdbcTemplate);
	}

}

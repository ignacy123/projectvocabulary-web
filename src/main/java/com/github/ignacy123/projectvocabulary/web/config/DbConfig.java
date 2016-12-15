package com.github.ignacy123.projectvocabulary.web.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Created by ignacy on 14.12.16.
 */
@Configuration
public class DbConfig {

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	@Primary
	public DataSource appDataSource() {
		return DataSourceBuilder.create()
				.build();
	}

	@Bean
	public NamedParameterJdbcOperations namedParameterJdbcTemplate() throws NamingException {
		return new NamedParameterJdbcTemplate(appDataSource());
	}
}

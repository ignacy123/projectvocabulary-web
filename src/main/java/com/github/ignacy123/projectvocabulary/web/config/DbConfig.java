package com.github.ignacy123.projectvocabulary.web.config;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.ext.mysql.MySqlMetadataHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.time.ZoneId;
import java.util.TimeZone;

/**
 * Created by ignacy on 14.12.16.
 */
@Configuration
@PropertySources({ @PropertySource("classpath:application.properties") })
public class DbConfig {

    @Autowired
    private Environment environment;

    @Bean
    public DataSource appDataSource() {
        MysqlDataSource dataSource = new MysqlConnectionPoolDataSource();

        dataSource.setURL(environment.getRequiredProperty("spring.datasource.url"));
        dataSource.setUser(environment.getRequiredProperty("spring.datasource.username"));
        dataSource.setPassword(environment.getRequiredProperty("spring.datasource.password"));

        return dataSource;
    }

    @Bean
    public DatabaseConfigBean dbUnitDatabaseConfig() {
        DatabaseConfigBean conf = new DatabaseConfigBean();
        conf.setDatatypeFactory(new MySqlDataTypeFactory());
        conf.setMetadataHandler(new MySqlMetadataHandler());
        return conf;
    }

    @Bean
    public DatabaseDataSourceConnectionFactoryBean dbUnitDatabaseConnection() {
        DatabaseDataSourceConnectionFactoryBean factory = new DatabaseDataSourceConnectionFactoryBean();
        factory.setDatabaseConfig(dbUnitDatabaseConfig());
        factory.setDataSource(appDataSource());

        return factory;
    }

    @Bean
    public NamedParameterJdbcOperations namedParameterJdbcTemplate() throws NamingException {
        return new NamedParameterJdbcTemplate(appDataSource());
    }
}

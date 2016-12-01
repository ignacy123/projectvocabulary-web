package com.github.ignacy123.projectvocabulary.web.repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by tokruzel on 01/12/16.
 */
public class DbTestUtil {

	public static void resetAutoIncrementInTable(DataSource dataSource, String... tableNames) throws SQLException {
		String resetSqlTemplate = "ALTER TABLE %s AUTO_INCREMENT = 1";
		Connection dbConnection = dataSource.getConnection();
		//Create SQL statements that reset the auto increment columns and invoke
		//the created SQL statements.
		for (String resetSqlArgument : tableNames) {
			try (Statement statement = dbConnection.createStatement()) {
				String resetSql = String.format(resetSqlTemplate, resetSqlArgument);
				statement.execute(resetSql);
			}
		}
	}
}

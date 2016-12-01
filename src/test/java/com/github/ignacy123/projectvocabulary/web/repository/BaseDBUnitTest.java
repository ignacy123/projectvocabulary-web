package com.github.ignacy123.projectvocabulary.web.repository;

import com.github.ignacy123.projectvocabulary.web.repository.BaseDBUnitTest.TestModifier;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.dataset.ReplacementDataSetModifier;
import org.dbunit.dataset.ReplacementDataSet;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

@DbUnitConfiguration(databaseConnection = "dbUnitDatabaseConnection")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
								TransactionDbUnitTestExecutionListener.class })
@ExpectedDatabase(modifiers = TestModifier.class)
public abstract class BaseDBUnitTest {

	static class TestModifier extends ReplacementDataSetModifier {

		@Override
		protected void addReplacements(ReplacementDataSet replacementDataSet) {
			replacementDataSet.addReplacementObject("[null]", null);
			replacementDataSet.addReplacementObject("[NULL]", null);
			replacementDataSet.addReplacementObject("[SYSTIMESTAMP]", new Timestamp(LocalDateTime.now()
					.atZone(ZoneId.systemDefault())
					.toInstant()
					.toEpochMilli()));
			replacementDataSet.addReplacementObject("[SYSDATE]", new Date(System.currentTimeMillis()));
		}
	}

}

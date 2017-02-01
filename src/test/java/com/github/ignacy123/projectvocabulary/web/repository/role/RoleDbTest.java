package com.github.ignacy123.projectvocabulary.web.repository.role;

import com.github.ignacy123.projectvocabulary.web.domain.Role;
import com.github.ignacy123.projectvocabulary.web.repository.BaseDBUnitTest;
import com.github.ignacy123.projectvocabulary.web.repository.RolesJdbcRepository;
import com.github.ignacy123.projectvocabulary.web.repository.user.RoleDbTestSetup;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * Created by ignacy on 11.01.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RoleDbTestSetup.class })
public class RoleDbTest extends BaseDBUnitTest {

	@Autowired
	RolesJdbcRepository repository;

	@Test
	@DatabaseSetup(value = "classpath:repository/role/RoleDbTest_givesRole_input.xml")
	public void givesRoles() {
		Set<Role> roles = repository.getRoles(1L);

		assertTrue(roles.contains(Role.TEACHER));
	}

	@Test
	@DatabaseSetup(value = "classpath:repository/role/RoleDbTest_givesMultipleRoles_input.xml")
	public void givesMultipleRoles() {
		Set<Role> roles = repository.getRoles(1L);

		// the order is not sure
		assertTrue(roles.contains(Role.STUDENT));
		assertTrue(roles.contains(Role.TEACHER));
	}
}

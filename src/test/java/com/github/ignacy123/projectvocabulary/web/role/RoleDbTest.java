package com.github.ignacy123.projectvocabulary.web.role;

import com.github.ignacy123.projectvocabulary.web.domain.Role;
import com.github.ignacy123.projectvocabulary.web.domain.User;
import com.github.ignacy123.projectvocabulary.web.repository.RolesJdbcRepository;
import com.github.ignacy123.projectvocabulary.web.repository.user.RoleDbTestSetup;
import com.github.ignacy123.projectvocabulary.web.repository.user.UserRepositoryDbTestConfig;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

import java.util.List;

/**
 * Created by ignacy on 11.01.17.
 */@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RoleDbTestSetup.class})
public class RoleDbTest {
    @Autowired
    RolesJdbcRepository repository;

    @Test
    @DatabaseSetup(value = "classpath:repository/role/RoleDbTest_givesRole_input.xml")
    @ExpectedDatabase(value = "classpath:repository/role/RoleDbTest_givesRole_input.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void givesRoles(){
        List<Role> roles = repository.getRoles(1L);
        assertEquals(roles.get(0), Role.TEACHER);
    }
}

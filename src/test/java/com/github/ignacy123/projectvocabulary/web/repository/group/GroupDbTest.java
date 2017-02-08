package com.github.ignacy123.projectvocabulary.web.repository.group;

import com.github.ignacy123.projectvocabulary.web.domain.Group;
import com.github.ignacy123.projectvocabulary.web.repository.GroupJdbcRepository;
import com.github.ignacy123.projectvocabulary.web.repository.RolesJdbcRepository;
import com.github.ignacy123.projectvocabulary.web.repository.role.RoleDbTestSetup;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.xmlunit.matchers.CompareMatcher.isIdenticalTo;

/**
 * Created by ignacy on 08.02.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { GroupDbTestSetup.class })
public class GroupDbTest {
    @Autowired
    GroupJdbcRepository repository;
    @Test
    @DatabaseSetup(value = "classpath:repository/role/GroupDbTest_testSave_input.xml")
    @ExpectedDatabase(value = "classpath:repository/role/GroupDbTest_testSave_output.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testSave(){
        Group group = new Group();
        group.setId(3L);
        group.setTeacherId(3L);
        group.setName("testThree");
        repository.createNew(group);
    }
    @Test
    @DatabaseSetup(value = "classpath:repository/role/GroupDbTest_testSave_input.xml")
    public void findsById(){
        Group group = repository.findById(1L);
        assertThat(group.getName(), is("testOne"));
    }
    @Test
    @DatabaseSetup(value = "classpath:repository/role/GroupDbTest_testSave_input.xml")
    public void findsByTacherId(){
        List<Group> groups = repository.findByTeacherId(1L);
        assertThat(groups.get(0).getName(), is("testOne"));
    }

}

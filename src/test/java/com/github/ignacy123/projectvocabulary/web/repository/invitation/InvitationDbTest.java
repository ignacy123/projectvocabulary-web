package com.github.ignacy123.projectvocabulary.web.repository.invitation;

import com.github.ignacy123.projectvocabulary.web.domain.Invitation;
import com.github.ignacy123.projectvocabulary.web.repository.BaseDBUnitTest;
import com.github.ignacy123.projectvocabulary.web.repository.InvitationJdbcRepository;
import com.github.ignacy123.projectvocabulary.web.repository.TeacherJdbcRepository;
import com.github.ignacy123.projectvocabulary.web.repository.role.RoleDbTestSetup;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by ignacy on 08.02.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {InvitationDbTestSetup.class})
public class InvitationDbTest extends BaseDBUnitTest {

    @Autowired
    InvitationJdbcRepository repository;

    @Test
    @DatabaseSetup(value = "classpath:repository/invitation/InvitationRepositoryDbTest_testSave_input.xml")
    @ExpectedDatabase(value = "classpath:repository/invitation/InvitationRepositoryDbTest_testSave_output.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testSave() {
        Invitation invitation = new Invitation();
        invitation.setUid("1234567");
        invitation.setEmail("c");
        invitation.setGroupId(3L);
        invitation.setName("cs");
        repository.save(invitation);
    }

    @Test
    @DatabaseSetup(value = "classpath:repository/invitation/InvitationRepositoryDbTest_testSave_output.xml")
    @ExpectedDatabase(value = "classpath:repository/invitation/InvitationRepositoryDbTest_testSave_input.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testDelete() {
        repository.delete("1234567");
    }
}

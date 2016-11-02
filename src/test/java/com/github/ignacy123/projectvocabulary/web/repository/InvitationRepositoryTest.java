package com.github.ignacy123.projectvocabulary.web.repository;

import com.github.ignacy123.projectvocabulary.web.domain.Invitation;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ignacy on 25.10.16.
 */
public class InvitationRepositoryTest extends RepositoryTest {
    private static final Set<String> IGNORED_ATTRS = new HashSet<>(Arrays.asList("uid"));
    private InvitationXmlRepository repository;

    @Override
    protected void createRepository() {
        repository = new InvitationXmlRepository(repositoryFile);
    }

    @Test
    public void testSave() throws IOException {
        prepareRepository("/repository/invitation/emptyRepositoryFile.xml");
        Invitation invitation = new Invitation();
        invitation.setGroupId(1L);
        invitation.setUid("00000000-0000-0000-0000-000000000000");
        invitation.setEmail("test@test.com");
        invitation.setName("test");
        repository.save(invitation);
        assertRepositoryXmlWithResource("/repository/invitation/testSave_out.xml", IGNORED_ATTRS);
    }
}

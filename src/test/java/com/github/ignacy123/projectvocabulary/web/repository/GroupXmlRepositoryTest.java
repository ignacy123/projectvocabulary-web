package com.github.ignacy123.projectvocabulary.web.repository;

import com.github.ignacy123.projectvocabulary.web.domain.Group;
import org.junit.Test;

import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Created by ignacy on 16.06.16.
 */
public class GroupXmlRepositoryTest extends RepositoryTest {

    private GroupXmlRepository repository;

    @Override
    protected void createRepository() {
        repository = new GroupXmlRepository(repositoryFile);
    }

    @Test
    public void testSave() throws Exception {
        prepareRepository("/repository/group/emptyRepositoryFile.xml");
        Group group = new Group();
        group.setName("testGroup");
        group.setTeacherId(1L);
        repository.createNew(group);

        assertRepositoryXmlWithResource("/repository/group/testSave_out.xml");
    }

}


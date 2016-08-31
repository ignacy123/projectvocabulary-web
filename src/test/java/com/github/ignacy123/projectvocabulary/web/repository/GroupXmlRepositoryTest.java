package com.github.ignacy123.projectvocabulary.web.repository;

import com.github.ignacy123.projectvocabulary.web.domain.Group;
import com.github.ignacy123.projectvocabulary.web.domain.User;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.xmlunit.matchers.CompareMatcher.isIdenticalTo;

/**
 * Created by ignacy on 16.06.16.
 */
public class GroupXmlRepositoryTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    private GroupXmlRepository repository;
    private File repositoryFile;

    @Test
    public void testSave() throws Exception {
        prepareRepository("/repository/group/emptyRepositoryFile.xml");
        Group group = new Group();
        group.setName("testGroup");
        group.setTeacherId(1L);
        repository.save(group);

        assertRepositoryXmlWithResource("/repository/group/testSave_out.xml");
    }

    private void assertRepositoryXml(String expectedXml) throws IOException {
        String repositoryXml = FileUtils.readFileToString(repositoryFile, "UTF-8");
        assertThat(repositoryXml, isIdenticalTo(expectedXml).ignoreWhitespace());
    }

    private void assertRepositoryXmlWithResource(String resourcePath) throws IOException {
        String expectedXml = IOUtils.toString(getClass().getResource(resourcePath), "UTF-8");
        assertRepositoryXml(expectedXml);
    }

    private void prepareRepository(String resourcePath) throws IOException {
        repositoryFile = temporaryFolder.newFile();
        InputStream input = getClass().getResourceAsStream(resourcePath);
        String inputContent = IOUtils.toString(input, "UTF-8");
        FileUtils.writeStringToFile(repositoryFile, inputContent, "UTF-8");
        repository = new GroupXmlRepository(repositoryFile);
    }

}


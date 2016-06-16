package com.github.ignacy123.projectvocabulary.web.repository;

import com.github.ignacy123.projectvocabulary.web.domain.User;
import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static org.junit.Assert.assertThat;
import static org.xmlunit.matchers.CompareMatcher.isIdenticalTo;

/**
 * Created by ignacy on 16.06.16.
 */
public class UserXmlRepositoryTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void testSave() throws Exception {
        File file = temporaryFolder.newFile();
        UserXmlRepository repository = new UserXmlRepository(file);
        User user = new User();
        user.setEmail("janusz@example.com");
        user.setLogin("janusz");
        user.setPassword("1234567");
        repository.save(user);
        String repositoryXml = FileUtils.readFileToString(file, "UTF-8");
        String expectedXml = "<users>\n" +
                "    <user id=\"1\" login=\"janusz\" email=\"janusz@example.com\" password=\"1234567\"/>\n" +
                "</users>";
        assertThat(repositoryXml, isIdenticalTo(expectedXml).ignoreWhitespace());
    }

    @Test
    public void testSaveTwo() throws Exception {
        File file = temporaryFolder.newFile();
        UserXmlRepository repository = new UserXmlRepository(file);
        User janusz = new User();
        janusz.setEmail("janusz@example.com");
        janusz.setLogin("janusz");
        janusz.setPassword("1234567");
        User mariusz = new User();
        mariusz.setEmail("mariusz@example.com");
        mariusz.setLogin("mariusz");
        mariusz.setPassword("1234567");
        repository.save(janusz);
        repository.save(mariusz);
        String repositoryXml = FileUtils.readFileToString(file, "UTF-8");
        String expectedXml = "<users>\n" +
                "    <user id=\"1\" login=\"janusz\" email=\"janusz@example.com\" password=\"1234567\"/>\n" +
                "    <user id=\"2\" login=\"mariusz\" email=\"mariusz@example.com\" password=\"1234567\"/>\n" +
                "</users>";
        assertThat(repositoryXml, isIdenticalTo(expectedXml).ignoreWhitespace());
    }

    @Test
    public void testFindOne() throws Exception {

        File file = temporaryFolder.newFile();
        String input = "<users>\n" +
                "    <user id=\"1\" login=\"janusz\" email=\"janusz@example.com\" password=\"1234567\"/>\n" +
                "    <user id=\"2\" login=\"mariusz\" email=\"mariusz@example.com\" password=\"1234567\"/>\n" +
                "</users>";
        FileUtils.writeStringToFile(file, input, "UTF-8");



    }
}

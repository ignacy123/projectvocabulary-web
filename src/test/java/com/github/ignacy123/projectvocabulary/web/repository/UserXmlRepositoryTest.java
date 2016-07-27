package com.github.ignacy123.projectvocabulary.web.repository;

import com.github.ignacy123.projectvocabulary.web.domain.User;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertThat;
import static org.xmlunit.matchers.CompareMatcher.isIdenticalTo;

/**
 * Created by ignacy on 16.06.16.
 */
public class UserXmlRepositoryTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    private UserXmlRepository repository;
    private File repositoryFile;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    public void testSave() throws Exception {
        prepareRepository("/repository/emptyRepositoryFile.xml");
        User user = new User();
        user.setEmail("janusz@example.com");
        user.setLogin("janusz");
        user.setPassword(passwordEncoder, "1234567");
        repository.save(user);
        String expectedXml = "<users>\n" +
                "    <user id=\"1\" login=\"janusz\" email=\"janusz@example.com\" password=\"20eabe5d64b0e216796e834f52d61fd0b70332fc\"/>\n" +
                "</users>";

        assertRepositoryXml(expectedXml);
    }

    @Test
    public void testSaveTwo() throws Exception {
        prepareRepository("/repository/emptyRepositoryFile.xml");
        User janusz = new User();
        janusz.setEmail("janusz@example.com");
        janusz.setLogin("janusz");
        janusz.setPassword(passwordEncoder, "1234567");
        User mariusz = new User();
        mariusz.setEmail("mariusz@example.com");
        mariusz.setLogin("mariusz");
        mariusz.setPassword(passwordEncoder, "1234567");
        repository.save(janusz);
        repository.save(mariusz);
        String expectedXml = "<users>\n" +
                "    <user id=\"1\" login=\"janusz\" email=\"janusz@example.com\" password=\"20eabe5d64b0e216796e834f52d61fd0b70332fc\"/>\n" +
                "    <user id=\"2\" login=\"mariusz\" email=\"mariusz@example.com\" password=\"20eabe5d64b0e216796e834f52d61fd0b70332fc\"/>\n" +
                "</users>";

        assertRepositoryXml(expectedXml);
    }

    @Test
    public void testFindById() throws Exception {
        prepareRepository("/repository/standardRepository.xml");
        User user = repository.findById(1L);
        assertThat(user.getId(), is(1L));
        assertThat(user.getEmail(), is("janusz@example.com"));
        assertThat(user.getLogin(), is("janusz"));

    }

    @Test
    public void testFindByEmail() throws Exception {
        prepareRepository("/repository/standardRepository.xml");
        User user = repository.findById(1L);
        assertThat(user.getId(), is(1L));
        assertThat(user.getEmail(), is("janusz@example.com"));
        assertThat(user.getLogin(), is("janusz"));

    }

    @Test(expected = NotUniqueEmailException.class)

    public void testSaveThrowsWhenEmailNotUnique() throws Exception {
        prepareRepository("/repository/standardRepository.xml");
        User janusz = new User();
        janusz.setEmail("janusz@example.com");
        janusz.setLogin("janusz");
        janusz.setPassword(passwordEncoder, "1234567");
        repository.save(janusz);
    }

    private void prepareRepository(String resourcePath) throws IOException {
        repositoryFile = temporaryFolder.newFile();
        InputStream input = getClass().getResourceAsStream(resourcePath);
        String inputContent = IOUtils.toString(input, "UTF-8");
        FileUtils.writeStringToFile(repositoryFile, inputContent, "UTF-8");
        repository = new UserXmlRepository(repositoryFile);
    }

    @Test(expected = InvalidRepositoryFileException.class)
    public void testLoadUsersThrowsWhenEmptyFile() throws Exception {
        File file = temporaryFolder.newFile();
        new UserXmlRepository(file);

    }

    @Test
    public void testFindAll() throws Exception {
        prepareRepository("/repository/standardRepository.xml");
        List<User> users = repository.findAll();
        assertThat(users.size(), is(2));
        assertThat(users, hasItem(allOf(
                hasProperty("id", is(1L)),
                hasProperty("login", is("janusz")),
                hasProperty("email", is("janusz@example.com"))
        )));
        assertThat(users, hasItem(allOf(
                hasProperty("id", is(2L)),
                hasProperty("login", is("mariusz")),
                hasProperty("email", is("mariusz@example.com"))
        )));
    }

    @Test
    public void testFirstIdIs1() throws Exception {
        prepareRepository("/repository/emptyRepositoryFile.xml");
        User janusz = new User();
        janusz.setEmail("janusz@example.com");
        janusz.setLogin("janusz");
        janusz.setPassword(passwordEncoder, "1234567");
        janusz = repository.save(janusz);
        assertThat(janusz.getId(), is(1L));
        String expectedXml = "<users>\n" +
                "    <user id=\"1\" login=\"janusz\" email=\"janusz@example.com\" password=\"20eabe5d64b0e216796e834f52d61fd0b70332fc\"/>\n" +
                "</users>";
        assertRepositoryXml(expectedXml);

    }

    private void assertRepositoryXml(String expectedXml) throws IOException {
        String repositoryXml = FileUtils.readFileToString(repositoryFile, "UTF-8");
        assertThat(repositoryXml, isIdenticalTo(expectedXml).ignoreWhitespace());

    }

    @Test
    public void thirdIdIs3() throws Exception {
        prepareRepository("/repository/standardRepository.xml");
        User janusz = new User();
        janusz.setEmail("janusz2@example.com");
        janusz.setLogin("janusz2");
        janusz.setPassword(passwordEncoder, "1234567");
        janusz = repository.save(janusz);
        assertThat(janusz.getId(), is(3L));
        String expectedXml = "<users>\n" +
                "    <user id=\"1\" login=\"janusz\" email=\"janusz@example.com\" password=\"20eabe5d64b0e216796e834f52d61fd0b70332fc\"/>\n" +
                "    <user id=\"2\" login=\"mariusz\" email=\"mariusz@example.com\" password=\"20eabe5d64b0e216796e834f52d61fd0b70332fc\"/>\n" +
                "    <user id=\"3\" login=\"janusz2\" email=\"janusz2@example.com\" password=\"20eabe5d64b0e216796e834f52d61fd0b70332fc\"/>\n" +
                "</users>";
        assertRepositoryXml(expectedXml);

    }
}

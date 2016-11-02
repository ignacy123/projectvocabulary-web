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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.xmlunit.matchers.CompareMatcher.isIdenticalTo;

/**
 * Created by ignacy on 16.06.16.
 */
public class UserXmlRepositoryTest extends RepositoryTest {

    private UserXmlRepository repository;
    private PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

    @Override
    protected void createRepository() {
        repository = new UserXmlRepository(repositoryFile);
    }

    @Test
    public void testSave() throws Exception {
        when(passwordEncoder.encode("1234567")).thenReturn("20eabe5d64b0e216796e834f52d61fd0b70332fc");
        prepareRepository("/repository/emptyRepositoryFile.xml");
        User user = new User();
        user.setEmail("janusz@example.com");
        user.setPassword(passwordEncoder, "1234567");
        user.setFirstName("janusz");
        user.setLastName("kowalski");
        repository.save(user);

        assertRepositoryXmlWithResource("/repository/testSave_out.xml");
    }

    @Test
    public void testSaveTwo() throws Exception {
        when(passwordEncoder.encode("1234567")).thenReturn("20eabe5d64b0e216796e834f52d61fd0b70332fc");
        prepareRepository("/repository/emptyRepositoryFile.xml");
        User janusz = new User();
        janusz.setFirstName("Janusz");
        janusz.setLastName("Kowalski");
        janusz.setEmail("janusz@example.com");
        janusz.setPassword(passwordEncoder, "1234567");
        User mariusz = new User();
        mariusz.setFirstName("Mariusz");
        mariusz.setLastName("Nowak");
        mariusz.setEmail("mariusz@example.com");
        mariusz.setPassword(passwordEncoder, "1234567");
        repository.save(janusz);
        repository.save(mariusz);
        String expectedXml = "<users>\n" +
                "    <user id=\"1\" firstName=\"Janusz\" lastName=\"Kowalski\" email=\"janusz@example.com\" password=\"20eabe5d64b0e216796e834f52d61fd0b70332fc\"/>\n" +
                "    <user id=\"2\" firstName=\"Mariusz\" lastName=\"Nowak\" email=\"mariusz@example.com\" password=\"20eabe5d64b0e216796e834f52d61fd0b70332fc\"/>\n" +
                "</users>";

        assertRepositoryXml(expectedXml);
    }

    @Test
    public void testFindById() throws Exception {
        prepareRepository("/repository/standardRepository.xml");
        User user = repository.findById(1L);
        assertThat(user.getId(), is(1L));
        assertThat(user.getEmail(), is("janusz@example.com"));

    }

    @Test
    public void testFindByEmail() throws Exception {
        prepareRepository("/repository/standardRepository.xml");
        User user = repository.findById(1L);
        assertThat(user.getId(), is(1L));
        assertThat(user.getEmail(), is("janusz@example.com"));

    }

    @Test(expected = NotUniqueEmailException.class)

    public void testSaveThrowsWhenEmailNotUnique() throws Exception {
        prepareRepository("/repository/standardRepository.xml");
        User janusz = new User();
        janusz.setEmail("janusz@example.com");
        janusz.setPassword(passwordEncoder, "1234567");
        repository.save(janusz);
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
                hasProperty("email", is("janusz@example.com"))
        )));
        assertThat(users, hasItem(allOf(
                hasProperty("id", is(2L)),
                hasProperty("email", is("mariusz@example.com"))
        )));
    }

    @Test
    public void testFirstIdIs1() throws Exception {
        when(passwordEncoder.encode("1234567")).thenReturn("20eabe5d64b0e216796e834f52d61fd0b70332fc");
        prepareRepository("/repository/emptyRepositoryFile.xml");
        User janusz = new User();
        janusz.setFirstName("Janusz");
        janusz.setLastName("Kowalski");
        janusz.setEmail("janusz@example.com");
        janusz.setPassword(passwordEncoder, "1234567");
        janusz = repository.save(janusz);
        assertThat(janusz.getId(), is(1L));
        String expectedXml = "<users>\n" +
                "    <user id=\"1\" firstName=\"Janusz\" lastName=\"Kowalski\" email=\"janusz@example.com\" password=\"20eabe5d64b0e216796e834f52d61fd0b70332fc\"/>\n" +
                "</users>";
        assertRepositoryXml(expectedXml);

    }

    @Test
    public void thirdIdIs3() throws Exception {
        when(passwordEncoder.encode("1234567")).thenReturn("20eabe5d64b0e216796e834f52d61fd0b70332fc");
        prepareRepository("/repository/standardRepository.xml");
        User janusz = new User();
        janusz.setEmail("janusz2@example.com");
        janusz.setPassword(passwordEncoder, "1234567");
        janusz = repository.save(janusz);
        assertThat(janusz.getId(), is(3L));
        String expectedXml = "<users>\n" +
                "    <user id=\"1\" email=\"janusz@example.com\" firstName=\"Janusz\" lastName=\"Kowalski\" password=\"20eabe5d64b0e216796e834f52d61fd0b70332fc\"/>\n" +
                "    <user id=\"2\" email=\"mariusz@example.com\" firstName=\"Mariusz\" lastName=\"Nowak\" password=\"20eabe5d64b0e216796e834f52d61fd0b70332fc\"/>\n" +
                "    <user id=\"3\" email=\"janusz2@example.com\" password=\"20eabe5d64b0e216796e834f52d61fd0b70332fc\"/>\n" +
                "</users>";
        assertRepositoryXml(expectedXml);

    }

    @Test
    public void testPersist() throws Exception {
        prepareRepository("/repository/standardRepository.xml");
        User user = repository.findById(1L);
        user.setEmail("damian@example.com");
        user.setFirstName("Damian");
        repository.persist();
        assertRepositoryXmlWithResource("/repository/testPersist_out.xml");

    }
}

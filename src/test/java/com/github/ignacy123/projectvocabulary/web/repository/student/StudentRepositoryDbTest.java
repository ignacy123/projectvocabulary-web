package com.github.ignacy123.projectvocabulary.web.repository.student;

import com.github.ignacy123.projectvocabulary.web.domain.User;
import com.github.ignacy123.projectvocabulary.web.repository.BaseDBUnitTest;
import com.github.ignacy123.projectvocabulary.web.repository.DbTestUtil;
import com.github.ignacy123.projectvocabulary.web.repository.NotUniqueEmailException;
import com.github.ignacy123.projectvocabulary.web.repository.StudentRepository;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasProperty;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by tokruzel on 30/11/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {StudentRepositoryDbTestConfig.class})
public class StudentRepositoryDbTest extends BaseDBUnitTest {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder noEncodingEncoder;

    @Before
    public void resetAutoIncrementField() throws SQLException {
        DbTestUtil.resetAutoIncrementInTable(dataSource, "student");
    }

    private PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

    @Test
    @DatabaseSetup(value = "classpath:repository/student/StudentRepositoryDbTest_saveStudent_in.xml")
    @ExpectedDatabase(value = "classpath:repository/student/StudentRepositoryDbTest_saveStudent_out.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void saveStudent() {
        final User student = new User();
        student.setEmail("email@email.com");
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setRawPassword(noEncodingEncoder, "1234567890");
        studentRepository.insert(student);
    }


    @Test
    @DatabaseSetup(value = "classpath:repository/student/StudentRepositoryDbTest_saveTwo_in.xml")
    @ExpectedDatabase(value = "classpath:repository/student/StudentRepositoryDbTest_saveTwo_out.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void saveTwo() {
        final User student = new User();
        student.setEmail("email@email.com");
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setRawPassword(noEncodingEncoder, "1234567890");
        final User student2 = new User();
        student2.setEmail("email2@email.com");
        student2.setFirstName("Johnny");
        student2.setLastName("Doebby");
        student2.setRawPassword(noEncodingEncoder, "123456789012");
        studentRepository.insert(student);
        studentRepository.insert(student2);

    }

    @Test
    @DatabaseSetup(value = "classpath:repository/student/StudentRepositoryDbTest_saveTwo_in.xml")
    public void findById() {
        User student = studentRepository.findById(1L);
        User student2 = new User();
        student2.setFirstName("a");
        student2.setLastName("a");
        student2.setId(1L);
        student2.setEmail("a@a.com");
        assertThat((student.getId()), is(student2.getId()));
    }

    @Test
    @DatabaseSetup(value = "classpath:repository/student/StudentRepositoryDbTest_saveTwo_in.xml")
    public void findByEmail() {
        User student = studentRepository.findByEmail("a@a.com");
        User student2 = new User();
        student2.setFirstName("a");
        student2.setLastName("a");
        student2.setId(1L);
        student2.setEmail("a@a.com");
        assertThat((student.getEmail()), is(student2.getEmail()));
    }

    @Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
    @DatabaseSetup(value = "classpath:repository/student/StudentRepositoryDbTest_saveTwo_in.xml")
    public void testSaveThrowsWhenEmailNotUnique() throws Exception {
        User janusz = new User();
        janusz.setEmail("a@a.com");
        janusz.setRawPassword(passwordEncoder, "1234567");
        studentRepository.insert(janusz);
    }


    @Test
    @DatabaseSetup(value = "classpath:repository/student/StudentRepositoryDbTest_findAll_in.xml")
    public void findAll() {
        List<User> users = studentRepository.findAll();

        assertThat(users.size(), is(3));
        assertThat(users, hasItem(allOf(
                hasProperty("id", is(1L)),
                hasProperty("email", is("a@a.com"))
        )));
        assertThat(users, hasItem(allOf(
                hasProperty("id", is(3L)),
                hasProperty("email", is("c@c.com"))
        )));
    }

    @Test
    @DatabaseSetup(value = "classpath:repository/student/StudentRepositoryDbTest_emptyRepository.xml")
    public void firstIdIs1(){
        when(passwordEncoder.encode("haslo")).thenReturn("20eabe5d64b0e216796e834f52d61fd0b70332fc");
        User user = new User();
        user.setFirstName("a");
        user.setLastName("a");
        user.setEmail("d@d.com");
        user.setRawPassword(passwordEncoder, "haslo");
        studentRepository.insert(user);
        User assertedUser = studentRepository.findByEmail("d@d.com");
        assertThat((assertedUser.getId()), is(1L));

    }

    @Test
    @DatabaseSetup(value = "classpath:repository/student/StudentRepositoryDbTest_emptyRepository.xml")
    public void thirdIdIs3(){
        when(passwordEncoder.encode("haslo")).thenReturn("20eabe5d64b0e216796e834f52d61fd0b70332fc");
        User user = new User();
        user.setFirstName("a");
        user.setLastName("a");
        user.setEmail("d@d.com");
        user.setRawPassword(passwordEncoder, "haslo");
        User user2 = new User();
        user2.setFirstName("a2");
        user2.setLastName("a2");
        user2.setEmail("d2@d2.com");
        user2.setRawPassword(passwordEncoder, "haslo");
        User user3 = new User();
        user3.setFirstName("a3");
        user3.setLastName("a3");
        user3.setEmail("d3@d3.com");
        user3.setRawPassword(passwordEncoder, "haslo");
        studentRepository.insert(user);
        studentRepository.insert(user2);
        studentRepository.insert(user3);
        User assertedUser = studentRepository.findByEmail("d3@d3.com");
        assertThat((assertedUser.getId()), is(3L));

    }


}

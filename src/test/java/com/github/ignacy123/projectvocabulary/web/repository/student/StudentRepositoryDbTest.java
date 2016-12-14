package com.github.ignacy123.projectvocabulary.web.repository.student;

import com.github.ignacy123.projectvocabulary.web.domain.User;
import com.github.ignacy123.projectvocabulary.web.repository.BaseDBUnitTest;
import com.github.ignacy123.projectvocabulary.web.repository.DbTestUtil;
import com.github.ignacy123.projectvocabulary.web.repository.StudentRepository;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.junit.Assert.assertThat;

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

    private PasswordEncoder noEncodingEncoder = NoOpPasswordEncoder.getInstance();

    @Before
    public void resetAutoIncrementField() throws SQLException {
        DbTestUtil.resetAutoIncrementInTable(dataSource, "student");
    }

    @Test
    @DatabaseSetup(value = "classpath:repository/student/StudentRepositoryDbTest_saveStudent_in.xml")
    @ExpectedDatabase(value = "classpath:repository/student/StudentRepositoryDbTest_saveStudent_out.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void saveStudent() {
        final User student = new User();
        student.setEmail("email@email.com");
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setPassword(noEncodingEncoder, "1234567890");
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
        student.setPassword(noEncodingEncoder, "1234567890");
        final User student2 = new User();
        student2.setEmail("email2@email.com");
        student2.setFirstName("Johnny");
        student2.setLastName("Doebby");
        student2.setPassword(noEncodingEncoder, "123456789012");
        studentRepository.insert(student);
        studentRepository.insert(student2);

    }

    @Test
    @DatabaseSetup(value = "classpath:repository/student/StudentRepositoryDbTest_saveTwo_in.xml")
    public void findById() {
        User student = studentRepository.findById(1L);
//        assertThat(student)is;
    }
}

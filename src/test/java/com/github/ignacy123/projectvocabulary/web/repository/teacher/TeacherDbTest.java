package com.github.ignacy123.projectvocabulary.web.repository.teacher;

import com.github.ignacy123.projectvocabulary.web.domain.Teacher;
import com.github.ignacy123.projectvocabulary.web.repository.TeacherJdbcRepository;
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

/**
 * Created by ignacy on 08.02.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TeacherDbTestSetup.class })
public class TeacherDbTest {
    @Autowired
    TeacherJdbcRepository repository;
    @Test
    @DatabaseSetup(value = "classpath:repository/teacher/TeacherRepositoryDbTest_testSave_input.xml")
    @ExpectedDatabase(value = "classpath:repository/teacher/TeacherRepositoryDbTest_testSave_output.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testSave(){
        Teacher teacher = new Teacher();
        teacher.setId(3L);
        teacher.setUserId(3l);
        repository.save(teacher);
    }

    @Test
    @DatabaseSetup(value = "classpath:repository/teacher/TeacherpDbTest_testSave_input.xml")
    public void testFindById(){
        Teacher teacher = repository.findById(1L);
        assertThat(teacher.getUserId(), is(1L));
    }

    @Test
    @DatabaseSetup(value = "classpath:repository/teacher/TeacherDbTest_testSave_input.xml")
    public void testFindAll(){
        List<Teacher> teachers = repository.findAll();
        assertThat(teachers.get(1).getUserId(), is(2L));
    }
}

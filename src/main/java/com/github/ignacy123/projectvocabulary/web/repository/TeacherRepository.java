package com.github.ignacy123.projectvocabulary.web.repository;

import com.github.ignacy123.projectvocabulary.web.domain.Teacher;
import com.github.ignacy123.projectvocabulary.web.domain.User;

import java.util.List;

/**
 * Created by ignacy on 30.11.16.
 */
public interface TeacherRepository {

    Teacher findById(Long id);

    List<Teacher> findAll();

    Teacher save (Teacher teacher);
}

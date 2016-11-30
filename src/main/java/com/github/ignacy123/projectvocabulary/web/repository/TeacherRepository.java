package com.github.ignacy123.projectvocabulary.web.repository;

import com.github.ignacy123.projectvocabulary.web.domain.User;

import java.util.List;

/**
 * Created by ignacy on 30.11.16.
 */
public interface TeacherRepository {

    User findById(Long id);

    List<User> findAll();

    User findByEmail(String email);

    User save (User user);
}

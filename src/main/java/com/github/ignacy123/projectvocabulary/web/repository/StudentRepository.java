package com.github.ignacy123.projectvocabulary.web.repository;

import com.github.ignacy123.projectvocabulary.web.domain.User;

import java.util.List;

/**
 * Created by ignacy on 16.11.16.
 */
public interface StudentRepository {
    User findById(Long id);

    List<User> findAll();

    User findByEmail(String email);

    User insert(User user);

    void update(User user);


}

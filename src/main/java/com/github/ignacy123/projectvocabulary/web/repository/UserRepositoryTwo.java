package com.github.ignacy123.projectvocabulary.web.repository;

import com.github.ignacy123.projectvocabulary.web.domain.User;

import java.util.List;

/**
 * Created by ignacy on 19.05.16.
 */
public interface UserRepositoryTwo {
    User save(User user);

    void persist();

    User findByEmail(String email);

    User findById(Long id);

    List<User> findAll();

}

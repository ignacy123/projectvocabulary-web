package com.github.ignacy123.projectvocabulary.web.repository;

import com.github.ignacy123.projectvocabulary.web.domain.User;

/**
 * Created by ignacy on 16.11.16.
 */
public interface StudentRepository {
    User findById(Long id);
}

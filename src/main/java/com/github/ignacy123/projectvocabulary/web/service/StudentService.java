package com.github.ignacy123.projectvocabulary.web.service;

import com.github.ignacy123.projectvocabulary.web.domain.User;

/**
 * Created by ignacy on 16.11.16.
 */
public interface StudentService {

    User findById(Long id);
}

package com.github.ignacy123.projectvocabulary.web.service;

import com.github.ignacy123.projectvocabulary.web.domain.User;
import com.github.ignacy123.projectvocabulary.web.dto.RegistrationDto;

/**
 * Created by ignacy on 19.05.16.
 */
public interface UserService {
    User register(RegistrationDto dto);
    User findByEmail(String email);
    User findById(Long id);
    User logIn(String email, String password);
}

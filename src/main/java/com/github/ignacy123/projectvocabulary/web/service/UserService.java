package com.github.ignacy123.projectvocabulary.web.service;

import com.github.ignacy123.projectvocabulary.web.domain.User;
import com.github.ignacy123.projectvocabulary.web.dto.RegistrationDto;
import com.github.ignacy123.projectvocabulary.web.dto.UserUpdateDto;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Created by ignacy on 19.05.16.
 */
public interface UserService {
    User register(RegistrationDto dto);

    @PreAuthorize("principal.id==#id")
    User findById(@P("id") Long id);

    User logIn(String email, String password);

    @PreAuthorize("principal.id==#id")
    User updateUser(Long id, UserUpdateDto updateDto);

    User registerWithUid(RegistrationDto dto, String uid);
}

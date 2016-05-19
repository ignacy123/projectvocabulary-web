package com.github.ignacy123.projectvocabulary.web.service;

import com.github.ignacy123.projectvocabulary.web.domain.User;
import com.github.ignacy123.projectvocabulary.web.domain.UserRepositoryImpl;
import com.github.ignacy123.projectvocabulary.web.dto.RegistrationDto;

/**
 * Created by ignacy on 19.05.16.
 */
public class UserServiceImpl implements UserService {

    UserRepositoryImpl userRepository = new UserRepositoryImpl();
    private Long idCounter = 1L;
    public User register(RegistrationDto dto) {
        User user = new User();
        user.setId(idCounter);
        idCounter++;
        user.setLogin(dto.getLogin());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        userRepository.add(user);
        return user;
    }

    public User findByEmail(String email) {
       return  userRepository.findByEmail(email);
    }

    public User findById(Long id) {
        return userRepository.findById(id);
    }
}

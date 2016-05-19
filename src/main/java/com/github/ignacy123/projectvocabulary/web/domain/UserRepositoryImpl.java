package com.github.ignacy123.projectvocabulary.web.domain;

import com.github.ignacy123.projectvocabulary.web.dto.UserNotFoundException;

import java.util.ArrayList;

/**
 * Created by ignacy on 19.05.16.
 */
public class UserRepositoryImpl implements UserRepository{
    private ArrayList<User> userRepository;

    @Override
    public void add(User user) {
        userRepository.add(user);
    }

    @Override
    public User findByEmail(String email) {
        for (User user : userRepository) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        throw new UserNotFoundException("unknown email");
    }

    @Override
    public User findById(Long id) {
        for (User user : userRepository) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        throw new UserNotFoundException("unknown id");
    }
}

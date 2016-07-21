package com.github.ignacy123.projectvocabulary.web.service;

import com.github.ignacy123.projectvocabulary.web.domain.User;
import com.github.ignacy123.projectvocabulary.web.dto.UserNotFoundException;
import com.github.ignacy123.projectvocabulary.web.repository.UserRepository;
import com.github.ignacy123.projectvocabulary.web.dto.RegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ignacy on 19.05.16.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(RegistrationDto dto) {
        User user = new User();
        user.setLogin(dto.getLogin());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        userRepository.save(user);
        return user;
    }

    public User findByEmail(String email) {
       return  userRepository.findByEmail(email);
    }

    public User findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User logIn(String email, String password) {
        try {
            User user = userRepository.findByEmail(email);

            if(user.matchesPassword(password)){
                return user;
            }
        }catch(UserNotFoundException e){
            throw new WrongCredentialsException();
        }
        throw new WrongCredentialsException();
    }
}

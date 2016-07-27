package com.github.ignacy123.projectvocabulary.web.security;

import com.github.ignacy123.projectvocabulary.web.dto.UserNotFoundException;
import com.github.ignacy123.projectvocabulary.web.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by ignacy on 27.07.16.
 */
public class UserRepositoryUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserRepositoryUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return userRepository.findByEmail(username);
        } catch (UserNotFoundException e) {
            throw new UsernameNotFoundException("incorrect email", e);
        }
    }
}

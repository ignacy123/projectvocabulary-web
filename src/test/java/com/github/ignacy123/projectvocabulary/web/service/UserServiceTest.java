package com.github.ignacy123.projectvocabulary.web.service;

import com.github.ignacy123.projectvocabulary.web.domain.User;
import com.github.ignacy123.projectvocabulary.web.dto.UserNotFoundException;
import com.github.ignacy123.projectvocabulary.web.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by ignacy on 07.07.16.
 */
public class UserServiceTest {
    private UserRepository repository;
    private UserServiceImpl service;
    private User mockedUser;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Before
    public void setUp() throws Exception {
        repository = mock(UserRepository.class);
        service = new UserServiceImpl(repository, passwordEncoder);
        mockedUser = new User();
        mockedUser.setEmail("janusz@example.com");
        mockedUser.setLogin("janusz");
        mockedUser.setId(1L);
        mockedUser.setPassword(passwordEncoder, "1234567Aa");
    }

    @Test
    public void testLogIn() throws Exception {
        when(repository.findByEmail("janusz@example.com")).thenReturn(mockedUser);
        User user = service.logIn("janusz@example.com", "1234567Aa");
        assertThat(user.getId(), is(1L));
        assertThat(user.getEmail(), is("janusz@example.com"));
        assertThat(user.getLogin(), is("janusz"));
    }
    @Test(expected = WrongCredentialsException.class)
    public void testLogInThrowsWhenWrongPassword() throws Exception {
        when(repository.findByEmail("janusz@example.com")).thenReturn(mockedUser);
        service.logIn("janusz@example.com", "1234567A");
    }
    @Test(expected = WrongCredentialsException.class)
    public void testLogInThrowsWhenWrongEmail() throws Exception {
        when(repository.findByEmail("janusz4@example.com")).thenThrow(new UserNotFoundException("User not found."));
        service.logIn("janusz4@example.com", "1234567A");
    }


}

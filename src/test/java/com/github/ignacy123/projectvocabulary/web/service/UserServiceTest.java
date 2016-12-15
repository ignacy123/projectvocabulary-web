package com.github.ignacy123.projectvocabulary.web.service;

import com.github.ignacy123.projectvocabulary.web.domain.User;
import com.github.ignacy123.projectvocabulary.web.dto.RegistrationDto;
import com.github.ignacy123.projectvocabulary.web.dto.UserNotFoundException;
import com.github.ignacy123.projectvocabulary.web.repository.StudentRepository;
import com.github.ignacy123.projectvocabulary.web.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by ignacy on 07.07.16.
 */
public class UserServiceTest {

    private StudentRepository repository;
    private UserServiceImpl service;
    private User mockedUser;
    private PasswordEncoder passwordEncoder;

    @Before
    public void setUp() throws Exception {
        repository = mock(StudentRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        service = new UserServiceImpl(repository, passwordEncoder);

        when(passwordEncoder.encode("1234567Aa")).thenReturn("hashedPassword");

        mockedUser = new User();
        mockedUser.setEmail("janusz@example.com");
        mockedUser.setId(1L);
        mockedUser.setRawPassword(passwordEncoder, "1234567Aa");
    }

    @Test
    public void testLogIn() throws Exception {
        when(repository.findByEmail("janusz@example.com")).thenReturn(mockedUser);
        when(passwordEncoder.matches("1234567Aa", "hashedPassword")).thenReturn(true);
        User user = service.logIn("janusz@example.com", "1234567Aa");
        assertThat(user.getId(), is(1L));
        assertThat(user.getEmail(), is("janusz@example.com"));
    }
    @Test(expected = WrongCredentialsException.class)
    public void testLogInThrowsWhenWrongPassword() throws Exception {
        when(repository.findByEmail("janusz@example.com")).thenReturn(mockedUser);
        //when(passwordEncoder.encode("1234567A")).thenReturn("hashedWrongPassword");
        when(passwordEncoder.matches("1234567A", "hashedPassword")).thenReturn(false);
        service.logIn("janusz@example.com", "1234567A");
    }
    @Test(expected = WrongCredentialsException.class)
    public void testLogInThrowsWhenWrongEmail() throws Exception {
        when(repository.findByEmail("janusz4@example.com")).thenThrow(new UserNotFoundException("User not found."));
        service.logIn("janusz4@example.com", "1234567A");
    }

    @Test
    public void testRegister() {

        RegistrationDto dto = new RegistrationDto();
        dto.setEmail("janusz@example.com");
        dto.setPassword("1234567Aa");
        dto.setFirstName("janusz");
        dto.setLastName("kowalski");

        service.register(dto);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(repository).insert(userCaptor.capture());

        User user = userCaptor.getValue();
        assertThat(user.getEmail(), is(dto.getEmail()));
        assertThat(user.getPassword(), is("hashedPassword"));
        assertThat(user.getFirstName(), is(dto.getFirstName()));
        assertThat(user.getLastName(), is(dto.getLastName()));

    }


}

package com.github.ignacy123.projectvocabulary.web.service;

import com.github.ignacy123.projectvocabulary.web.domain.User;
import com.github.ignacy123.projectvocabulary.web.dto.RegistrationDto;
import com.github.ignacy123.projectvocabulary.web.dto.UserNotFoundException;
import com.github.ignacy123.projectvocabulary.web.dto.UserUpdateDto;
import com.github.ignacy123.projectvocabulary.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by ignacy on 19.05.16.
 */
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public User register(RegistrationDto dto) {
		User user = new User();
		user.setRawPassword(passwordEncoder, dto.getPassword());
		user.setEmail(dto.getEmail());
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setType(dto.getType());
		userRepository.insert(user);
		return user;
	}

	public User findById(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public User logIn(String email, String password) {
		try {
			User user = userRepository.findByEmail(email);

			if (user.matchesPassword(passwordEncoder, password)) {
				return user;
			}
		} catch (UserNotFoundException e) {
			throw new WrongCredentialsException();
		}
		throw new WrongCredentialsException();
	}

	@Override
	public User updateUser(Long id, UserUpdateDto updateDto) {
		User user = findById(id);
		user.setEmail(updateDto.getEmail());
		user.setFirstName(updateDto.getFirstName());
		user.setLastName(updateDto.getLastName());
		userRepository.update(user);
		return user;
	}
}

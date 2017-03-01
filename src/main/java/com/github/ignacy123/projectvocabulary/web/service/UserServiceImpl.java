package com.github.ignacy123.projectvocabulary.web.service;

import com.github.ignacy123.projectvocabulary.web.domain.Role;
import com.github.ignacy123.projectvocabulary.web.domain.User;
import com.github.ignacy123.projectvocabulary.web.dto.*;
import com.github.ignacy123.projectvocabulary.web.repository.GroupRepository;
import com.github.ignacy123.projectvocabulary.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ignacy on 19.05.16.
 */
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	GroupService service;

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
		Set<Role> roles = new HashSet<>();
		roles.add(Role.STUDENT);
		user.setAuthorities(roles);
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

    @Override
    public User registerWithUid(RegistrationWithUidDto dto) {
        User user = register(dto);
		InvitationAcceptanceDto invDto = new InvitationAcceptanceDto();
		invDto.setStudentId(user.getId());
		invDto.setInvitationUid(dto.getUid());
		service.acceptInvitation(invDto);
		return user;
    }
}

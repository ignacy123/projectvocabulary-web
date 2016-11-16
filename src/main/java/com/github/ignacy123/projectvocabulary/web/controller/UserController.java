package com.github.ignacy123.projectvocabulary.web.controller;

import com.github.ignacy123.projectvocabulary.web.domain.User;
import com.github.ignacy123.projectvocabulary.web.dto.*;
import com.github.ignacy123.projectvocabulary.web.repository.NotUniqueEmailException;
import com.github.ignacy123.projectvocabulary.web.service.UserService;
import com.github.ignacy123.projectvocabulary.web.service.WrongCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by ignacy on 19.05.16.
 */
@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    @ResponseBody
    public User register(@RequestBody @Valid RegistrationDto dto) {
        return userService.register(dto);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    @ResponseBody
    public User logIn(@RequestBody @Valid LogInDto dto) {
        return userService.logIn(dto.getEmail(), dto.getPassword());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/{id}")
    @ResponseBody
    public User findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/users/{id}")
    @ResponseBody
    public User updateUser(@PathVariable Long id, @RequestBody UserUpdateDto updateDto) {
        return userService.updateUser(id, updateDto);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorDto handleException(UserNotFoundException e) {
        e.printStackTrace();
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(e.getMessage());
        return errorDto;
    }

    @ExceptionHandler(value = NotUniqueEmailException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDto handleException(NotUniqueEmailException e) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage("Validation failed");
        errorDto.getErrors().put("email", "email is not unique");
        return errorDto;
    }

    @ExceptionHandler(value = WrongCredentialsException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDto handleException(WrongCredentialsException e) {
        e.printStackTrace();
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage("Invalid email or password");
        return errorDto;
    }
}

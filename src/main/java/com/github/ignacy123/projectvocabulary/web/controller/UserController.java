package com.github.ignacy123.projectvocabulary.web.controller;

import com.github.ignacy123.projectvocabulary.web.domain.User;
import com.github.ignacy123.projectvocabulary.web.dto.ErrorDto;
import com.github.ignacy123.projectvocabulary.web.dto.RegistrationDto;
import com.github.ignacy123.projectvocabulary.web.dto.UserNotFoundException;
import com.github.ignacy123.projectvocabulary.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    @ResponseBody
    public User findByEmail(@RequestParam String email) {
        return userService.findByEmail(email);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/{id}")
    @ResponseBody
    public User findById(@PathVariable Long id) {
        return userService.findById(id);
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

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorDto handleException(MethodArgumentNotValidException e) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage("Validation failed");
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errorDto.getErrors().put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return errorDto;
    }
}

package com.github.ignacy123.projectvocabulary.web.dto;

/**
 * Created by ignacy on 19.05.16.
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}

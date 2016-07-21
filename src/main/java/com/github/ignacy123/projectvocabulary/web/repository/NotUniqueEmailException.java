package com.github.ignacy123.projectvocabulary.web.repository;

/**
 * Created by ignacy on 13.07.16.
 */
public class NotUniqueEmailException extends RuntimeException {
    public NotUniqueEmailException(String e) {
        super(e);
    }
}

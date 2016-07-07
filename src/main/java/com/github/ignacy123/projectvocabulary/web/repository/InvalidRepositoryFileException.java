package com.github.ignacy123.projectvocabulary.web.repository;

import javax.xml.bind.JAXBException;

/**
 * Created by ignacy on 30.06.16.
 */
public class InvalidRepositoryFileException extends RuntimeException {
    public InvalidRepositoryFileException(Exception e) {
        super(e);
    }
}

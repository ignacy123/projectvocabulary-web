package com.github.ignacy123.projectvocabulary.web.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ignacy on 19.05.16.
 */
public class ErrorDto {
    private String message;

    private Map<String, String> errors = new HashMap<>();

    public Map<String, String> getErrors() {
        return errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

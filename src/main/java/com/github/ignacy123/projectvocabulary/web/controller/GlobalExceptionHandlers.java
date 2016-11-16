package com.github.ignacy123.projectvocabulary.web.controller;

import com.github.ignacy123.projectvocabulary.web.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by ignacy on 09.11.16.
 */
@ControllerAdvice
public class GlobalExceptionHandlers {


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
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

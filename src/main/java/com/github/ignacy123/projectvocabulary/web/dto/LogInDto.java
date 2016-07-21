package com.github.ignacy123.projectvocabulary.web.dto;

/**
 * Created by ignacy on 21.07.16.
 */
public class LogInDto {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

package com.github.ignacy123.projectvocabulary.web.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by ignacy on 19.05.16.
 */
public class RegistrationDto {
    @NotNull(message = "This field is required.")
    private String login;
    @Pattern(regexp = "")
    private String password;
    private String email;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

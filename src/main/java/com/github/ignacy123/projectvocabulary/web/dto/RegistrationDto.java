package com.github.ignacy123.projectvocabulary.web.dto;

import com.github.ignacy123.projectvocabulary.web.domain.User;
import com.github.ignacy123.projectvocabulary.web.validation.StrongPassword;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by ignacy on 19.05.16.
 */
public class RegistrationDto {

    @StrongPassword
    private String password;

    @NotNull(message = "This field is required.")
    @Email(message =  "Has to be valid email")
    private String email;
    private String lastName;
    private String firstName;
    @NotNull(message =  "This field is required.")
    private User.Type type;

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

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public User.Type getType() {
        return type;
    }

    public void setType(User.Type type) {
        this.type = type;
    }
}

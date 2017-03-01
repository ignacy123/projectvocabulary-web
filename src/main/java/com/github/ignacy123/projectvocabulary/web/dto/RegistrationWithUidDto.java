package com.github.ignacy123.projectvocabulary.web.dto;

import com.github.ignacy123.projectvocabulary.web.validation.StrongPassword;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;

/**
 * Created by ignacy on 01.03.17.
 */
public class RegistrationWithUidDto extends RegistrationDto{

    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

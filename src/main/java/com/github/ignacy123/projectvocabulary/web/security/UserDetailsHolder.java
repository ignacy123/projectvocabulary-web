package com.github.ignacy123.projectvocabulary.web.security;

import com.github.ignacy123.projectvocabulary.web.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by ignacy on 09.02.17.
 */
public class UserDetailsHolder {
    public static User getUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

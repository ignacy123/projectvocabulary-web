package com.github.ignacy123.projectvocabulary.web.repository;

import com.github.ignacy123.projectvocabulary.web.domain.Role;

import java.util.List;

/**
 * Created by ignacy on 11.01.17.
 */
public interface RolesRepository {
    List<Role> getRoles(Long userId);


}

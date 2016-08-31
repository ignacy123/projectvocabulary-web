package com.github.ignacy123.projectvocabulary.web.repository;

import com.github.ignacy123.projectvocabulary.web.domain.Group;

/**
 * Created by ignacy on 31.08.16.
 */
public interface GroupRepository {
    Group save(Group group);

    void persist();
}


package com.github.ignacy123.projectvocabulary.web.repository;

import com.github.ignacy123.projectvocabulary.web.domain.Group;

import java.util.List;

/**
 * Created by ignacy on 31.08.16.
 */
public interface GroupRepository {
    Group save(Group group);

    void persist();

    List<Group> findByTeacherId(Long teacherId);
}


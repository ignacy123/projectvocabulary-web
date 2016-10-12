package com.github.ignacy123.projectvocabulary.web.service;

import com.github.ignacy123.projectvocabulary.web.domain.Group;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * Created by ignacy on 31.08.16.
 */
public interface GroupService {

    @PreAuthorize("principal.type == T(com.github.ignacy123.projectvocabulary.web.domain.User.Type).TEACHER && principal.id==#group.teacherId")
    Group createGroup(@P("group") Group group);

    @PreAuthorize("principal.type == T(com.github.ignacy123.projectvocabulary.web.domain.User.Type).TEACHER && principal.id==#teacherId")
    List<Group> getTeacherGroups(@P("teacherId")Long teacherId);

}

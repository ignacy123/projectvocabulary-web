package com.github.ignacy123.projectvocabulary.web.service;

import com.github.ignacy123.projectvocabulary.web.domain.Group;
import com.github.ignacy123.projectvocabulary.web.domain.Invitation;
import com.github.ignacy123.projectvocabulary.web.dto.InvitationAcceptanceDto;
import com.github.ignacy123.projectvocabulary.web.dto.InvitationDto;
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
    List<Group> getTeacherGroups(@P("teacherId") Long teacherId);

    // && principal.id==#group.teacherId"
    @PreAuthorize("principal.type == T(com.github.ignacy123.projectvocabulary.web.domain.User.Type).TEACHER")
    Invitation createInvitation(Long id, InvitationDto dto);

    void acceptInvitation(InvitationAcceptanceDto acceptanceDto);
}

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

    @PreAuthorize("hasRole('ROLE_TEACHER') && principal.id==#group.teacherId")
    Group createGroup(@P("group") Group group);

    @PreAuthorize("hasRole('ROLE_TEACHER') && principal.id==#teacherId")
    List<Group> getTeacherGroups(@P("teacherId") Long teacherId);

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    Invitation createInvitation(Long id, InvitationDto dto);

    void acceptInvitation(InvitationAcceptanceDto acceptanceDto);

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    Group getGroup(Long groupId);
}

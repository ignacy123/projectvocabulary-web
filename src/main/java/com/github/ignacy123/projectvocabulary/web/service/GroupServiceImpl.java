package com.github.ignacy123.projectvocabulary.web.service;

import com.github.ignacy123.projectvocabulary.web.domain.Group;
import com.github.ignacy123.projectvocabulary.web.domain.Invitation;
import com.github.ignacy123.projectvocabulary.web.dto.InvitationAcceptanceDto;
import com.github.ignacy123.projectvocabulary.web.dto.InvitationDto;
import com.github.ignacy123.projectvocabulary.web.repository.GroupRepository;
import com.github.ignacy123.projectvocabulary.web.repository.GroupRepositoryTwo;
import com.github.ignacy123.projectvocabulary.web.repository.InvitationRepositoryTwo;
import com.github.ignacy123.projectvocabulary.web.security.UserDetailsHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ignacy on 31.08.16.
 */
@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository repository;
    private final InvitationRepositoryTwo invitationRepository;



    @Autowired
    public GroupServiceImpl(GroupRepository repository, InvitationRepositoryTwo invitationRepository) {
        this.repository = repository;
        this.invitationRepository = invitationRepository;
    }

    @Override
    public Group createGroup(Group group) {
        return repository.createNew(group);
    }

    @Override
    public List<Group> getTeacherGroups(Long teacherId) {
        return repository.findByTeacherId(teacherId);
    }

    @Override
    public Invitation createInvitation(Long groupId, InvitationDto dto) {
        Invitation invitation = new Invitation();
        invitation.setName(dto.getName());
        invitation.setEmail(dto.getEmail());
        invitation.setGroupId(groupId);
        return invitationRepository.save(invitation);
    }

    @Override
    public void acceptInvitation(InvitationAcceptanceDto acceptanceDto) {
        Invitation invitation = invitationRepository.findByUid(acceptanceDto.getInvitationUid());
        if(invitation == null) {
            throw new WrongInvitationException();
        }
        Long groupId = invitation.getGroupId();
        Group group = repository.findById(groupId);
        if(group == null){
            throw new WrongInvitationException();
        }
        group.getStudentIds().add(acceptanceDto.getStudentId());
        invitationRepository.delete(invitation.getUid());
    }

    @Override
    public Group getGroup(Long groupId) {
        Group group = repository.findById(groupId);
        Long teacherId = UserDetailsHolder.getUser().getId();
        if(group == null || !group.getTeacherId().equals(teacherId)){
            throw new SecurityException("no such group");
        }
        return group;
    }

}

package com.github.ignacy123.projectvocabulary.web.service;

import com.github.ignacy123.projectvocabulary.web.domain.Group;
import com.github.ignacy123.projectvocabulary.web.domain.Invitation;
import com.github.ignacy123.projectvocabulary.web.dto.InvitationAcceptanceDto;
import com.github.ignacy123.projectvocabulary.web.dto.InvitationDto;
import com.github.ignacy123.projectvocabulary.web.repository.GroupRepository;
import com.github.ignacy123.projectvocabulary.web.repository.InvitationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by ignacy on 31.08.16.
 */
@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository repository;
    private final InvitationRepository invitationRepository;
    private Invitation invitation;
    private Group group;

    @Autowired
    public GroupServiceImpl(GroupRepository repository, InvitationRepository invitationRepository) {
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
        if(invitationRepository.findByUid(acceptanceDto.getInvitationUid()) != null) {
            invitation = invitationRepository.findByUid(acceptanceDto.getInvitationUid());
        }else{
            throw new WrongInvitationException();
        }
        Long groupId = invitation.getGroupId();
        if(repository.findById(groupId) != null){
            group = repository.findById(groupId);
        }else{
            throw new WrongInvitationException();
        }
        group.getStudentIds().add(acceptanceDto.getStudentId());
        repository.persist();
        invitationRepository.delete(invitation.getUid());
    }

}

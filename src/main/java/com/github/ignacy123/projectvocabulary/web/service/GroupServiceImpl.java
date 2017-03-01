package com.github.ignacy123.projectvocabulary.web.service;

import com.github.ignacy123.projectvocabulary.web.domain.Group;
import com.github.ignacy123.projectvocabulary.web.domain.Invitation;
import com.github.ignacy123.projectvocabulary.web.dto.InvitationAcceptanceDto;
import com.github.ignacy123.projectvocabulary.web.dto.InvitationDto;
import com.github.ignacy123.projectvocabulary.web.repository.GroupRepository;
import com.github.ignacy123.projectvocabulary.web.repository.InvitationRepository;
import com.github.ignacy123.projectvocabulary.web.security.UserDetailsHolder;
import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.List;
import java.util.Properties;
import java.util.UUID;

/**
 * Created by ignacy on 31.08.16.
 */
@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository repository;
    private final InvitationRepository invitationRepository;



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
        invitation.setUid(UUID.randomUUID().toString());
        sendEmail(invitation);
        return invitationRepository.save(invitation);
    }

    private void sendEmail(Invitation invitation) {
        final String username = "projectvocabulary@onet.pl";
        final String password = "1234567Aa";
        Properties props = new Properties();
        try {
            MailSSLSocketFactory ssl = new MailSSLSocketFactory();
            ssl.setTrustAllHosts(true);
            props.put("mail.smtp.socketFactory.class",
                    ssl);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            return;
        }

        props.put("mail.smtp.host", "smtp.poczta.onet.pl");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.port", "465");


        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        session.setDebug(true);

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("projectvocabulary@onet.pl"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(invitation.getEmail()));
            message.setSubject("Invitation to Project Vocabulary");
            message.setText("Dear "+ invitation.getName()+"! \n \n"+"You've jest been invited to group'"
                    +repository.findById(invitation.getGroupId()).getName()
                    +"' in Project Vocabulary. To start studying, please register via: localhost:8080/projectvocabulary/register"
                    +"Here is your uid which you will need during the registration process: "
                    +invitation.getUid()+"\n Best wishes, \n Project Vocabulary Team"
            );

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
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
        repository.addToGroup(acceptanceDto.getStudentId(), groupId);
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

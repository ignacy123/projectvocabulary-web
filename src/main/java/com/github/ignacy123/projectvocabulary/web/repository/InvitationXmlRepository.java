package com.github.ignacy123.projectvocabulary.web.repository;

import com.github.ignacy123.projectvocabulary.web.domain.Group;
import com.github.ignacy123.projectvocabulary.web.domain.Invitation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ignacy on 31.08.16.
 */
@Repository
public class InvitationXmlRepository implements InvitationRepository {

    private final File repositoryFile;
    private Invitations invitations;

    public InvitationXmlRepository(File repositoryFile) {
        this.repositoryFile = repositoryFile;
        loadInvitations();

    }

    @Autowired
    public InvitationXmlRepository(@Value("${projectvocabulary.invitationsRepositoryFile}") String repositoryFilePath) {
        this(new File(repositoryFilePath));
    }

    private void loadInvitations() {

        try {
            JAXBContext context = JAXBContext.newInstance(InvitationXmlRepository.Invitations.class, Group.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            this.invitations = (InvitationXmlRepository.Invitations) unmarshaller.unmarshal(repositoryFile);
        } catch (JAXBException e) {
            throw new InvalidRepositoryFileException(e);
        }
    }

    @Override
    public Invitation save(Invitation invitation) {
        invitation.setUid(UUID.randomUUID().toString());
        invitations.getInvitations().add(invitation);
        persist();
        return invitation;
    }

    @Override
    public void persist() {
        try {
            JAXBContext context = JAXBContext.newInstance(InvitationXmlRepository.Invitations.class, Invitation.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(invitations, repositoryFile);
        } catch (JAXBException e) {
            throw new RuntimeException("Couldn't persist repository", e);
        }

    }

    @XmlRootElement(name = "invitations")
    @XmlAccessorType(XmlAccessType.FIELD)
    private static class Invitations {
        @XmlElement(name = "invitation")
        private List<Invitation> invitations = new ArrayList<>();

        public List<Invitation> getInvitations() {
            return invitations;
        }

        public void setInvitations(List<Invitation> invitations) {
            this.invitations = invitations;
        }


    }
}

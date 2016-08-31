package com.github.ignacy123.projectvocabulary.web.repository;

import com.github.ignacy123.projectvocabulary.web.domain.Group;
import com.github.ignacy123.projectvocabulary.web.domain.User;
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

/**
 * Created by ignacy on 31.08.16.
 */
@Repository
public class GroupXmlRepository implements GroupRepository{

    private final File repositoryFile;
    private Groups groups;

    public GroupXmlRepository(File repositoryFile) {
        this.repositoryFile = repositoryFile;
        loadGroups();

    }
    @Autowired
    public GroupXmlRepository(@Value("${projectvocabulary.groupsRepositoryFile}") String repositoryFilePath) {
        this(new File(repositoryFilePath));
    }

    private void loadGroups() {

        try {
            JAXBContext context = JAXBContext.newInstance(GroupXmlRepository.Groups.class, Group.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            this.groups = (GroupXmlRepository.Groups) unmarshaller.unmarshal(repositoryFile);
        } catch (JAXBException e) {
            throw new InvalidRepositoryFileException(e);
        }
    }

    @Override
    public Group save(Group group) {
        group.setId(getMaxId() + 1L);
        groups.getGroups().add(group);
        persist();
        return group;
    }

    private Long getMaxId() {
        Long maxId = 0L;
        for (Group group : groups.getGroups()) {
            maxId = Math.max(group.getId(), maxId);
        }
        return maxId;
    }

    @Override
    public void persist() {
        try {
            JAXBContext context = JAXBContext.newInstance(GroupXmlRepository.Groups.class, Group.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(groups, repositoryFile);
        } catch (JAXBException e) {
            throw new RuntimeException("Couldn't persist repository", e);
        }

    }

    @XmlRootElement(name = "groups")
    @XmlAccessorType(XmlAccessType.FIELD)
    private static class Groups {
        @XmlElement(name = "group")
        private List<Group> groups = new ArrayList<>();

        public List<Group> getGroups() {
            return groups;
        }

        public void setGroups(List<Group> groups) {
            this.groups = groups;
        }


    }
}

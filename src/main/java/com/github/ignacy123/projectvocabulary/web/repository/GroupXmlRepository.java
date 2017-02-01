package com.github.ignacy123.projectvocabulary.web.repository;

import com.github.ignacy123.projectvocabulary.web.domain.Group;
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
import java.util.stream.Collectors;

/**
 * Created by ignacy on 31.08.16.
 */
@Repository
public class GroupXmlRepository implements GroupRepositoryTwo {

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
    public Group createNew(Group group) {
        if (group.getId() != null) {
            throw new IllegalArgumentException("group already has id");
        }
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

    @Override
    public List<Group> findByTeacherId(Long teacherId) {
        return groups.getGroups()
                .stream()
                .filter(group -> group.getTeacherId().equals(teacherId))
                .collect(Collectors.toList());
    }

    @Override
    public Group findById(Long id) {
        for (Group group : groups.getGroups()) {
            if (group.getId().equals(id)) {
                return group;
            }
        }
        throw new RuntimeException("unknwon group id");
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

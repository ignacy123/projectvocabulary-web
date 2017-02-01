package com.github.ignacy123.projectvocabulary.web.repository;

import com.github.ignacy123.projectvocabulary.web.domain.User;
import com.github.ignacy123.projectvocabulary.web.dto.UserNotFoundException;
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
 * Created by ignacy on 19.05.16.
 */

public class UserXmlRepository implements UserRepositoryTwo {
    private final File repositoryFile;
    Users users = new Users();


    public UserXmlRepository(File repositoryFile) {
        this.repositoryFile = repositoryFile;
        loadUsers();
    }


    @Autowired
    public UserXmlRepository(@Value("${projectvocabulary.usersRepositoryFile}") String repositoryFilePath) {
        this(new File(repositoryFilePath));
    }


    @Override
    public User save(User user) {
        checkEmailUnique(user.getEmail());
        user.setId(getMaxId() + 1L);
        users.getUsers().add(user);
        persist();
        return user;
    }

    @Override
    public void persist() {
        try {
            JAXBContext context = JAXBContext.newInstance(Users.class, User.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(users, repositoryFile);
        } catch (JAXBException e) {
            throw new RuntimeException("Couldn't persist repository", e);
        }

    }


    private Long getMaxId() {
        Long maxId = 0L;
        for (User user : users.getUsers()) {
            maxId = Math.max(user.getId(), maxId);
        }
        return maxId;
    }

    @Override
    public User findByEmail(String email) {
        for (User user : users.getUsers()) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        throw new UserNotFoundException("unknown email");
    }

    public void checkEmailUnique(String email) {
        for (User user : users.getUsers()) {
            if (user.getEmail().equals(email)) {
                throw new NotUniqueEmailException("email not unique");
            }
        }
    }

    @Override
    public User findById(Long id) {
        for (User user : users.getUsers()) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        throw new UserNotFoundException("unknown id");
    }


    @Override
    public List<User> findAll() {
        return users.getUsers();
    }

    private void loadUsers() {
        try {
            JAXBContext context = JAXBContext.newInstance(Users.class, User.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            this.users = (Users) unmarshaller.unmarshal(repositoryFile);
        } catch (JAXBException e) {
            throw new InvalidRepositoryFileException(e);
        }

    }


    @XmlRootElement(name = "users")
    @XmlAccessorType(XmlAccessType.FIELD)
    private static class Users {
        @XmlElement(name = "user")
        private List<User> users = new ArrayList<>();

        public List<User> getUsers() {
            return users;
        }

        public void setUsers(List<User> users) {
            this.users = users;
        }
    }
}


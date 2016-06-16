package com.github.ignacy123.projectvocabulary.web.repository;

import com.github.ignacy123.projectvocabulary.web.domain.User;
import com.github.ignacy123.projectvocabulary.web.dto.UserNotFoundException;
import org.apache.commons.io.FileUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ignacy on 19.05.16.
 */
public class UserXmlRepository implements UserRepository {
    private ArrayList<User> userRepository;
    private final File repositoryFile;
    Users users = new Users();


    public UserXmlRepository(File repositoryFile) {
        this.repositoryFile = repositoryFile;
    }
//    private File userData = new File("/home/IdeaProjects/userrep", "rep", ".txt");
//    ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(userData));


    @Override
    public void save(User user) {
        try {
            user.setId(users.getUsers().size()+1L);
            users.getUsers().add(user);
            JAXBContext context = JAXBContext.newInstance(Users.class, User.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(users, repositoryFile);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findByEmail(String email) {
        for (User user : userRepository) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        throw new UserNotFoundException("unknown email");
    }

    @Override
    public User findById(Long id) {
        for (User user : userRepository) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        throw new UserNotFoundException("unknown id");
    }

    @Override
    public void saveToFile(User user) {
//        outputStream.writeObject(user);
//        outputStream.flush();
//        outputStream.close();

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


package com.github.ignacy123.projectvocabulary.web.repository;

import com.github.ignacy123.projectvocabulary.web.domain.User;

/**
 * Created by ignacy on 19.05.16.
 */
public interface UserRepository {
    UserXmlRepository INSTANCE = new UserXmlRepository(null);

    void save(User user);

    static UserXmlRepository getInstance() {
        return INSTANCE;
    }

    User findByEmail(String email);

    User findById(Long id);

    void saveToFile(User user);
}

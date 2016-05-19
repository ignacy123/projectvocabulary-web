package com.github.ignacy123.projectvocabulary.web.domain;

/**
 * Created by ignacy on 19.05.16.
 */
public interface UserRepository {
    UserRepositoryImpl INSTANCE = new UserRepositoryImpl();
    void add (User user);
    static UserRepositoryImpl getInstance() {
        return INSTANCE;
    }
    User findByEmail(String email);
    User findById(Long id);
}

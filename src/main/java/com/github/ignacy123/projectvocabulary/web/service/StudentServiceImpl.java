package com.github.ignacy123.projectvocabulary.web.service;

import com.github.ignacy123.projectvocabulary.web.domain.User;
import com.github.ignacy123.projectvocabulary.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ignacy on 16.11.16.
 */
@Service
public class StudentServiceImpl implements StudentService {
    private final UserRepository studentRepository;

    @Autowired
    public StudentServiceImpl(UserRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional
    @Override
    public User findById(Long id) {
        return  studentRepository.findById(id);
    }
}

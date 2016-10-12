package com.github.ignacy123.projectvocabulary.web.service;

import com.github.ignacy123.projectvocabulary.web.domain.Group;
import com.github.ignacy123.projectvocabulary.web.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ignacy on 31.08.16.
 */
@Service
public class GroupServiceImpl implements GroupService{
    private final GroupRepository repository;
    @Autowired
    public GroupServiceImpl(GroupRepository repository) {
        this.repository = repository;
    }

    @Override
    public Group createGroup(Group group) {
        return repository.save(group);
    }

    @Override
    public List<Group> getTeacherGroups(Long teacherId) {
        return repository.findByTeacherId(teacherId);
    }
}

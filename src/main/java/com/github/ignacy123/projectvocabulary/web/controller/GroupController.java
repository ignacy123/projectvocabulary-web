package com.github.ignacy123.projectvocabulary.web.controller;

import com.github.ignacy123.projectvocabulary.web.domain.Group;
import com.github.ignacy123.projectvocabulary.web.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by ignacy on 31.08.16.
 */
@RestController
@RequestMapping("/groups")
public class GroupController {
    private final GroupService service;

    @Autowired
    public GroupController(GroupService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST, value = "")
    public Group create(@RequestBody Group group) {
        return service.createGroup(group);
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    public List<Group> getTeacherGroups(@RequestParam Long teacherId) {
        return service.getTeacherGroups(teacherId);
    }



}

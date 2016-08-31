package com.github.ignacy123.projectvocabulary.web.controller;

import com.github.ignacy123.projectvocabulary.web.domain.Group;
import com.github.ignacy123.projectvocabulary.web.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

}

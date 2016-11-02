package com.github.ignacy123.projectvocabulary.web.controller;

import com.github.ignacy123.projectvocabulary.web.domain.Invitation;
import com.github.ignacy123.projectvocabulary.web.dto.InvitationAcceptanceDto;
import com.github.ignacy123.projectvocabulary.web.dto.InvitationDto;
import com.github.ignacy123.projectvocabulary.web.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by ignacy on 25.10.16.
 */
@RestController
@RequestMapping("/groups")
public class InvitationController {
    private final GroupService service;

    @Autowired
    public InvitationController(GroupService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{groupId}/invitation")
    public Invitation createInvitation(@PathVariable Long groupId, @RequestBody InvitationDto dto) {
        return service.createInvitation(groupId, dto);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/invitations/acceptances")
    public void acceptInvitation(@RequestBody InvitationAcceptanceDto acceptanceDto){
        service.acceptInvitation(acceptanceDto);
    }
}

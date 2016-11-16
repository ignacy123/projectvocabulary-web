package com.github.ignacy123.projectvocabulary.web.dto;

import javax.validation.constraints.NotNull;

/**
 * Created by ignacy on 26.10.16.
 */
public class InvitationAcceptanceDto {
    @NotNull
    private String invitationUid;
    @NotNull
    private Long studentId;

    public String getInvitationUid() {
        return invitationUid;
    }

    public void setInvitationUid(String invitationUid) {
        this.invitationUid = invitationUid;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}

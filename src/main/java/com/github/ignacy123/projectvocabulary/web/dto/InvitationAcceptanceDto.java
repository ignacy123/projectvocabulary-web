package com.github.ignacy123.projectvocabulary.web.dto;

/**
 * Created by ignacy on 26.10.16.
 */
public class InvitationAcceptanceDto {
    private String invitationUid;

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

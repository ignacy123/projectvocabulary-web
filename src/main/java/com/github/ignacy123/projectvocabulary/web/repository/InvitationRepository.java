package com.github.ignacy123.projectvocabulary.web.repository;

import com.github.ignacy123.projectvocabulary.web.domain.Invitation;

import java.util.UUID;

/**
 * Created by ignacy on 19.10.16.
 */
public interface InvitationRepository {
    Invitation save(Invitation invitation);

    void persist();

    Invitation findByUid(String invitationUid);

    void delete(String invitationUid);
}

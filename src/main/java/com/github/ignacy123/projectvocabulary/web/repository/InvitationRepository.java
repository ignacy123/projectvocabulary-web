package com.github.ignacy123.projectvocabulary.web.repository;

import com.github.ignacy123.projectvocabulary.web.domain.Invitation;

/**
 * Created by ignacy on 19.10.16.
 */
public interface InvitationRepository {
    Invitation save(Invitation invitation);

    void persist();
}

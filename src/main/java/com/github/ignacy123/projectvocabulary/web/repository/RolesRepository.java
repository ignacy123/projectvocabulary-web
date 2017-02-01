package com.github.ignacy123.projectvocabulary.web.repository;

import com.github.ignacy123.projectvocabulary.web.domain.Role;

import java.util.Set;

/**
 * Created by ignacy on 11.01.17.
 */
public interface RolesRepository {

	/**
	 * Return roles associated with given user id
	 *
	 * @param userId
	 * @return set of user roles
	 */
	Set<Role> getRoles(Long userId);
}

package service;

import java.util.List;

import javax.ejb.Local;

import model.entity.Role;
import model.entity.Role.UserRoleName;

@Local
public interface RoleServiceLocal {

	/**
	 * Get all available user roles.
	 */
	List<UserRoleName> getAllRoleNames();

	List<Role> getByName(final UserRoleName roleName);

}

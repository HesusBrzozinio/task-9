package service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.entity.Role;
import model.entity.User;
import model.entity.Role.UserRoleName;
import model.entity.User.UserState;
import model.security.UserDTO;

@Stateless
@LocalBean
public class UserFactoryBean {

	@PersistenceContext
	private EntityManager manager;

	@EJB
	private RoleServiceLocal roleService;

	public User create(final UserDTO userData) {

		final List<Role> role = roleService.getByName(UserRoleName.BASIC);

		final User user = new User();
		user.setName(userData.getUsername());
		user.setPassword(userData.getPassword());
		user.setState(UserState.ACTIVE);
		user.setRoles(role);

		return user;
	}
}

package service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import model.entity.Role;
import model.entity.Role.UserRoleName;
import model.entity.User;
import model.security.UserDTO;

@Stateless
@LocalBean
public class UserTransformationBean {

	/**
	 * Transform list of entities to list of DTO.
	 */
	public List<UserDTO> toDTO(final List<User> entity) {
		final List<UserDTO> users = new ArrayList<UserDTO>(entity.size());
		entity.forEach(e -> {
			users.add(transform(e));
		});
		return users;
	}

	public UserDTO toDTO(final User entity) {
		return transform(entity);
	}

	/**
	 * Transform entity to DTO
	 */
	private UserDTO transform(final User user) {
		final UserDTO usr = new UserDTO();
		usr.setId(user.getId());
		usr.setUsername(user.getName());
		usr.setState(user.getState());
		usr.setRoles(transform(user.getRoles()));
		return usr;
	}

	private List<UserRoleName> transform(List<Role> roles) {
		final List<UserRoleName> rls = new ArrayList<>(roles.size());
		roles.forEach(r -> {
			rls.add(r.getName());
		});
		return rls;
	}

	public User toEntity(final UserDTO dto) {
		return null;
	}

}

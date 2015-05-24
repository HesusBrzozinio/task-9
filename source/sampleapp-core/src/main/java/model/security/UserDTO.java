package model.security;

import java.util.ArrayList;
import java.util.List;

import model.entity.Role.UserRoleName;
import model.entity.User.UserState;

public class UserDTO {

	private Integer id;
	private String username;
	private String password;
	private UserState state;
	private List<UserRoleName> roles = new ArrayList<>();

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<UserRoleName> getRoles() {
		return roles;
	}

	public void setRoles(List<UserRoleName> roles) {
		this.roles = roles;
	}

	public void setState(UserState state) {
		this.state = state;
	}

	public UserState getState() {
		return state;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", username=" + username + ", state="
				+ state + ", roles=" + roles + "]";
	}
}

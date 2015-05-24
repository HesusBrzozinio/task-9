package service.security;

import javax.ejb.Local;

import model.security.UserDTO;

@Local
public interface UserAuthenticationServiceLocal {

	UserDTO authenticate(final String username, final String password);
}

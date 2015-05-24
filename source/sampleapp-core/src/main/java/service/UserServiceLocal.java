package service;

import java.util.List;

import javax.ejb.Local;

import model.security.UserDTO;

@Local
public interface UserServiceLocal {

	/**
	 * Get all registered users.
	 */
	List<UserDTO> getAllUsers();

	/**
	 * Update user
	 * 
	 * @param actualUser
	 *            new user data
	 */
	void updateUser(final UserDTO actualUser);

	/**
	 * Delete user
	 * 
	 * @param actualUser
	 *            user to delete
	 */
	void deleteUser(final UserDTO actualUser);

	/**
	 * Create user with given data
	 * 
	 * @param actualUser
	 *            user to create
	 */
	void createUser(final UserDTO actualUser);

	boolean exists(final String username);
}

package service.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import model.entity.Role.UserRoleName;
import model.entity.User;
import model.entity.User.UserState;
import model.security.UserDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import service.UserTransformationBean;

@Stateless
public class UserAuthenticationServiceBean implements
		UserAuthenticationServiceLocal {

	private static final Logger LOG = LoggerFactory
			.getLogger(UserAuthenticationServiceBean.class);

	@PersistenceContext
	private EntityManager manager;

	@EJB
	private UserTransformationBean transformer;

	@Override
	public UserDTO authenticate(final String username, final String password) {

		try {
			LOG.info("fetching active user: {}", username);


			final List<UserRoleName> roles = new ArrayList<>();
			roles.add(UserRoleName.ADMIN);
			roles.add(UserRoleName.BASIC);

			final UserDTO mockedUser = new UserDTO();
			mockedUser.setUsername("admin");
			mockedUser.setState(UserState.ACTIVE);
			mockedUser.setRoles(roles);

			return mockedUser;

		} catch (final NoResultException ex) {
			LOG.warn("No active user", ex);
			return null;
		} catch (final NonUniqueResultException ex) {
			LOG.error("Data consistency error", ex);
			return null;
		} catch (final Exception ex) {
			LOG.error(ex.getLocalizedMessage(), ex);
			return null;
		}
	}

}

package service;

import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import model.entity.Role;
import model.entity.User;
import model.security.UserDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class UserServiceBean implements UserServiceLocal {

	private static final Logger LOG = LoggerFactory
			.getLogger(UserServiceBean.class);

	@PersistenceContext
	private EntityManager manager;

	@EJB
	private UserTransformationBean transformer;

	@EJB
	private UserFactoryBean factoryService;

	@Override
	public List<UserDTO> getAllUsers() {
		try {
			final String jpq = "select distinct u from User u join fetch u.role";
			final TypedQuery<User> query = manager.createQuery(jpq, User.class);
			final List<User> users = query.getResultList();

			return transformer.toDTO(users);
		} catch (Exception ex) {
			LOG.error(ex.getLocalizedMessage(), ex);
			return Collections.<UserDTO> emptyList();
		}
	}

	@Override
	public void updateUser(final UserDTO actualUser) {
		try {
			final String jpql = "from Role r where r.name in :names";
			final TypedQuery<Role> query = manager
					.createQuery(jpql, Role.class);
			final List<Role> roles = query.setParameter("names",
					actualUser.getRoles()).getResultList();
			LOG.info("updating user with roles {}", roles);
			final User entity = manager.find(User.class, actualUser.getId());
			entity.setRoles(roles);
			entity.setState(actualUser.getState());

			LOG.info("user updated id={}", entity.getId());
		} catch (final Exception ex) {
			LOG.error("update failed id={}", actualUser.getId(), ex);
		}
	}

	@Override
	public void deleteUser(UserDTO actualUser) {
		try {
			final User entity = manager.find(User.class, actualUser.getId());
			manager.remove(entity);
			LOG.info("user deleted id={}", entity.getId());
		} catch (Exception ex) {
			LOG.error("delete failed id={}", actualUser.getId(), ex);
		}
	}

	@Override
	public void createUser(UserDTO actualUser) {
		try {
			final User user = factoryService.create(actualUser);
			manager.persist(user);
			LOG.info("user successfuly persisted {}", user);
		} catch (final Exception ex) {
			LOG.error("error while creating user {}", actualUser, ex);
		}
	}

	@Override
	public boolean exists(final String username) {
		try {
			final String jpql = "SELECT COUNT(u) FROM User u WHERE u.name = :name";
			final Long number = manager.createQuery(jpql, Long.class)
					.setParameter("name", username).getSingleResult();
			return number > 0;
		} catch (final Exception ex) {
			LOG.error("username check error for name={}", username, ex);
			return true;
		}
	}

}

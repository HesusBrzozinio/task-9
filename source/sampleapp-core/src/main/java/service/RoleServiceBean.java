package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import model.entity.Role;
import model.entity.Role.UserRoleName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class RoleServiceBean implements RoleServiceLocal {

	private static final Logger LOG = LoggerFactory
			.getLogger(RoleServiceBean.class);

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<UserRoleName> getAllRoleNames() {
		try {
			final String jpq = "from Role r";
			final TypedQuery<Role> query = manager.createQuery(jpq, Role.class);
			final List<Role> roles = query.getResultList();
			return getRoleNames(roles);
		} catch (Exception ex) {
			LOG.error(ex.getLocalizedMessage(), ex);
			return Collections.<UserRoleName> emptyList();
		}
	}

	private List<UserRoleName> getRoleNames(final List<Role> roles) {
		final List<UserRoleName> names = new ArrayList<UserRoleName>(
				roles.size());
		roles.forEach(r -> {
			names.add(r.getName());
		});
		return names;
	}

	@Override
	public List<Role> getByName(UserRoleName roleName) {
		try {
			final String jpql = "from Role r where r.name = :name";
			final TypedQuery<Role> query = manager
					.createQuery(jpql, Role.class);

			return query.setParameter("name", roleName).getResultList();

		} catch (Exception ex) {
			LOG.error("error while fetching role with name {}", roleName, ex);
			return Collections.<Role> emptyList();
		}
	}
}

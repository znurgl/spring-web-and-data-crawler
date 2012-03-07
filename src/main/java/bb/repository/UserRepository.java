package bb.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import bb.domain.Role;
import bb.domain.User;

@Repository
public class UserRepository {

	private final static Log log = LogFactory.getLog(UserRepository.class);

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public List<User> findAll() {

		return em.createQuery("from User").getResultList();
	}

	@Transactional
	public User findByLogin(String login) {
		User user = null;

		try {
			user = (User) em.createNamedQuery("findByLogin")
					.setParameter("login", login).getSingleResult();
		} catch (Exception e) {
			log.debug("User.findByLogin error: " + login);
		}

		return user;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void register(String login, String name, String password) {
		Role role = (Role) em
				.createQuery("select o from Role o where value = :value")
				.setParameter("value", "USER").getSingleResult();
		List<Role> roles = new ArrayList<Role>();
		roles.add(role);
		User user = new User();
		user.setLogin(login);
		user.setFirstName(name);
		user.setPassword(password);
		user.setRoles(roles);
		create(user);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(User user) {
		em.merge(user);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void create(User user) {
		em.persist(user);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Role role) {
		em.merge(role);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void create(Role role) {
		em.persist(role);
	}

}

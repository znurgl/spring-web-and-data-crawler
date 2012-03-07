package bb.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import bb.domain.Role;

@Repository
public class RoleRepository extends GenericDao<Role, Long> {

	public RoleRepository() {
	}

	private final static Log log = LogFactory.getLog(UserRepository.class);

	@PersistenceContext
	private EntityManager em;

	public Role findByValue(String value) {
		Role role = null;

		try {
			role = (Role) em.createNamedQuery("findByValue")
					.setParameter("value", value).getSingleResult();
		} catch (Exception e) {
			log.debug("Role.findByValue error: " + value);
		}

		return role;
	}

	public List<Role> findAll() {

		return em.createQuery("from User").getResultList();
	}

}

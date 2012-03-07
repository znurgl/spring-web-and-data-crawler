package bb.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class GenericDao<E, PK extends Serializable> {
    
	protected final static Log log = LogFactory.getLog(UserRepository.class);
	
	private Class<E> type;
	 
	@PersistenceContext
    protected EntityManager em;
	
	public synchronized  E update(E entity) {
		em.merge(entity);
		return entity;
	}


	public void create(List<E> entities) {
		for (E e : entities) {
			create(e);
		}
	}
	
	public E read(PK id) {
		return (E) em.find( type, id);
	}

	public void delete(E entity) {
		em.remove(entity);
	}
	
	public synchronized  E create(E entity) {

		log.debug("entity mentese: " + entity);

		em.persist(entity);
		return entity;
	}


	
}


package bb.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import bb.domain.Dictionary;

@Repository
public class DictionaryRepository extends GenericDao<Dictionary, Long> {

	private final static Log log = LogFactory
			.getLog(DictionaryRepository.class);

	@PersistenceContext
	private EntityManager em;

	public List<String> allValue() {
		return em.createQuery("select LOWER(d.value) from Dictionary d")
				.getResultList();
	}

	public Dictionary findByValue(String value) {
		Dictionary dic = null;
		dic = (Dictionary) em.createNamedQuery("Dictionary.findByValue")
				.setParameter("value", value).getSingleResult();
		return dic;

	}

}

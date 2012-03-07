package bb.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import bb.domain.Campaign;
import bb.domain.Keyword;

@Repository
public class KeywordRepository extends GenericDao<Keyword, Long> {

	private final static Log log = LogFactory.getLog(KeywordRepository.class);

	@PersistenceContext
	private EntityManager em;

	public List<Keyword> findAll() {

		return em.createQuery("from Keyword").getResultList();
	}
	
	public List<Keyword> findByCampaign(Campaign campaign) {
		
		List<Keyword> campaignList = null;

		try {
			campaignList = em.createQuery("select o from Keyword o left join o.campaign where o.campaign = :campaign")
					.setParameter("campaign", campaign).getResultList();
		} catch (Exception e) {
			log.debug("A keresett Keyword nem talalhato: " + campaign);
			e.printStackTrace();
		}
		return campaignList;
	}

}

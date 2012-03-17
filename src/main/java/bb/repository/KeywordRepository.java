package bb.repository;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bb.domain.Campaign;
import bb.domain.Data;
import bb.domain.Keyword;

@Repository
@Transactional
public class KeywordRepository extends GenericDao<Keyword, Long> {

	private final static Log log = LogFactory.getLog(KeywordRepository.class);

	@PersistenceContext
	private EntityManager em;

	public List<Keyword> findAll() {

		return em.createQuery("from Keyword").getResultList();
	}

	public void addDataToKeyword(Data data, Keyword keyword) {
		
		
		
		
		
		BigInteger res = (BigInteger)em
				.createNativeQuery(
						"select Count(*) from Keyword_Data where keywords_id = :keyword_id and data_id = :data_id")
				.setParameter("keyword_id", keyword.getId())
				.setParameter("data_id", data.getId()).getSingleResult();
		log.debug("Keyword_Data: " + res);
		if (res == BigInteger.valueOf(0) ) {
			log.debug("Keyword_Data add to ...");
			em.createNativeQuery(
					"insert into Keyword_Data values (:keyword_id,:data_id)")
					.setParameter("keyword_id", keyword.getId())
					.setParameter("data_id", data.getId()).executeUpdate();
		}
		
	}

	public boolean isDataInKeywords(Data data, Keyword keyword) {

		boolean resp = false;

		try {
			Long count = (Long)em.createQuery(
					"select count(o) from Keyword o left join o.data d where d = :data and o = :keyword")
					.setParameter("data", data).
					setParameter("keyword", keyword).
					getSingleResult();
			if ( count > 0) {
				resp = true;
			}
		} catch (Exception e) {
			log.debug("A keresett Keyword nem talalhato: " + data);
			e.printStackTrace();
		}
		return resp;
	}

	public List<Keyword> findByData(Data data) {

		List<Keyword> keywordList = null;

		try {
			keywordList = em
					.createQuery(
							"select o from Keyword o left join o.data where o.data = :data")
					.setParameter("data", data).getResultList();
		} catch (Exception e) {
			log.debug("A keresett Keyword nem talalhato: " + data);
			e.printStackTrace();
		}
		return keywordList;
	}

	public List<Keyword> findByCampaign(Campaign campaign) {

		List<Keyword> campaignList = null;

		try {
			campaignList = em
					.createQuery(
							"select o from Keyword o left join o.campaign where o.campaign = :campaign")
					.setParameter("campaign", campaign).getResultList();
		} catch (Exception e) {
			log.debug("A keresett Keyword nem talalhato: " + campaign);
			e.printStackTrace();
		}
		return campaignList;
	}

}

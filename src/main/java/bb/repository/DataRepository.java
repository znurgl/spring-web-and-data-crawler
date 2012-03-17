package bb.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bb.domain.Campaign;
import bb.domain.Data;
import bb.domain.Keyword;

@Repository
public class DataRepository extends GenericDao<Data, Long> {

	public Data findBySourceId(String sourceId) {
		Data data = null;

		try {
			data = (Data) em.createNamedQuery("Data.findBySourceId")
					.setParameter("sourceId", sourceId).getSingleResult();
		} catch (Exception e) {
			log.debug("A keresett Data nem talalhato: " + sourceId);
		}
		return data;
	}

	public Data findBySourceIdAndKeyword(String sourceId, Keyword k) {
		Data data = null;

		try {
			data = (Data) em.createNamedQuery("Data.findBySourceIdAndKeyword")
					.setParameter("sourceId", sourceId).getSingleResult();
		} catch (Exception e) {
			log.debug("A keresett Data nem talalhato: " + sourceId);
		}
		return data;
	}

	@Transactional
	public List<Data> allByCampaign(Campaign campaign) {
		List<Data> dataList = null;

		try {
			dataList = em.createQuery("select o from Data o left join fetch o.keywords k left join k.campaign where k.campaign = :campaign")
						.setParameter("campaign", campaign).
						setFirstResult(0).
						setMaxResults(20).
						getResultList();
			//dataList = em
			//		.createNativeQuery(
			//				"select * from Data d left join Keyword_data kd on d.id = kd.data_id left join keyword k on kd.keywords_id = k.id left join campaign c on k.campaign = :campaign limit 0,100",
			//				Data.class)
			//		.setParameter("campaign", campaign.getId()).getResultList();
		} catch (Exception e) {
			log.debug("A keresett Data nem talalhato: " + campaign);
			e.printStackTrace();
		}
		return dataList;
	}

}

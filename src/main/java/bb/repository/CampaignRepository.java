package bb.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import bb.domain.Campaign;
import bb.domain.Company;

@Repository
public class CampaignRepository/* implements GenericDao<Campaign, Long>*/ {
	
	private final static Log log = LogFactory.getLog(UserRepository.class);
	 
	@PersistenceContext
    private EntityManager em;
	
	public List<Campaign> findAllByCompany(Company company){
		return em.createNamedQuery("findAllByCompany").setParameter("company", company).getResultList();
	}

    public Campaign create(Campaign t) {
        this.em.persist(t);
        return t;
    }

    public Campaign read(Long id) {
        return this.em.find(Campaign.class, id);
    }

    public Campaign update(Campaign t) {
        return this.em.merge(t);
    }

    public void delete(Campaign t) {
        t = this.em.merge(t);
        this.em.remove(t);
    }

	

}

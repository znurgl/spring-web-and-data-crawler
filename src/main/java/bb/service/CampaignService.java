package bb.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bb.domain.Campaign;
import bb.domain.Company;
import bb.repository.CampaignRepository;

@Service
@Transactional
public class CampaignService {

	private final static Log log = LogFactory.getLog(UserService.class);

	@Autowired
	CampaignRepository campaignRepository;

	public List<Campaign> findAllByCompany(Company company) {
		return campaignRepository.findAllByCompany(company);
	}

	public Campaign create(Campaign t) {
		return campaignRepository.create(t);
	}

	public Campaign read(Long id) {
		return campaignRepository.read(id);
	}

	public Campaign update(Campaign t) {
		return campaignRepository.update(t);
	}

	public void delete(Campaign t) {
		campaignRepository.delete(t);
	}

}

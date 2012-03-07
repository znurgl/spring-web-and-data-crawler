package bb.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bb.domain.Company;
import bb.repository.CompanyRepository;

@Service
@Transactional
public class CompanyService {
	
	private final static Log log = LogFactory.getLog(UserService.class);

	@Autowired
	CompanyRepository service;

	public Company create(Company t) {
		return service.create(t);
	}

	public Company read(Long id) {
		return service.read(id);
	}

	public Company update(Company t) {
		return service.update(t);
	}

	public void delete(Company t) {
		service.delete(t);
	}


}

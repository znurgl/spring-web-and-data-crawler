package bb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bb.domain.Keyword;
import bb.repository.KeywordRepository;

@Service
@Transactional
public class KeywordService {

	@Autowired
	KeywordRepository keywordRepository;

	public List<Keyword> allKeywords() {
		return keywordRepository.findAll();
	}

	public Keyword create(Keyword t) {
		// TODO Auto-generated method stub
		return keywordRepository.create(t);
	}

	public Keyword read(Long id) {
		// TODO Auto-generated method stub
		return keywordRepository.read(id);
	}

	public Keyword update(Keyword t) {
		// TODO Auto-generated method stub
		return keywordRepository.update(t);
	}

	public void delete(Keyword t) {
		keywordRepository.delete(t);

	}

}

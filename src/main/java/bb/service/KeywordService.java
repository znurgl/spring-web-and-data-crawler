package bb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bb.domain.Data;
import bb.domain.Keyword;
import bb.repository.KeywordRepository;

@Service
@Transactional
public class KeywordService {

	@Autowired
	KeywordRepository keywordRepository;
	
	public boolean isDataInKeywords(Data data, Keyword keyword) {
		return keywordRepository.isDataInKeywords(data, keyword);
	}
	
	public void addDataToKeyword(Data data, Keyword keyword) {
		keywordRepository.addDataToKeyword(data, keyword);
	}

	public List<Keyword> allKeywords() {
		return keywordRepository.findAll();
	}

	public Keyword create(Keyword t) {
		return keywordRepository.create(t);
	}

	public Keyword read(Long id) {
		return keywordRepository.read(id);
	}

	public Keyword update(Keyword t) {
		return keywordRepository.update(t);
	}

	public void delete(Keyword t) {
		keywordRepository.delete(t);
	}

}

package bb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bb.domain.Keyword;
import bb.domain.Language;
import bb.repository.LanguageRepository;

@Service
public class LanguageService {

	@Autowired
	LanguageRepository languageRepository;

	public Language findByLocale(String locale) {
		return languageRepository.findByLocale(locale);
	}
	
	public Language create(Language t) {
		return languageRepository.create(t);
	}

	public Language read(Long id) {
		return languageRepository.read(id);
	}

	public Language update(Language t) {
		return languageRepository.update(t);
	}

	public void delete(Language t) {
		languageRepository.delete(t);
	}

}

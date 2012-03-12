package bb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bb.domain.Language;
import bb.repository.LanguageRepository;

@Service
public class LanguageService {

	@Autowired
	LanguageRepository languageRepository;

	public Language findByLocale(String locale) {
		return languageRepository.findByLocale(locale);
	}

}
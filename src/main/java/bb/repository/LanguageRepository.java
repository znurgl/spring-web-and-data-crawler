package bb.repository;

import org.springframework.stereotype.Repository;

import bb.domain.Language;

@Repository
public class LanguageRepository extends GenericDao<Language, Long> {

	public Language findByLocale(String locale) {
		Language lan = null;
		lan = (Language) em.createNamedQuery("Language.findByLocale")
				.setParameter("locale", locale).getSingleResult();
		return lan;
	}

}

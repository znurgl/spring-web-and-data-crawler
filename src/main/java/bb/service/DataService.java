package bb.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bb.crawler.CrawlerJob;
import bb.domain.Data;
import bb.domain.Keyword;
import bb.domain.SearchSession;
import bb.repository.DataRepository;

@Service
public class DataService {
	
	private final static Log log = LogFactory.getLog(DataService.class);

	@Autowired
	DataRepository dataRepository;
	
	@Autowired
	KeywordService keywordService;

	public void createData(String sourceId, String body, String url, String title, 
			String type, SearchSession searchSession, Date originalDate, Keyword k) {

		Data data = dataRepository.findBySourceId(sourceId);
		if (data == null || data.getId() == null) {
			data = new Data();
			try {
				data.setSourceId(sourceId);

				data.setType(type);
				data.setBody(body);
				data.setUrl(url);
				data.setTitle(title);
				data.setCreateDate(Calendar.getInstance().getTime());				
				data.setOriginalDate(originalDate);
				data.setSearchSession(searchSession);

				List<Keyword> keys = new ArrayList<Keyword>();
				keys.add(k);

				data.setKeywords(keys);

				dataRepository.create(data);

			} catch (Exception e) {
				log.debug("hiba a data letrehozasakor");
				e.printStackTrace();
			}
		}

		// if (data.getKeywords() == null
		// || !data.getKeywords().contains(k)) {

		// List<Keyword> keys = data.getKeywords();
		// if (keys == null) {
		// new ArrayList<Keyword>();
		// }

		/*
		 * List<Keyword> keys = new ArrayList<Keyword>();
		 * 
		 * keys.add(k);
		 * 
		 * data.setKeywords(keys);
		 * 
		 * dataRepository.update(data);
		 */
		// }

		if (!keywordService.isDataInKeywords(data, k)) {
			keywordService.addDataToKeyword(data, k);
		}

	}

}

package bb.crawler;

import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import bb.crawler.FacebookResponse.FBData;
import bb.domain.Data;
import bb.domain.Keyword;
import bb.repository.DataRepository;
import bb.repository.KeywordRepository;
import bb.repository.SearchSessionRepository;

import com.google.gson.Gson;

public class CrawlerJob {

	@Autowired
	KeywordRepository keywordRepository;

	@Autowired
	DataRepository dataRepository;

	@Autowired
	SearchSessionRepository searchSessionRepository;

	private final static Log log = LogFactory.getLog(CrawlerJob.class);

	protected synchronized void collectData() throws JobExecutionException {
		log.debug("crawler inditasa...");
		// FacebookCrawler fbc = new FacebookCrawler();

		Keyword k = new Keyword();
		k.setValue("tavasz");

		getFacebookResults(k);
	}

	private void getFacebookResults(Keyword k) {

		log.debug("name: " + k.getValue());
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		HttpClient httpClient = new DefaultHttpClient();

		params.add(new BasicNameValuePair("q", k.getValue()));
		params.add(new BasicNameValuePair("limit", "400"));

		String query = URLEncodedUtils.format(params, "utf-8");

		URI url = null;
		try {

			url = URIUtils.createURI("https", "graph.facebook.com", 0,
					"search", query, null);

			log.debug(url.toString());
			HttpGet httpGet = new HttpGet(url);

			HttpResponse r = httpClient.execute(httpGet);
			HttpEntity entity = r.getEntity();

			// System.out.println(EntityUtils.toString(entity));

			Gson gson = new Gson();
			String respRow = EntityUtils.toString(entity);

			log.debug(respRow);

			FacebookResponse respList = gson.fromJson(respRow,
					FacebookResponse.class);

			// System.out.println(respList);

			for (FBData d : respList.data) {
				// ellenorzi sourceid alapjan, hogy szerepel-e az
				// adatbaziban

				log.debug(d);

				Data data = dataRepository.findBySourceIdAndKeyword(d.id, k);
				if (data == null) {
					data = new Data();
					try {
						data.setSourceId(d.id);

						data.setTitle((d.name != null && d.name.length() > 100) ? d.name
								.substring(0, 100) : d.name);
						data.getKeywords().add(k);
						data.setCreateDate(Calendar.getInstance().getTime());
						data.setType("facebook");
						data.setBody("body"); //
						// data.setTitle(d.caption);
						dataRepository.create(data);
					} catch (Exception e) {
						log.debug("hiba a data letrehozasakor");
					}
				} else if (!data.getKeywords().contains(k)) {
					List<Data> keyData = k.getData();

					keyData.add(data);
					k.setData(keyData);
					keywordRepository.update(k);

				} else {
					log.debug("A data objektum mar letezik: " + data);
				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

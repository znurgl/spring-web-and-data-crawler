package bb.crawler;

import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import bb.crawler.FacebookResponse.FBData;
import bb.crawler.TwitterResponse.TwitterResult;
import bb.domain.Keyword;
import bb.domain.SearchSession;
import bb.repository.DataRepository;
import bb.repository.KeywordRepository;
import bb.repository.SearchSessionRepository;
import bb.service.DataService;
import bb.service.DictionaryService;
import bb.service.KeywordService;

import com.google.gson.Gson;

public class CrawlerJob {

	@Autowired
	KeywordService keywordService;

	@Autowired
	KeywordRepository keywordRepository;

	@Autowired
	DataRepository dataRepository;

	@Autowired
	DictionaryService dictionaryService;

	@Autowired
	SearchSessionRepository searchSessionRepository;
	
	@Autowired
	DataService dataService;

	private final static Log log = LogFactory.getLog(CrawlerJob.class);

	public synchronized void collectData() throws JobExecutionException {
		log.debug("crawler inditasa...");
		List<Keyword> keywords = keywordService.allKeywords();

		for (Keyword k : keywords) {
			//getFacebookResults(k);
			//getTwitterResults(k);
			getBloghuResults(k);
		}
	}

	public String getStringFromUrl(URI url, int restart) {
		String resp = null;
		boolean finish = false;

		if (restart > 0) {

			try {
				HttpGet httpGet = new HttpGet(url);
				HttpClient httpClient = new DefaultHttpClient();

				HttpResponse r = httpClient.execute(httpGet);
				HttpEntity entity = r.getEntity();

				// System.out.println(EntityUtils.toString(entity));

				resp = EntityUtils.toString(entity);
				finish = true;
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (!finish) {
				try {
					Thread.sleep(30000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				resp = getStringFromUrl(url, restart);
			}

		}

		return resp;
	}

	private void getFacebookResults(Keyword k) {
		
		

		log.debug("name: " + k.getValue());
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("q", k.getValue()));
		params.add(new BasicNameValuePair("limit", "200"));

		String query = URLEncodedUtils.format(params, "utf-8");
		
		SearchSession searchSession = new SearchSession();
		searchSession.setStartDate(Calendar.getInstance().getTime());		
		searchSession.setSearchText(query);
		searchSessionRepository.create(searchSession);

		URI url = null;
		try {

			url = URIUtils.createURI("https", "graph.facebook.com", 0,
					"search", query, null);

			log.debug(url.toString());

			String respRow = getStringFromUrl(url, 3);
			
			searchSession.setRawData(respRow);

			Gson gson = new Gson();


			FacebookResponse respList = gson.fromJson(respRow,
					FacebookResponse.class);


			for (FBData d : respList.data) {

				boolean validText = false;

				String body = new String(d.message.getBytes("UTF-8"), "UTF-8");

				if (body.length() > 2) {
					boolean resp = dictionaryService.valideText(body, 50);

					if (resp) {
						validText = true;
					} else {
						log.debug("nem magyar szoveg: " + body);

					}

				} else {
					log.debug("a body nem eleg hosszu ");
				}

				if (validText) {
					
					DateFormat formatter;
					Date originalDate;
					formatter = new SimpleDateFormat(
							"yyyy-MM-dd'T'HH:mm:ss");
					originalDate = formatter.parse(d.created_time);
					
					String title = (d.name != null && d.name.length() > 100) ? d.name
							.substring(0, 100) : d.name;
					
					dataService.createData(d.id, body, url.toString(), title, "facebook", searchSession, originalDate, k);

					
				}

			}
			
			searchSession.setEndDate(Calendar.getInstance().getTime());
			searchSessionRepository.update(searchSession);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void getTwitterResults(Keyword k) {

		log.debug("name: " + k.getValue());
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("q", k.getValue()));

		String query = URLEncodedUtils.format(params, "utf-8");

		URI url = null;
		try {

			SearchSession searchSession = new SearchSession();

			searchSession.setStartDate(Calendar.getInstance().getTime());

			url = URIUtils.createURI("http", "search.twitter.com", 0,
					"search.json", query, null);

			searchSession.setSearchText(url.toString());

			log.debug(url.toString());

			Gson gson = new Gson();
			String respRow = getStringFromUrl(url, 3);
			TwitterResponse respList = gson.fromJson(respRow,
					TwitterResponse.class);

			// System.out.println(respList);

			searchSession.setRawData(respRow);
			searchSessionRepository.create(searchSession);
			for (TwitterResult d : respList.results) {
				// ellenorzi sourceid alapjan, hogy szerepel-e az
				// adatbaziban

				String body = URLDecoder.decode(d.text, "UTF-8");

				boolean validText = false;

				if (body.length() > 2) {
					boolean resp = dictionaryService.valideText(body, 50);

					if (resp) {
						validText = true;
					} else {
						log.debug("nem magyar szoveg: " + body);

					}

				} else {
					log.debug("a body nem eleg hosszu ");
				}

				log.debug(d.id);

				if (validText) {
					
					DateFormat formatter;
					Date originalDate;
					formatter = new SimpleDateFormat(
							"E, dd MMM yyyy HH:mm:ss Z");
					originalDate = formatter.parse(d.created_at);
					
					dataService.createData(d.id, body, url.toString(), d.to_user, "twitter", searchSession, originalDate, k);

					
				}

			}
			searchSession.setEndDate(Calendar.getInstance().getTime());
			searchSessionRepository.update(searchSession);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Document getDocumentByUrl(String url, int restart) {

		Document doc = null;
		boolean finish = false;

		if (restart > 0) {

			try {
				doc = Jsoup.connect(url).get();
				finish = true;
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (!finish) {
				try {
					Thread.sleep(20000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				doc = getDocumentByUrl(url, --restart);
			}
		}

		return doc;

	}


	public void getBloghuResults(Keyword k) {
		try {

			int page = 1;
			boolean sessionEnd = false;

			while (!sessionEnd) {

				String url = "http://blog.hu/cimlap/search/?sterm="
						+ k.getValue() + "&page=" + page++;

				Document doc = getDocumentByUrl(url, 3);

				Elements links = doc.select("#talalatbox h1 a");
				Elements bodies = doc.select("#talalatbox p");

				if (links.size() == 0) {
					sessionEnd = true;
					continue;
				}

				SearchSession searchSession = new SearchSession();
				searchSession.setStartDate(Calendar.getInstance().getTime());
				searchSession.setRawData(doc.html());
				searchSession.setSearchText(url);
				searchSessionRepository.create(searchSession);

				for (int i = 0; i < links.size(); i++) {
					String linkHref = links.get(i).attr("href");
					String linkText = links.get(i).text();
					
					log.debug(linkHref);
					
					Document bodyDoc = getDocumentByUrl(linkHref, 3);
					//log.debug(bodyDoc.html());
					Element bodyElements = bodyDoc.getElementsByClass("post-content").get(0);
					
					String body = bodyElements.text();

					
					log.debug(body);

					boolean resp = dictionaryService.valideText(body, 41);

					if (resp) {
						MessageDigest md = MessageDigest.getInstance("MD5");
						md.update(linkHref.getBytes());

						byte byteData[] = md.digest();

						// convert the byte to hex format method 1
						StringBuffer sb = new StringBuffer();
						for (int j = 0; j < byteData.length; j++) {
							sb.append(Integer.toString(
									(byteData[j] & 0xff) + 0x100, 16)
									.substring(1));
						}

						String sourceId = sb.toString();
						log.debug("sourceId: " + sourceId);
						
						DateFormat formatter;
						Date originalDate;
						formatter = new SimpleDateFormat("yyyymmdd");
						String[] cut = linkHref.split("/");
						originalDate = formatter
								.parse(cut[3] + cut[4] + cut[5]);
						
						dataService.createData(sourceId, body, linkHref, linkText, "bloghu", searchSession, originalDate, k);
						
						
					}

				}
				searchSession.setEndDate(Calendar.getInstance().getTime());
				searchSessionRepository.update(searchSession);
			}// sessionEnd
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

package bb.crawler;

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
import org.jsoup.select.Elements;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import bb.crawler.FacebookResponse.FBData;
import bb.crawler.TwitterResponse.TwitterResult;
import bb.domain.Data;
import bb.domain.Keyword;
import bb.domain.SearchSession;
import bb.repository.DataRepository;
import bb.repository.SearchSessionRepository;
import bb.service.DictionaryService;
import bb.service.KeywordService;

import com.google.gson.Gson;

public class CrawlerJob {

	@Autowired
	KeywordService keywordService;

	@Autowired
	DataRepository dataRepository;

	@Autowired
	DictionaryService dictionaryService;

	@Autowired
	SearchSessionRepository searchSessionRepository;

	private final static Log log = LogFactory.getLog(CrawlerJob.class);

	public synchronized void collectData() throws JobExecutionException {
		log.debug("crawler inditasa...");
		List<Keyword> keywords = keywordService.allKeywords();

		for (Keyword k : keywords) {
			//System.out.println("őűŐŰ");
			//getFacebookResults(k);
			//getTwitterResults(k);
			getBloghuResults(k);
		}
	}

	private void getFacebookResults(Keyword k) {

		log.debug("name: " + k.getValue());
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		HttpClient httpClient = new DefaultHttpClient();

		params.add(new BasicNameValuePair("q", k.getValue()));
		params.add(new BasicNameValuePair("limit", "200"));

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

			//log.debug(respRow);

			FacebookResponse respList = gson.fromJson(respRow,
					FacebookResponse.class);

			// System.out.println(respList);

			for (FBData d : respList.data) {
				// ellenorzi sourceid alapjan, hogy szerepel-e az
				// adatbaziban

				//log.debug(d);

				boolean validText = false;

				String body = new String(d.message.getBytes("UTF-8"), "UTF-8");

				if (body.length() > 2) {
					boolean resp = dictionaryService.valideText(body, 30);

					if (resp) {
						validText = true;
					} else {
						log.debug("nem magyar szoveg: " + body);

					}

				} else {
					log.debug("a body nem eleg hosszu ");
				}

				if (validText) {
					Data data = dataRepository
							.findBySourceIdAndKeyword(d.id, k);
					if (data == null) {
						data = new Data();
						try {
							data.setSourceId(d.id);

							data.setTitle((d.name != null && d.name.length() > 100) ? d.name
									.substring(0, 100) : d.name);

							data.setCreateDate(Calendar.getInstance().getTime());
							data.setType("facebook");
							data.setBody(body); //
							// data.setTitle(d.caption);
							dataRepository.create(data);

							List<Data> keyData = k.getData();
							if (keyData == null) {
								keyData = new ArrayList<Data>();
							}
							keyData.add(data);
							k.setData(keyData);
							keywordService.update(k);

						} catch (Exception e) {
							log.error("hiba a data letrehozasakor");
							e.printStackTrace();
							//log.error(d);
						}
					} else if (!data.getKeywords().contains(k)) {
						List<Data> keyData = k.getData();

						keyData.add(data);
						k.setData(keyData);
						keywordService.update(k);

					} else {
						log.debug("A data objektum mar letezik: " + data);
					}
				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void getTwitterResults(Keyword k) {

		log.debug("name: " + k.getValue());
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		HttpClient httpClient = new DefaultHttpClient();

		params.add(new BasicNameValuePair("q", k.getValue()));
		// params.add(new BasicNameValuePair("limit", "400"));

		String query = URLEncodedUtils.format(params, "utf-8");

		URI url = null;
		try {

			SearchSession searchSession = new SearchSession();

			searchSession.setStartDate(Calendar.getInstance().getTime());

			url = URIUtils.createURI("http", "search.twitter.com", 0,
					"search.json", query, null);

			searchSession.setSearchText(url.toString());

			log.debug(url.toString());
			HttpGet httpGet = new HttpGet(url);

			HttpResponse r = httpClient.execute(httpGet);
			HttpEntity entity = r.getEntity();

			Gson gson = new Gson();
			String respRow = EntityUtils.toString(entity);
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
					boolean resp = dictionaryService.valideText(body, 30);

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

					Data data = dataRepository
							.findBySourceIdAndKeyword(d.id, k);
					if (data == null) {
						data = new Data();
						try {
							data.setSourceId(d.id);

							data.setType("twitter");
							data.setBody(body); //
							// data.setTitle(d.caption);
							data.setCreateDate(Calendar.getInstance().getTime());
							DateFormat formatter;
							Date date;
							formatter = new SimpleDateFormat(
									"E, dd MMM yyyy HH:mm:ss Z");
							log.debug(d.created_at);
							date = formatter.parse(d.created_at);
							data.setOriginalDate(date);
							data.setSearchSession(searchSession);
							dataRepository.create(data);

							List<Data> keyData = k.getData();
							if (keyData == null) {
								keyData = new ArrayList<Data>();
							}
							keyData.add(data);
							k.setData(keyData);
							keywordService.update(k);

						} catch (Exception e) {
							log.debug("hiba a data letrehozasakor");
							e.printStackTrace();
						}
					} else if (!data.getKeywords().contains(k)) {
						List<Data> keyData = k.getData();

						keyData.add(data);
						k.setData(keyData);
						keywordService.update(k);
					} else {
						log.debug("A data objektum mar letezik: " + data);
					}
				}

			}
			searchSession.setEndDate(Calendar.getInstance().getTime());
			searchSessionRepository.update(searchSession);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void getBloghuResults(Keyword k) {
		try {

			int page = 1;
			boolean sessionEnd = false;

			while (!sessionEnd) {

				String url = "http://blog.hu/cimlap/search/?sterm="
						+ k.getValue() + "&page=" + page++;

				Document doc = Jsoup.connect(url).get();

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
					String body = bodies.get(i).text();

					log.debug(linkHref);
					log.debug(linkText);
					log.debug(body);

					boolean resp = dictionaryService.valideText(body, 30);

					if (resp) {
						MessageDigest md = MessageDigest.getInstance("MD5");
						md.update(linkHref.getBytes());

						byte byteData[] = md.digest();

						//convert the byte to hex format method 1
						StringBuffer sb = new StringBuffer();
						for (int j = 0; j < byteData.length; j++) {
							sb.append(Integer.toString(
									(byteData[j] & 0xff) + 0x100, 16)
									.substring(1));
						}

						String sourceId = sb.toString();
						log.debug("sourceId: " + sourceId);

						Data data = dataRepository.findBySourceId(sourceId);
						if (data == null || data.getId() == null) {
							data = new Data();
							try {
								data.setSourceId(sourceId);

								data.setType("bloghu");
								data.setBody(body);
								data.setUrl(linkHref);
								data.setTitle(linkText);
								data.setCreateDate(Calendar.getInstance()
										.getTime());
								DateFormat formatter;
								Date date;
								formatter = new SimpleDateFormat(
										"E, dd MMM yyyy HH:mm:ss Z");
								//log.debug(d.created_at);
								//date = formatter.parse(d.created_at);
								//data.setOriginalDate(date);
								data.setSearchSession(searchSession);
								dataRepository.create(data);

								List<Data> keyData = k.getData();
								if (keyData == null) {
									keyData = new ArrayList<Data>();
								}
								keyData.add(data);
								k.setData(keyData);
								keywordService.update(k);

							} catch (Exception e) {
								log.debug("hiba a data letrehozasakor");
								e.printStackTrace();
							}
						} else if (!k.getData().contains(data)) {
							List<Data> keyData = k.getData();

							keyData.add(data);
							k.setData(keyData);
							keywordService.update(k);
						} else {
							log.debug("A data objektum mar letezik: " + data);
						}

					}

				}
				searchSession.setEndDate(Calendar.getInstance().getTime());
				searchSessionRepository.update(searchSession);
			}//sessionEnd
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

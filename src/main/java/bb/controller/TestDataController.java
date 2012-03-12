package bb.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import bb.crawler.CrawlerJob;
import bb.domain.Campaign;
import bb.domain.Company;
import bb.domain.Dictionary;
import bb.domain.Keyword;
import bb.domain.Language;
import bb.domain.Role;
import bb.domain.User;
import bb.service.CampaignService;
import bb.service.CompanyService;
import bb.service.DictionaryService;
import bb.service.KeywordService;
import bb.service.LanguageService;
import bb.service.UserService;

@Controller
public class TestDataController {

	@Autowired
	UserService userService;

	@Autowired
	CompanyService companyService;

	@Autowired
	KeywordService keywordService;

	@Autowired
	CampaignService campaignService;

	@Autowired
	DictionaryService dictionaryService;

	@Autowired
	LanguageService languageService;

	@RequestMapping(value = "/testcrawler", method = RequestMethod.GET)
	public void TestCrawler() {
		CrawlerJob cj = new CrawlerJob();
		try {
			cj.collectData();
		} catch (JobExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//@RequestMapping(value = "/testdic", method = RequestMethod.GET)
	public void LangParse() {

		try {
			// Open the file that is the first 
			// command line parameter
			FileInputStream fstream = new FileInputStream(
					"/Users/znurgl/Documents/Projects/brandbrother/other/hu_HU.dic");

			BufferedReader br = new BufferedReader(new InputStreamReader(
					fstream, "UTF-8"));
			String strLine;
			//Read File Line By Line
			int slashLine = 0;
			int tabLine = 0;
			int allLine = 0;

			Language lan = languageService.findByLocale("hu_HU");

			List<Dictionary> newWords = new ArrayList<Dictionary>();

			BufferedWriter out = new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream(
									"/Users/znurgl/Documents/Projects/brandbrother/other/tout.txt"),
							"UTF-8"));

			while ((strLine = br.readLine()) != null) {
				allLine++;
				// Print the content on the console
				System.out.println(strLine);

				String delimiter = "/";
				/* given string will be split by the argument delimiter provided. */
				String[] temp = strLine.split(delimiter);

				if (temp.length > 1) {
					Dictionary dic = new Dictionary();
					dic.setValue(temp[0]);
					dic.setLanguage(lan);
					newWords.add(dic);
					slashLine++;
					continue;
				}

				delimiter = "\\t";
				/* given string will be split by the argument delimiter provided. */
				temp = strLine.split(delimiter);

				if (temp.length > 1) {
					Dictionary dic = new Dictionary();
					dic.setValue(temp[0]);
					dic.setLanguage(lan);
					newWords.add(dic);
					tabLine++;
					continue;
				}

				Dictionary dic = new Dictionary();
				dic.setValue(strLine);
				dic.setLanguage(lan);
				newWords.add(dic);

			}
			//Close the input stream
			//in.close();
			System.out.println(allLine);
			System.out.println(slashLine);
			System.out.println(tabLine);

			int id = 1000000;

			for (Dictionary d : newWords) {
				//System.out.println(d.getValue());
				//dictionaryService.create(d);
				out.write("insert into Dictionary values (" + id++ + ", '"
						+ d.getValue() + "', 1);\n");
			}

		} catch (Exception e) {//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

	}

	@RequestMapping(value = "/testdata", method = RequestMethod.GET)
	public void TestData() {
		Role userRole = new Role();
		userRole.setValue("USER");
		List<Role> roles = new ArrayList<Role>();
		roles.add(userRole);
		User user = new User();
		user.setLogin("bakosgergo@gmail.com");
		user.setPassword("123456");
		user.setRoles(roles);

		Company company = new Company();
		company.setAddress("Baross u. 100");
		company.setName("Proba ceg");
		company.setRegisterNumber("123518387123");
		company.setTaxNumber("12931298371");

		companyService.create(company);
		userService.create(userRole);
		user.setCompany(company);
		userService.create(user);

		List<Keyword> keyList = new ArrayList<Keyword>();
		Keyword k = new Keyword();
		k.setValue("oscar");
		keywordService.create(k);
		keyList.add(k);
		k = new Keyword();
		k.setValue("orbán");
		keywordService.create(k);
		keyList.add(k);
		k = new Keyword();
		k.setValue("tavasz");
		keywordService.create(k);
		keyList.add(k);

		Campaign c = new Campaign();
		c.setCompany(company);
		c.setName("Index fooldalrol kulcsszavak keresese");
		c.setKeywords(keyList);
		campaignService.create(c);

	}

}

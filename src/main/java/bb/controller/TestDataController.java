package bb.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import bb.domain.Campaign;
import bb.domain.Company;
import bb.domain.Keyword;
import bb.domain.Role;
import bb.domain.User;
import bb.service.CampaignService;
import bb.service.CompanyService;
import bb.service.KeywordService;
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
		k.setValue("orb�n");
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

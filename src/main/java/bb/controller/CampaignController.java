package bb.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bb.domain.Campaign;
import bb.domain.Keyword;
import bb.domain.User;
import bb.service.CampaignService;
import bb.service.KeywordService;
import bb.service.UserService;

@Controller
@RequestMapping("/campaigns")
public class CampaignController {

	@Autowired
	UserService userService;

	@Autowired
	KeywordService keywordService;

	@Autowired
	CampaignService campaignService;

	private final static Log log = LogFactory.getLog(DashboardController.class);

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String login(Model model) {

		return "/campaign/index";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String showCampaign(Model model) {

		User user = userService.getUserFromSession();
		model.addAttribute("user", user);

		return "/campaign/create";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String createPage(Model model) {

		User user = userService.getUserFromSession();
		model.addAttribute("user", user);

		return "/campaign/create";
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public String create(@RequestParam(value = "name") String name,
			@RequestParam(value = "dateFrom") String dateFrom,
			@RequestParam(value = "keywords") String keywords, Model model) {

		try {
			User user = userService.getUserFromSession();
			String[] keyArray = keywords.split(",");
			List<Keyword> keywordForCampaign = new ArrayList<Keyword>();
			for (String k : keyArray) {
				Keyword keyword = new Keyword();
				keyword.setValue(k);
				keywordService.create(keyword);
				keywordForCampaign.add(keyword);
			}
			Campaign campaign = new Campaign();
			campaign.setName(name);
			campaign.setKeywords(keywordForCampaign);
			campaign.setCompany(user.getCompany());
			campaignService.create(campaign);

		} catch (Exception e) {
			log.error("Hiba a kampany letrehozasakor: " + e.getMessage());
			e.printStackTrace();
		}

		User user = userService.getUserFromSession();
		model.addAttribute("user", user);

		return "/campaign/create";
	}

}

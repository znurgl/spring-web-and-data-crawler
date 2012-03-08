package bb.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import bb.domain.User;
import bb.repository.CampaignRepository;
import bb.repository.DataRepository;
import bb.repository.KeywordRepository;
import bb.service.UserService;

@Controller
@RequestMapping("/report")
public class ReportController {

	@Autowired
	UserService userService;

	@Autowired
	DataRepository dataRepository;

	@Autowired
	CampaignRepository campaignRepository;

	@Autowired
	KeywordRepository keywordRepository;

	private final static Log log = LogFactory.getLog(HitsController.class);

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String frontPage(Model model) {

		User user = userService.getUserFromSession();

		return "/report/list";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String showCampaign(Model model) {

		User user = userService.getUserFromSession();
		model.addAttribute("user", user);

		return "/report/download";
	}

}

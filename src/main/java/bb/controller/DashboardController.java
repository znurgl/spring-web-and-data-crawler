	package bb.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import bb.service.CampaignService;
import bb.service.KeywordService;
import bb.service.UserService;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	KeywordService keywordService;
	
	@Autowired
	CampaignService campaignService;	

	private final static Log logger = LogFactory.getLog(DashboardController.class);
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String login(Model model) {
		
		return "/dashboard/index";
	}

}

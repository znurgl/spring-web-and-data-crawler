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
import bb.domain.Data;
import bb.domain.User;
import bb.repository.CampaignRepository;
import bb.repository.DataRepository;
import bb.repository.KeywordRepository;
import bb.service.HitsFilterService;
import bb.service.UserService;

@Controller
@RequestMapping("/hits")
public class HitsController {

	@Autowired
	UserService userService;

	@Autowired
	DataRepository dataRepository;

	@Autowired
	CampaignRepository campaignRepository;

	@Autowired
	KeywordRepository keywordRepository;
	
	@Autowired
	HitsFilterService hfService;

	private final static Log log = LogFactory.getLog(HitsController.class);
	
	@RequestMapping(value = "ajax/dataFilter", method = RequestMethod.POST)
	public String ajaxDataFilter(Model model, @RequestParam("campaign") Long campaignId) {
		
		Campaign campaign = campaignRepository.read(campaignId);
		
		model.addAttribute("dataList", getDataByCampaign(campaign));
		
		return "/hits/dataList";
		
	}
	
	private List<Data> getDataByCampaign(Campaign campaign){
		
		List<Data> dataList = new ArrayList<Data>();
		List<Data> innerData = dataRepository.allByCampaign(campaign);
		if (innerData != null) {
			dataList.addAll(innerData);
		}
		
		for(Data d :dataList){
			String body = d.getBody();
			if( body.length()>200 ){
				body = body.substring(0, 200) + "...";
			}
			d.setBody(body);
		}
		
		return dataList;
		
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String frontPage(Model model) {

		User user = userService.getUserFromSession();

		log.debug(user.getCompany().getId());

		List<Campaign> campaigns = campaignRepository.findAllByCompany(user
				.getCompany());
		
		model.addAttribute("dataList", getDataByCampaign(campaigns.get(0)));
		model.addAttribute("campaigns", campaigns);
		
		hfService.getFilterFromSession();

		return "/hits/list";
	}

}

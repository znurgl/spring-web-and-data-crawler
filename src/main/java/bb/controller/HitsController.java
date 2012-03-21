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
import bb.domain.HitsFilter;
import bb.domain.User;
import bb.repository.CampaignRepository;
import bb.repository.DataRepository;
import bb.repository.KeywordRepository;
import bb.service.HitsFilterService;
import bb.service.SessionService;
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

	@Autowired
	SessionService sessionService;

	private final static Log log = LogFactory.getLog(HitsController.class);

	@RequestMapping(value = "ajax/dataFilter", method = RequestMethod.POST)
	public String ajaxDataFilter(Model model,
			@RequestParam("campaign") Long campaignId) {
		
		HitsFilter hf = getFilter();

		Campaign campaign = campaignRepository.read(campaignId);

		if (hf == null) {
			hf = new HitsFilter();
		}
		
		hf.setCampaign(campaign);
		sessionService.setFilterFromSession("dataFilter", hf);

		

		model.addAttribute("dataList", getDataByCampaign(hf.getCampaign()));

		return "/hits/dataList";

	}

	private List<Data> getDataByCampaign(Campaign campaign) {

		List<Data> dataList = new ArrayList<Data>();
		List<Data> innerData = dataRepository.allByCampaign(campaign);
		if (innerData != null) {
			dataList.addAll(innerData);
		}

		for (Data d : dataList) {
			String body = d.getBody();
			if (body.length() > 200) {
				body = body.substring(0, 200) + "...";
			}
			d.setBody(body);
		}

		return dataList;

	}

	private HitsFilter getFilter() {

		HitsFilter hf = null;

		try {
			hf = (HitsFilter) sessionService.getFilterFromSession("dataFilter");
		} catch (Exception e) {
			log.error("HitsFilter nincs a sessionben.");
		}
		
		if (hf == null) {
			hf = new HitsFilter();
		}

		return hf;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String frontPage(Model model) {

		User user = userService.getUserFromSession();

		log.debug(user.getCompany().getId());

		HitsFilter hf = getFilter();

		List<Campaign> campaigns = campaignRepository.findAllByCompany(user
				.getCompany());
		
		if (hf.getCampaign() == null) {
			hf.setCampaign(campaigns.get(0));
			sessionService.setFilterFromSession("dataFilter", hf);
		} 

		model.addAttribute("campaigns", campaigns);
		model.addAttribute("active_campaign_id", hf.getCampaign().getId());
		model.addAttribute("filter_keywords", keywordRepository.findByCampaign(hf.getCampaign()));

		return "/hits/list";
	}

}

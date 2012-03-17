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

import bb.domain.Campaign;
import bb.domain.Data;
import bb.domain.User;
import bb.repository.CampaignRepository;
import bb.repository.DataRepository;
import bb.repository.KeywordRepository;
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

	private final static Log log = LogFactory.getLog(HitsController.class);

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String frontPage(Model model) {

		User user = userService.getUserFromSession();

		log.debug(user.getCompany().getId());

		List<Campaign> campaigns = campaignRepository.findAllByCompany(user
				.getCompany());
		List<Data> dataList = new ArrayList<Data>();
		// for (Campaign cam : campaigns.get(0)) {

		for (Campaign c : campaigns) {
			List<Data> innerData = dataRepository.allByCampaign(c);
			if (innerData != null) {
				dataList.addAll(innerData);
			}
		}
		
		for(Data d :dataList){
			String body = d.getBody();
			if( body.length()>200 ){
				body = body.substring(0, 200) + "...";
			}
			d.setBody(body);
		}
		
		model.addAttribute("dataList", dataList);

		return "/hits/list";
	}

}

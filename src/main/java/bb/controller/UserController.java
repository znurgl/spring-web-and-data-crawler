package bb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String frontPage(Model model) {

		//List<User> users = userRepository.findAll();

		//System.out.println("users: " + users);
		
		return "/index";
	}

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public String userPage(Model model, @PathVariable String id) {

		return "/index";
	}

}

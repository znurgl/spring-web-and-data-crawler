package bb.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import bb.domain.Role;
import bb.domain.User;

public class TestData {

	@Autowired
	UserService userService;

	public TestData() {
		Role userRole = new Role();
		userRole.setValue("USER");
		List<Role> roles = new ArrayList<Role>();
		roles.add(userRole);
		User user = new User();
		user.setLogin("bakosgergo@gmail.com");
		user.setPassword("123456");
		user.setRoles(roles);
		System.out.println(userService);
		userService.create(user);
	}

}

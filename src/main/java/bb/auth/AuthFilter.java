package bb.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import bb.domain.Role;
import bb.domain.User;
import bb.repository.RoleRepository;
import bb.repository.UserRepository;
import bb.service.UserService;


/**
 * Servlet Filter implementation class AuthFilter
 */
public class AuthFilter implements Filter {

		

    /**
     * Default constructor. 
     */
    public AuthFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		/*
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		HttpSession session = httpRequest.getSession(true);
		
		User user = (User) session.getAttribute("user");

		if( user == null ){
			log.debug("User nincs bejelentkezve");
			//log.debug(userService.getUserFromSession());
		}
		
		log.debug("A filter mukodik, URL: " + httpRequest.getRequestURI());
		*/
		/*
		User user = userRepository.findByLogin("user1");
		
		//if( user.getRoles() == null ){
			Role role = roleRepository.findByValue("USER");
			List<Role> roles = new ArrayList<Role>();
			roles.add(role);
			user.setRoles( roles );
			userRepository.update(user);
		//}
		*/
		
		
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}

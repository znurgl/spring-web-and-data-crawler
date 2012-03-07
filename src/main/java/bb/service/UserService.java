package bb.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import bb.domain.Role;
import bb.domain.User;
import bb.repository.UserRepository;

public class UserService implements BrandbrotherUserDetailsService {

	private final static Log log = LogFactory.getLog(UserService.class);

	@Autowired
	UserRepository userRepository;

	@Override
	public User getUserFromSession() {
		User user = null;

		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		Object principal = authentication.getPrincipal();
		if (principal instanceof UserDetails) {
			BrandbrotherUserDetails userDetails = (BrandbrotherUserDetails) principal;
			user = userDetails.getUser();
		}

		return user;
	}

	void setUserInSession(User user) {
		SecurityContext context = SecurityContextHolder.getContext();
		BrandbrotherUserDetails userDetails = new BrandbrotherUserDetails(user);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				userDetails, user.getPassword(), userDetails.getAuthorities());
		context.setAuthentication(authentication);

	}

	@Override
	public BrandbrotherUserDetails loadUserByUsername(String login)
			throws UsernameNotFoundException, DataAccessException {
		User user = userRepository.findByLogin(login);
		log.debug("Felhasznalo bejelentkezes ellenorzese: " + login
				+ ", user: " + user);
		return new BrandbrotherUserDetails(user);
	}

	public void create(User user) {
		userRepository.create(user);
	}

	public void create(Role role) {
		userRepository.create(role);
	}

}

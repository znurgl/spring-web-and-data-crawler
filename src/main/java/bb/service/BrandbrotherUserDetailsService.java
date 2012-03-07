package bb.service;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import bb.domain.User;

public interface BrandbrotherUserDetailsService extends UserDetailsService {
	@Override
	BrandbrotherUserDetails loadUserByUsername(String login)
			throws UsernameNotFoundException, DataAccessException;

	User getUserFromSession();

}

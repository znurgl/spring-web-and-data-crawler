package bb.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

import bb.domain.Role;
import bb.domain.User;

public class BrandbrotherUserDetails implements UserDetails {

	private final User user;

	public BrandbrotherUserDetails(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (Role role : user.getRoles()) {
			authorities.add(new GrantedAuthorityImpl(role.getValue()));
		}

		return authorities;
	}

	@Override
	public String getPassword() {
		String resp = null;
		if (user != null && user.getPassword() != null) {
			resp = user.getPassword();
		}
		return resp;
	}

	@Override
	public String getUsername() {
		return user.getLogin();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public User getUser() {
		return user;
	}
}

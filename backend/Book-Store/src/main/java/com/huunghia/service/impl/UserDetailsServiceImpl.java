package com.huunghia.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.huunghia.entity.User;
import com.huunghia.entity.UserRole;
import com.huunghia.repository.UserRepository;
import com.huunghia.security.CustomUserDetails;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("khong tin thay user");
		}

		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		Set<UserRole> userRoles = user.getUserRoles();

		for (UserRole userRole : userRoles) {
			authorities.add(new SimpleGrantedAuthority(userRole.getRole().getName()));
		}

		CustomUserDetails userDetails = new CustomUserDetails(user.getUsername(), user.getPassword(), authorities);

		return userDetails;

	}

}

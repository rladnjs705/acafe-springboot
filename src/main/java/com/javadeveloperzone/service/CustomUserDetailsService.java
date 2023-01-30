package com.javadeveloperzone.service;

import com.javadeveloperzone.entity.Users;
import com.javadeveloperzone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	private final UserRepository userRepository;

	//메서드가 자동으로 불림.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Users user = userRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("Email: " + username + " not found"));
		return new org.springframework.security.core.userdetails.User(user.getEmail(),
				user.getPassword(), getAuthorities(user));

	}

	private static Collection<? extends GrantedAuthority> getAuthorities(Users user){
		Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList();
		return authorities;
	}
}

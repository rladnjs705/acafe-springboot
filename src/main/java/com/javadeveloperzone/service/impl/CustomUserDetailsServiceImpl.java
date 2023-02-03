package com.javadeveloperzone.service.impl;

import com.javadeveloperzone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsService {
	private final UserRepository userRepository;

	//메서드가 자동으로 불림.
	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

		return Optional
				.ofNullable(userRepository.findByEmail(userEmail).get())
				.orElseThrow(() -> new BadCredentialsException("이메일 주소를 확인해주세요."));
	}
}

package com.javadeveloperzone.config;

import com.javadeveloperzone.config.utils.JwtUtil;
import com.javadeveloperzone.entity.Users;
import com.javadeveloperzone.service.impl.CustomUserDetailsServiceImpl;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public JwtUtil jwtUtil;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        String userEmail = token.getName();
        String userPassword = (String) token.getCredentials();

        Users user = (Users) userDetailsService.loadUserByUsername(userEmail);

        if(passwordEncoder.matches(userPassword, user.getPassword()) == false) {
            throw new BadCredentialsException(user.getUsername() + " 비밀번호를 확인해주세요.");
        }

        //String JwtToken = JwtUtil.createToken(authentication);

        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}

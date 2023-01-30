package com.javadeveloperzone.config;

import com.javadeveloperzone.config.securityHandler.CustomLogoutSuccessHandler;
import com.javadeveloperzone.config.securityHandler.RestAuthenticationFailureHandler;
import com.javadeveloperzone.config.securityHandler.RestAuthenticationSuccessHandler;
import com.javadeveloperzone.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); // BCryptPasswordEncoder 생성
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	http.authorizeHttpRequests().requestMatchers("/", "/static/**", "/favicon.ico").permitAll()
//        	.requestMatchers("/users/**", "/admin/**").hasAuthority(ProjectConstants.USER_ROLE)
//            .requestMatchers("/**").permitAll()
//                .requestMatchers("/user/**").hasAuthority(ProjectConstants.USER_ROLE)
//                .requestMatchers("/admin/**").hasAuthority(ProjectConstants.ADMIN_ROLE)
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/user/loginPage")
            .loginProcessingUrl("/user/login")
            .defaultSuccessUrl("/user/success")
            .failureHandler(authenticationFailureHandler())
            .successHandler(authenticationSuccessHandler())
            .permitAll()
            .and()
            //.rememberMe().key("WmQeZeP5mOtGX5ZnCzVX4U469QgtrsOe")
            //.and()
            .logout()
            .logoutUrl("/user/logout")
            .logoutSuccessHandler(logoutSuccessHandler())
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .and() // 403 예외처리 핸들링
            .exceptionHandling()
            //.authenticationEntryPoint(authenticationHandler())
            .accessDeniedPage("/user/denied")
            .and()
            .csrf().disable();
        
    	http.sessionManagement()
        	.maximumSessions(1)
        	.maxSessionsPreventsLogin(false)
        	.expiredUrl("/");
        
//        http.headers().frameOptions().sameOrigin();
//        http.addFilterBefore(authenticationJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        //어떤 UserDetailsService를 사용하고, 어떤 PasswordEncoder를 사용하는지
        auth.userDetailsService(customUserDetailsService);
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new RestAuthenticationFailureHandler();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new RestAuthenticationSuccessHandler();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }
    
//    @Bean
//    public AuthenticationEntryPoint authenticationHandler() {
//        return new RestAuthenticationEntryPoint();
//    }

}

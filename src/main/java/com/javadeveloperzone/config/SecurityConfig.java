package com.javadeveloperzone.config;

import com.javadeveloperzone.config.securityHandler.CustomLogoutSuccessHandler;
import com.javadeveloperzone.config.securityHandler.RestAuthenticationFailureHandler;
import com.javadeveloperzone.config.securityHandler.RestAuthenticationSuccessHandler;
import com.javadeveloperzone.service.impl.CustomUserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsServiceImpl customUserDetailsService;
//    private final CustomAuthenticationProvider authenticationProvider;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	http.authorizeHttpRequests().requestMatchers("/","/user/**", "/admin/**", "/static/**", "/favicon.ico").permitAll()
//            .requestMatchers("/user/**").hasAuthority(ProjectConstants.USER_ROLE)
//            .requestMatchers("/admin/**").hasAuthority(ProjectConstants.ADMIN_ROLE)
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginProcessingUrl("/user/login")
                .usernameParameter("userEmail").passwordParameter("password")
            //.defaultSuccessUrl("/menu/index")
            .failureHandler(authenticationFailureHandler())
            .successHandler(authenticationSuccessHandler())
            .permitAll()
            .and()
            .logout()
            .logoutUrl("/user/logout")
            .logoutSuccessHandler(logoutSuccessHandler())
            .invalidateHttpSession(true)
            .and() // 403 예외처리 핸들링
            .exceptionHandling()
            .accessDeniedPage("/user/denied")
            .and()
            .csrf().disable();

    	http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.headers().frameOptions().sameOrigin();
//        http.addFilterBefore(authenticationJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

//    @Bean
//    public AuthenticationManager configureAuthenticationManager(AuthenticationManagerBuilder builder) throws Exception {
//
//        builder
//                .inMemoryAuthentication()
//                .withUser("admin@admin.com")
//                .password(passwordEncoder().encode("1234"))
//                .roles(ProjectConstants.ADMIN_ROLE);
//        return builder.authenticationProvider(authenticationProvider).build();
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        ProviderManager authenticationManager = (ProviderManager)authenticationConfiguration.getAuthenticationManager();
        authenticationManager.getProviders().add(customAuthenticationProvider());
        return authenticationManager;
    }
    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider(){
        return new CustomAuthenticationProvider(customUserDetailsService,passwordEncoder());
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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


}

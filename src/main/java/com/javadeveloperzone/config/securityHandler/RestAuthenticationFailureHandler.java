package com.javadeveloperzone.config.securityHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javadeveloperzone.config.exception.ResultCodeType;
import com.javadeveloperzone.model.ResponseVo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

@Component
public class RestAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private static final Logger logger = LoggerFactory.getLogger(RestAuthenticationFailureHandler.class);

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException ex) throws IOException, ServletException {

		logger.debug("RestAuthenticationFailureHandler error : {}", ex);

		String errorMessage = "";

		if(ex instanceof BadCredentialsException) {
			errorMessage = "아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해주세요.";
		} else if (ex instanceof InternalAuthenticationServiceException) {
			errorMessage = "내부 시스템 문제로 로그인 요청을 처리할 수 없습니다. 관리자에게 문의하세요. ";
		} else if (ex instanceof UsernameNotFoundException) {
			errorMessage = "존재하지 않는 계정입니다. 회원가입 후 로그인해주세요.";
		} else if (ex instanceof AuthenticationCredentialsNotFoundException) {
			errorMessage = "인증 요청이 거부되었습니다. 관리자에게 문의하세요.";
		} else {
			errorMessage = "알 수 없는 오류로 로그인 요청을 처리할 수 없습니다. 관리자에게 문의하세요.";
		}

		logger.error("loginFail errorMessage: " + errorMessage);

		errorMessage = URLEncoder.encode(errorMessage, "UTF-8"); /* 한글 인코딩 깨진 문제 방지 */
		setDefaultFailureUrl("/user/loginPage?error=true&exception="+errorMessage);
		super.onAuthenticationFailure(request, response, ex);

	}
}
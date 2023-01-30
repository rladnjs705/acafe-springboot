package com.javadeveloperzone.config.securityHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javadeveloperzone.config.exception.ResultCodeType;
import com.javadeveloperzone.model.ResponseVo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

@Component
public class RestAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private static final Logger logger = LoggerFactory.getLogger(RestAuthenticationFailureHandler.class);

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse httpServletResponse,
			AuthenticationException ex) throws IOException, ServletException {

		logger.debug("RestAuthenticationFailureHandler error : {}", ex);
		
		ResponseVo resp = null;
		int status = 0;
		if(ex instanceof UsernameNotFoundException) {
			resp = new ResponseVo(ResultCodeType.ERROR_USER_NOT_FOUND);
			status = ResultCodeType.ERROR_USER_NOT_FOUND.getStatus().value();
		}
		else if(ex instanceof BadCredentialsException) {
			resp = new ResponseVo(ResultCodeType.ERROR_PWD_NOT_MATCH);
			status = ResultCodeType.ERROR_PWD_NOT_MATCH.getStatus().value();
		}
		else {
			resp = new ResponseVo(ResultCodeType.ERROR_USER_NOT_FOUND);
			status = ResultCodeType.ERROR_USER_NOT_FOUND.getStatus().value();
		}

		httpServletResponse.setStatus(status);
		OutputStream out = httpServletResponse.getOutputStream();
		ObjectMapper mapper = new ObjectMapper();
		mapper.writerWithDefaultPrettyPrinter().writeValue(out, resp);
		out.flush();

		logger.error("loginfail resultCode: " + resp.getResultCode());
	}
}
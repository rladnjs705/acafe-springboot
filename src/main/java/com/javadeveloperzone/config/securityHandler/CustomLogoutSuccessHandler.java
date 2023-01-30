package com.javadeveloperzone.config.securityHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

	private static final Logger logger = LoggerFactory.getLogger(CustomLogoutSuccessHandler.class);

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		if (authentication != null && authentication.getDetails() != null) {
            try {
                 request.getSession().invalidate();
            } catch (Exception e) {
            	logger.error("CustomLogoutSuccessHandler error : {}", e);
            }
        }
        response.setStatus(HttpServletResponse.SC_OK);
        try {
			response.sendRedirect("/");
		} catch (IOException e) {
			logger.error("CustomLogoutSuccessHandler error : {}", e);
		}
	}

}
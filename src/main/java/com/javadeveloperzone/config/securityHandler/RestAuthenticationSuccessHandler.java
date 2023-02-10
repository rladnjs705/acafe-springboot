package com.javadeveloperzone.config.securityHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javadeveloperzone.config.utils.JwtUtil;
import com.javadeveloperzone.model.CustomUserDetails;
import com.javadeveloperzone.model.ResponseVo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Component
public class RestAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private JwtUtil jwtUtil;

	private static final Logger logger = LoggerFactory.getLogger(RestAuthenticationSuccessHandler.class);

//	@Value("${server.servlet.session.timeout}")
//	private int sessionTimeout;
	
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {
		logger.debug("AuthenticationSuccessHandler success"+authentication.getName());
		//HttpSession session = request.getSession(true);
		//session.setAttribute("userEmail", authentication.getName());

		//SecurityContextHolder.clearContext();

		ResponseVo resp = new ResponseVo();
		response.setStatus(HttpStatus.OK.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		OutputStream out = response.getOutputStream();
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> data = new HashMap<>();
		String JwtToken = JwtUtil.createToken(authentication);
		data.put("token", JwtToken);
		data.put("userEmail", authentication.getName());
		resp.setData(data);
		mapper.writerWithDefaultPrettyPrinter().writeValue(out, resp);
		out.flush();

		logger.info("loginsuccess id: " + authentication.getName());
	}
}
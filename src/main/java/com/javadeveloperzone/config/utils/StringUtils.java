package com.javadeveloperzone.config.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.regex.Pattern;

public class StringUtils {

	public static boolean isEmpty(String str) {
		return ((str == null) || (str.trim().isEmpty()));
	}

	/**
	 * 아이디체크
	 * 영문소문자로 시작하고 영문소문자와 숫자만 허용하는 5~20자 이하
	 */
	public static boolean idCheck(String id) {
		String regex = "^[a-z]{1}[a-z0-9]{4,19}$";
		return Pattern.matches(regex, id);
	}

	/**
	 * 이메일 형식체크
	 */
	public static boolean emailCheck(String email) {
		String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
		return Pattern.matches(regex, email);
	}
	
	/**
	 * Client IP를 구한다.
	 * 
	 * @param request
	 *        HttpServletRequest 객체
	 * @return String 실제 사용자 IP
	 * 
	 */
	public static String getClientIp() {

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

		String ip = request.getHeader("X-FORWARDED-FOR");

		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("x-real-ip");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("x-original-forwarded-for");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("HTTP_X_FORWARDED");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("HTTP_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("HTTP_FORWARDED");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("HTTP_VIA");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("REMOTE_ADDR");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getRemoteAddr();
		}

		// 여러 프로시들을 거치며, 최초 클라이언트 ip를 제외한 각 프록시의 ip 주소들이 차례로 열거되는 현상 제거.
		if (ip.contains(",")) {
			ip = ip.substring(0, ip.lastIndexOf(","));
		}

		if ("0:0:0:0:0:0:0:1".equals(ip)) {
			ip = "127.0.0.1";
		}

		return ip;
	}
}

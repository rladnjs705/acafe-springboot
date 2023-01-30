package com.javadeveloperzone.config;

import java.lang.annotation.*;

public class AnnotationConfig {

	@Target({ElementType.METHOD,ElementType.ANNOTATION_TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface LoginRequired {

	}

	/**
	 * Admin권한이 있는 유저만 접근 가능하다.
	 * @author knits
	 *
	 */
	@Target({ElementType.METHOD,ElementType.ANNOTATION_TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@LoginRequired
	public @interface AdminOnly {

	}

	/**
	 * 로깅 제외
	 * 
	 * @author ksh
	 */
	@Target({ElementType.METHOD})
	@Retention(RetentionPolicy.RUNTIME)
	public @interface NoLogging {
		
	}
}

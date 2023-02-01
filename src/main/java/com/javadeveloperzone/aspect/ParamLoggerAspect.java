package com.javadeveloperzone.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
@Slf4j
public class ParamLoggerAspect {
	private final HttpServletRequest request;

	public ParamLoggerAspect(HttpServletRequest request) {
		this.request = request;
	}

	private JSONObject getParams() {
		JSONObject jsonObject = new JSONObject();
		Enumeration<String> params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String param = params.nextElement();
			String replaceParam = param.replaceAll("\\.", "-");
			jsonObject.put(replaceParam, request.getParameter(param));
		}
		return jsonObject;
	}

	@Pointcut("execution(* com.*.service.controller.*Controller.*(..))")
	public void loggerPointCut() {
	}

	@Around("loggerPointCut()")
	public Object methodLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Object result = proceedingJoinPoint.proceed();
		String controllerName = proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName();
		String methodName = proceedingJoinPoint.getSignature().getName();

		Map<String, Object> params = new HashMap<>();

		try {
			params.put("requestUrl", request.getRequestURI());
			params.put("httpMethod", request.getMethod());
			params.put("controller", controllerName);
			params.put("method", methodName);
			params.put("params", getParams());
			params.put("time", LocalDateTime.now());
		} catch (Exception e) {
			log.error("LoggerAspect error", e);
		}
		log.info("requestLog : {}", params);

		return result;
	}

}
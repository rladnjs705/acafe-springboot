//package com.javadeveloperzone.aspect;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.Part;
//import org.apache.http.entity.ContentType;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.json.simple.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Component
//@Aspect
//public class ParamLoggerAspect {
//	private static final Logger logger = LoggerFactory.getLogger(ParamLoggerAspect.class);
//
//	@Pointcut("execution(* com.example.acafekiosk..*Controller.*(..))"
//			+ "&& !@annotation(com.example.acafekiosk.config.AnnotationConfig.NoLogging)") // 이런 패턴이 실행될 경우 수행
//	public void loggerPointCut() {
//	}
//
//	@Around("loggerPointCut()")
//	public Object methodLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//		try {
//			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
//
//			String controllerName = proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName();
//			String methodName = proceedingJoinPoint.getSignature().getName();
//			String uuid = UUID.randomUUID().toString();
//
//			LinkedHashMap<String, Object> params = new LinkedHashMap<>();
//
//			Date date = Calendar.getInstance().getTime();
//
//			try {
//				params.put("request_uri", request.getRequestURI());
//				params.put("http_method", request.getMethod());
//				params.put("params", getParams(request));
//
//				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				String strDate = dateFormat.format(date);
//
//				params.put("log_time", strDate);
//				params.put("controller", controllerName);
//				params.put("method", methodName);
//				params.put("uuid", uuid);
//				if (request.getContentType() != null && request.getContentType().contains(
//	                    ContentType.MULTIPART_FORM_DATA.getMimeType())) {
//					if(null != request.getParts()) {
//						List<Part> fileParts = request.getParts().stream().filter(
//								part -> null != part.getSubmittedFileName() && part.getSize() > 0)
//								.collect(Collectors.toList());
//						for(int i=0; i < fileParts.size(); i++) {
//							params.put("multipart_name_" + fileParts.get(i).getName() + "_" + i, fileParts.get(i).getSubmittedFileName());
//						}
//					}
//	            }
//			} catch (Exception e) {
//				logger.error("ParamLoggerAspect error : {}", e);
//			}
//			logger.info("params : {}", params); // param에 담긴 정보들을 한번에 로깅한다.
//
//			Object result = proceedingJoinPoint.proceed();
//			Date end = Calendar.getInstance().getTime();
//
//			if(result instanceof ResponseEntity) {
//				ResponseEntity<Object> resp = (ResponseEntity<Object>) result;
//				LinkedHashMap<String, Object> respMap = new LinkedHashMap<String, Object>();
//				respMap.put("statusCode", resp.getStatusCode());
//				if(null != resp.getBody()) {
//					//data까지 로깅필요시 ResponseVo의 toString주석을 해제한다.
//					respMap.put("body", resp.getBody().toString());
//				}
//				respMap.put("uuid", uuid);
//				respMap.put("apiTime",  end.getTime() - date.getTime());
//				logger.info("response : {}", respMap);
//			}
//
////			commonService.createStat(params);
//			return result;
//
//		} catch (Throwable throwable) {
//			throw throwable;
//		}
//	}
//
//	/**
//	 * request 에 담긴 정보를 JSONObject 형태로 반환한다.
//	 * @param request
//	 * @return
//	 */
//	private static JSONObject getParams(HttpServletRequest request) {
//		JSONObject jsonObject = new JSONObject();
//		Enumeration<String> params = request.getParameterNames();
//		while (params.hasMoreElements()) {
//			String param = params.nextElement();
//			String replaceParam = param.replaceAll("\\.", "-");
//			jsonObject.put(replaceParam, request.getParameter(param));
//		}
//		return jsonObject;
//	}
//
//}
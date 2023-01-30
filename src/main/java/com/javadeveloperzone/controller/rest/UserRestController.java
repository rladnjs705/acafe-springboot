package com.javadeveloperzone.controller.rest;//package com.example.acafekiosk.controller.rest;
//
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.RestController;
//
//
//@RestController
//@RequiredArgsConstructor
//public class UserRestController {
//
//	private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);
//
////	@Value("${server.servlet.session.timeout}")
////	private int sessionTimeout;
//	//private final UserService userService;
//
//	/**
//	 * 로그인 - 1차인증
//	 *
//	 * @param authentication
//	 * @param param
//	 * @return
//	 * @throws Exception
//	 */
////	@AnnotationConfig.NoLogging
////	@RequestMapping(path = "/user/check", method = RequestMethod.POST)
////	public ResponseEntity<ResponseVo> getEmail(Authentication authentication, @RequestParam HashMap<String, Object> param, HttpServletRequest request) throws Exception {
////
////		AssertForm.notNullEx(param, "userid", null);
////		AssertForm.notNullEx(param, "password", null);
////
////		param.put("password", Sha512Utils.encrypt(param.get("password").toString()));
////		Map<String, Object> respMap = userService.selectUser(param);
////		if (null != respMap) {
////			//2차인증 메일전송
////	        //String authNo = emailUtils.randomAuthNo();
////	        ///respMap.put("authNo", authNo);
////	        //userService.updateAuthNo(respMap);
//////	        emailUtils.sendSimpleMessage(respMap.get("user_email").toString(),"[유캔두CMS] 인증번호를 발송해드립니다.","인증 번호는 ["+authNo+"] 입니다.");
////
////			return ResponseUtils.response(respMap);
////
////		}
////		else {
////			//userService.updateFalsePwd(param);
////			return ResponseUtils.response(ResultCodeType.ERROR_PWD_NOT_MATCH, null);
////		}
////	}
///*
//	*//**
//	 * 정보설정 - 로그인 정보 조회
//	 *
//	 * @param authentication
//	 * @param param
//	 * @return
//	 * @throws Exception
//	 *//*
//	@RequestMapping(path = "/user/getUserInfo", method = RequestMethod.GET)
//	public ResponseEntity<ResponseVo> getUserInfo(Authentication authentication, @RequestParam HashMap<String, Object> param) throws Exception {
//
//		Map<String, Object> respMap = new HashMap<String, Object>();
//		param.put("userId", authentication.getName());
//		respMap = userService.getUserInfo(param);
//		return ResponseUtils.response(respMap);
//	}
//
//	*//**
//	 * 정보설정 - 비빌번호 변경
//	 *
//	 * @param authentication
//	 * @param param
//	 * @return
//	 * @throws Exception
//	 *//*
//	@NoLogging
//	@RequestMapping(path = "/user/updatePwd", method = RequestMethod.POST)
//	public ResponseEntity<ResponseVo> updatePwd(Authentication authentication, @RequestParam HashMap<String, Object> param) throws Exception {
//
//		Map<String, Object> reqMap = new HashMap<String, Object>();
//		reqMap.put("userid", authentication.getName());
//		reqMap.put("password", Sha512Utils.encrypt(param.get("password").toString()));
//		userService.updatePwd(reqMap);
//
//		return ResponseUtils.response(ResultCodeType.SUCCESS, null);
//	}
//
//	*//**
//	 * 로그인 비밀번호 확인
//	 *
//	 * @param authentication
//	 * @param param
//	 * @return
//	 * @throws Exception
//	 *//*
//	@RequestMapping(path = "/user/validatePwd", method = RequestMethod.POST)
//	public ResponseEntity<ResponseVo> validatePwd(Authentication authentication, @RequestParam HashMap<String, Object> param) throws Exception {
//
//		Map<String, Object> respMap = new HashMap<String, Object>();
//		param.put("userName", authentication.getName());
//		respMap = userService.selectUser(param);
//
//		if (null == respMap || null == respMap.get("userPw") || !Sha512Utils.encrypt(param.get("password").toString()).equals(respMap.get("userPw"))) {
//			return ResponseUtils.response(ResultCodeType.ERROR_AUTH, null);
//		}
//		return ResponseUtils.response(ResultCodeType.SUCCESS, null);
//	}*/
//
//}

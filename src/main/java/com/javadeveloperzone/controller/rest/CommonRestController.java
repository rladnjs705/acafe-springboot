package com.javadeveloperzone.controller.rest;//package com.example.acafekiosk.controller.rest;
//
//import com.youcandoo.cms.config.utils.ResponseUtils;
//import com.youcandoo.cms.config.view.ExcelView;
//import com.youcandoo.cms.model.ResponseVo;
//import com.youcandoo.cms.service.UserService;
//import org.json.simple.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.RequestEntity;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.net.URI;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//public class CommonRestController {
//
//	@Autowired
//	private UserService userService;
//
//	@Autowired
//	private RestTemplate restTemplate;
//
//	@Autowired
//	private ExcelView excelView;
//
//    @RequestMapping(path = "/test", method = RequestMethod.POST)
//    public ResponseEntity<ResponseVo> test(Authentication authentication, @RequestParam Map<String, Object> param) throws Exception {
//
//    	Map<String,Object> respMap = new HashMap<String, Object>();
//    	respMap.put("test", "post");
//    	return ResponseUtils.response(respMap);
//    }
//
//    @RequestMapping(path = "/test", method = RequestMethod.GET)
//    public ResponseEntity<ResponseVo> testGet(Authentication authentication, @RequestParam Map<String, Object> param) throws Exception {
//
//    	Map<String,Object> respMap = new HashMap<String, Object>();
//    	respMap.put("test", "post");
//
//        return ResponseUtils.response(respMap);
//    }
//
//    @RequestMapping(path = "/upload", method = RequestMethod.POST)
//    public ResponseEntity<ResponseVo> testUpload(Authentication authentication, @RequestPart List<MultipartFile> files,
//    		@RequestParam Map<String, Object> param) throws Exception {
//
//    	Map<String,Object> respMap = new HashMap<String, Object>();
//    	respMap.put("test", "post");
//
//        return ResponseUtils.response(respMap);
//    }
//
//    @RequestMapping(path = "/testexcel", method = RequestMethod.GET)
//    public ModelAndView testXls(Authentication authentication, @RequestParam Map<String, Object> param) throws Exception {
//    	ModelAndView mav = new ModelAndView(excelView);
//
//    	List<String> header = new ArrayList<String>();
//		header.add("번호");
//		header.add("ID");
//		header.add("NAME");
//
//		mav.addObject("excelType", ExcelView.TYPE_TEST);
//		mav.addObject("excelHeader", header);
//		mav.addObject("excelFileName", "adminList");
//		mav.addObject("excelList", new ArrayList<String>());
//
//        return mav;
//    }
//
//    @RequestMapping(path = "/rest", method = RequestMethod.GET)
//    public ResponseEntity<ResponseVo> testRest(Authentication authentication, @RequestParam Map<String, Object> param) throws Exception {
//
//    	Map<String,Object> respMap = new HashMap<String, Object>();
//    	respMap.put("test", "rest");
//
//    	param.put("aaaa", "bbbb");
//    	RequestEntity<JSONObject> req = new RequestEntity<JSONObject>(new JSONObject(param),
//    			HttpMethod.POST, new URI("http://localhost:8080/test"));
//    	ResponseEntity<JSONObject> resp = restTemplate.exchange(req, JSONObject.class);
//
//        return ResponseUtils.response(respMap);
//    }
//
//    @RequestMapping(path = "/async", method = RequestMethod.GET)
//    public ResponseEntity<ResponseVo> testAsync(Authentication authentication, @RequestParam Map<String, Object> param) throws Exception {
//
//    	Map<String,Object> respMap = new HashMap<String, Object>();
//    	respMap.put("test", "async");
//
//    	userService.asyncTest();
//        return ResponseUtils.response(respMap);
//    }
//}

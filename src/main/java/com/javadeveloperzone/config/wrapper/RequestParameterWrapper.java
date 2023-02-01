package com.javadeveloperzone.config.wrapper;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class RequestParameterWrapper extends HttpServletRequestWrapper {

    private Charset encoding;
    private byte[] rawData;
    private Map<String, String[]> params = new HashMap<>();

    public RequestParameterWrapper(HttpServletRequest request) {
        super(request);
        //param or multipart xss 필터링
        //this.params.putAll(xssChangeMap(request.getParameterMap()));
        this.params.putAll(request.getParameterMap());

        String charEncoding = request.getCharacterEncoding();
        this.encoding = StringUtils.isBlank(charEncoding) ? StandardCharsets.UTF_8 : Charset.forName(charEncoding);

        try {
        	if (request.getContentType() != null && request.getContentType().contains(
                    ContentType.MULTIPART_FORM_DATA.getMimeType())) {
                return;
            }

            InputStream is = request.getInputStream();
            this.rawData = IOUtils.toByteArray(is);

            //requestbody xss 필터링
            String bodyStr = new String(rawData, this.encoding);
            bodyStr = xssChange(bodyStr);

            this.rawData = bodyStr.getBytes(this.encoding);

            String collect = this.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

            if (StringUtils.isEmpty(collect)) {
                return;
            }

            JSONParser jsonParser = new JSONParser();
            Object parse = jsonParser.parse(collect);
            if (parse instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) jsonParser.parse(collect);
                setParameter("requestBody", jsonArray.toJSONString());
            } else {
                JSONObject jsonObject = (JSONObject) jsonParser.parse(collect);
                Iterator<?> iterator = jsonObject.keySet().iterator();
                while (iterator.hasNext()) {
                    String key = (String) iterator.next();
                    setParameter(key, jsonObject.get(key).toString().replace("\"", "\\\""));
                }
            }
        } catch (Exception e) {
            log.error("RequestParameterLoggingWrapper init error", e);
        }
    }

    @Override
    public String getParameter(String name) {
        String[] paramArray = getParameterValues(name);
        if (paramArray != null && paramArray.length > 0) {
            return paramArray[0];
        } else {
            return null;
        }
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return Collections.unmodifiableMap(params);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(params.keySet());
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] result = null;
        String[] dummyParamValue = params.get(name);

        if (dummyParamValue != null) {
            result = new String[dummyParamValue.length];
            System.arraycopy(dummyParamValue, 0, result, 0, dummyParamValue.length);
        }
        return result;
    }

    private void setParameter(String name, String value) {
        String[] param = {value};
        setParameter(name, param);
    }

    private void setParameter(String name, String[] values) {
        params.put(name, values);
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.rawData);

        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }

            public int read() {
                return byteArrayInputStream.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(this.getInputStream(), this.encoding));
    }

    private Map<String, String[]> xssChangeMap(Map<String, String[]> orgMap) {
    	if(null != orgMap) {
    		Iterator<String> keys = orgMap.keySet().iterator();
    	      while( keys.hasNext() ){
    	          String key = keys.next();
    	          String[] arr = orgMap.get(key);
    	          for(int i=0; i < arr.length; i++) {
    	        	  arr[i] = xssChange(arr[i]);
    	          }
    	      }
    	}

    	return orgMap;
    }

    private String xssChange(String org) {
    	org = org.replaceAll("<", "&lt;");
        org = org.replaceAll(">", "&gt;");
        return org;
    }
}
package com.javadeveloperzone.interceptor;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.nio.charset.Charset;

public class RestTemplateLoggingInterceptor implements ClientHttpRequestInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(RestTemplateLoggingInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        //전송전 로그
    	logger.debug("request url:"+ request.getURI().toString()
    			+ " reqHeader: " +request.getHeaders().toString()
    			+ " reqBody: " + IOUtils.toString(body, "UTF-8"));

    	ClientHttpResponse response = execution.execute(request, body);

    	// 전송후 로그
        logger.debug("response Header" + response.getHeaders().toString()
        		+ " respBody: "+ IOUtils.toString(response.getBody(),Charset.forName("UTF-8")));
        return response;

    }

}

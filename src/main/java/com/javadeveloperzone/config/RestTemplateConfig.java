package com.javadeveloperzone.config;//package com.example.acafekiosk.config;
//
//import com.example.acafekiosk.interceptor.RestTemplateLoggingInterceptor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.client.BufferingClientHttpRequestFactory;
//import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Collections;
//
//@Configuration
//public class RestTemplateConfig {
//    @Bean
//    public RestTemplate restTemplate() throws Exception{
//
//        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
//
//        factory.setConnectTimeout(2000);
//
//        RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(factory));
//        restTemplate.setInterceptors(Collections.singletonList(new RestTemplateLoggingInterceptor()));
//
//        // 로깅필요없으면 return new RestTemplate(factory);
//        return restTemplate;
//
//    }
//
//}
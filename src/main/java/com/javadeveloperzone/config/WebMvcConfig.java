package com.javadeveloperzone.config;


import com.javadeveloperzone.interceptor.RequestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.resource.EncodedResourceResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;


@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**", "/robots.txt", "/favicon.ico" );
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
            .addResourceLocations("classpath:/static/")
            .setCachePeriod(3600)
            .resourceChain(true)
            .addResolver(new EncodedResourceResolver())
            .addResolver(new PathResourceResolver());
        registry.addResourceHandler("/robots.txt")
        	.addResourceLocations("classpath:/static/")
        	.setCachePeriod(3600);
        registry.addResourceHandler("/favicon.ico")
        	.addResourceLocations("classpath:/static/")
        	.setCachePeriod(3600 * 24);
    }
}
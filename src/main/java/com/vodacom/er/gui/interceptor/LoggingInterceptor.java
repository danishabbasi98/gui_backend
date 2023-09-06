package com.vodacom.er.gui.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("Start Request URL: {}", request.getRequestURL());
//        logger.info("Request Body: {}", request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
        // You can log other information like request method, headers, etc.
        return true; // Continue with the request
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // This method is called after the handler method is invoked, but before the view is rendered.
        logger.info("Start Response URL: {}", request.getRequestURL() + ", Status " + response.getStatus());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // This method is called after the view is rendered (if any) and after the response is committed.
        logger.info("End Response : " + response.toString());
//        logger.info("Response Body: {}", response.getContentType());
    }
}


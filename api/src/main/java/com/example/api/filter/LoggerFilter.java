package com.example.api.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.Enumeration;

@Slf4j
@Component
public class LoggerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        // ServletRequest와 ServletResponse는 읽고나면 재사용할 수 없기 때문에 ContentCachingRequestWrapper와 ContentCachingResponseWrapper로 감싸서 사용
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) servletRequest);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) servletResponse);

        // 필터 체인에 요청과 응답을 전달
        filterChain.doFilter(requestWrapper, responseWrapper);

        // Request 정보 (원래는 doFilter 이전에 로그를 찍어야 하지만, ContentCachingRequestWrapper가 요청 본문을 읽을 수 있도록 하기 위해 doFilter 이후에 로그를 찍음)
        StringBuilder headerValues = new StringBuilder();

        requestWrapper.getHeaderNames().asIterator().forEachRemaining(headerKey -> {
            String headerValue = requestWrapper.getHeader(headerKey);
            headerValues.append("[").append(headerKey).append(" : ").append(headerValue).append("] ");
        });

        String requestBody = new String(requestWrapper.getContentAsByteArray());
        String uri = requestWrapper.getRequestURI();
        String method = requestWrapper.getMethod();

        log.info("\n>>>>> uri : {}, method : {} \nheader : {} \nbody : {}", uri, method, headerValues, requestBody);

        // Response 정보
        StringBuilder responseHeaderValues = new StringBuilder();

        responseWrapper.getHeaderNames().forEach(headerKey -> {
            String headerValue = responseWrapper.getHeader(headerKey);
            responseHeaderValues.append("[").append(headerKey).append(" : ").append(headerValue).append("] ");
        });

        String responseBody = new String(responseWrapper.getContentAsByteArray());

        log.info("\n<<<<< uri : {}, method : {} \nheader : {} \nbody : {}", uri, method, responseHeaderValues, responseBody);

        // 응답 본문을 클라이언트로 복사 (없으면 빈 응답이 전송됨)
        responseWrapper.copyBodyToResponse();
    }
}

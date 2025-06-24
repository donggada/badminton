package com.toy.badminton.infrastructure.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

@Component
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 요청과 응답을 캐싱할 수 있는 래퍼로 감싸기
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();

        // 요청 정보 로깅
        logRequest(requestWrapper);

        // 다음 필터 실행 (실제 컨트롤러 호출)
        filterChain.doFilter(requestWrapper, responseWrapper);

        // 응답 정보 로깅
        logResponse(responseWrapper, System.currentTimeMillis() - startTime);

        // 중요: 응답 본문을 복원해야 함 (안 하면 클라이언트가 빈 응답을 받음)
        responseWrapper.copyBodyToResponse();
    }

    private void logRequest(ContentCachingRequestWrapper request) {
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("\n===== REQUEST =====\n");
        logMessage.append("URI: ").append(request.getRequestURI()).append("\n");
        logMessage.append("Method: ").append(request.getMethod()).append("\n");

        // 헤더 로깅
        logMessage.append("Headers:\n");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            logMessage.append("    ").append(headerName).append(": ").append(request.getHeader(headerName)).append("\n");
        }

        // 요청 파라미터 로깅
        logMessage.append("Request Parameters:\n");
        request.getParameterMap().forEach((key, value) -> {
            logMessage.append("    ").append(key).append(": ").append(String.join(", ", value)).append("\n");
        });

        // 요청 본문 로깅 (POST, PUT 등)
        byte[] content = request.getContentAsByteArray();
        if (content.length > 0) {
            logMessage.append("Request Body: ").append(new String(content, StandardCharsets.UTF_8)).append("\n");
        }

        log.info(logMessage.toString());
    }

    private void logResponse(ContentCachingResponseWrapper response, long timeElapsed) {
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("\n===== RESPONSE =====\n");
        logMessage.append("Status: ").append(response.getStatus()).append("\n");
        logMessage.append("Time Elapsed: ").append(timeElapsed).append("ms\n");

        // 응답 헤더 로깅
        logMessage.append("Headers:\n");
        response.getHeaderNames().forEach(headerName -> {
            logMessage.append("    ").append(headerName).append(": ").append(response.getHeader(headerName)).append("\n");
        });

        // 응답 본문 로깅
        byte[] content = response.getContentAsByteArray();
        if (content.length > 0) {
            logMessage.append("Response Body: ").append(new String(content, StandardCharsets.UTF_8)).append("\n");
        }

        log.info(logMessage.toString());
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // 특정 경로는 로깅하지 않을 수 있음 (예: 정적 리소스, 헬스 체크 등)
        String path = request.getRequestURI();
        return path.contains("/actuator") || path.contains("/static");
    }
}


package ksd.sto.ndm.configs;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
/*
 * MDC.put()을 활용하여 요청별 traceId를 로깅에 포함
 * 요청이 끝난 후 MDC.clear()로 정리
 * Logback 설정을 통해 traceId를 포함한 로그 패턴을 지정
 * 
 * [123e4567-e89b-12d3-a456-426614174000] INFO  TestController - 테스트 요청이 들어왔습니다.
 */
@Component
public class MdcFilter implements Filter {

    private static final String TRACE_ID = "traceId";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            String traceId = UUID.randomUUID().toString();
            MDC.put(TRACE_ID, traceId);

            HttpServletRequest httpRequest = (HttpServletRequest) request;
            System.out
                .println("Request: " + httpRequest.getRequestURI() + ", Trace ID: " + traceId);

            chain.doFilter(httpRequest, response);
        } finally {
            MDC.clear();
        }

    }

}

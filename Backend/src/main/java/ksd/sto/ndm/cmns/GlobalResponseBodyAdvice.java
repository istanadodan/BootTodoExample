package ksd.sto.ndm.cmns;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType,
            Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request, ServerHttpResponse response) {
        // 응답 수정 로직
        log.info("Request: {}", body.getClass().toString());

        if (body instanceof ApiResponse || request.getURI().getPath().contains("/v3/api-docs")
                || request.getURI().getPath().contains("/actuator")) {
            //오류, 스웨거, monitoring
            return body;

        } else if (body instanceof String) {
            log.info("String Request: {}", body.toString());
            // ObjectMapper를 사용하여 직접 JSON 문자열로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(ApiResponse.builder().data(body).build());
            } catch (JsonProcessingException e) {
                return "";
            }

        } 
//        else if (body instanceof LinkedHashMap) {
//            // 인증처리중 오류
//            @SuppressWarnings("unchecked")
//            LinkedHashMap<String, String> errMap = (LinkedHashMap<String, String>) body;
//            if (errMap.containsKey("_links")) { return body; }
//            
//            ApiResponse.Error error = new ApiResponse.Error();
//            error.setCode("500");
//            error.setMessage(errMap.get("message"));
//            error.setType("security");
//            return ApiResponse
//                .builder()
//                .error(error);
//        }

        return ApiResponse.<Object>builder().data(body).build();
    }
}

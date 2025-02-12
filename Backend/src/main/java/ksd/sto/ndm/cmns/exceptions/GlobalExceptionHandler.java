package ksd.sto.ndm.cmns.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import jakarta.servlet.http.HttpServletRequest;
import ksd.sto.ndm.cmns.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    // BizException 처리
    @ExceptionHandler(BizException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Object> handleBizException(BizException ex) {
        String message = "Business error: " + ex.getMessage();
        return createResponse("40401001", message, "bizType");
    }

    // 403 Forbidden 에러 처리
    @ExceptionHandler(Unauthorized.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> UnauthorizedException(AccessDeniedException ex) {
        return new ResponseEntity<>("Unauthorized: " + ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        return new ResponseEntity<>("Access is denied: " + ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    // 405 Method Not Allowed 에러 처리
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<String> handleMethodNotAllowedException(
            HttpRequestMethodNotSupportedException ex) {
        return new ResponseEntity<>("Method not allowed: " + ex.getMessage(),
                HttpStatus.METHOD_NOT_ALLOWED);
    }
    // No Resource Found 에러 처리
//    @ExceptionHandler(NoResourceFoundException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public Object handleNoResourceFoundException(NoResourceFoundException ex,
//            HttpServletRequest request) throws Exception {
//        ModelAndView mView = new ModelAndView("forward:/login");
        // return mView;
//        if(request.getRequestURL().toString().contains("/actuator") == true) {
//            return "index";
//        }
//        return "/error.html";
//        return createResponse("40401001", ex.getMessage(), "system error");
//    }

    // 일반 Exception 처리
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ApiResponse<Object> handleGenericException(Exception ex) {
//        // ex.printStackTrace();
//        var message = "An unexpected error occurred: " + ex.getMessage();
//        return createResponse("40401001", message, "system error");
//    }

    /**
     * 응답공통
     * 
     * @param 에러메시지객체
     * @return 응답객체
     */
    private ApiResponse<Object> createResponse(String code, String msg, String type) {
        ApiResponse.Error error = new ApiResponse.Error();
        error.setCode(code);
        error.setMessage(msg);
        error.setType(type);
        return ApiResponse.builder().error(error).build();
    }

}

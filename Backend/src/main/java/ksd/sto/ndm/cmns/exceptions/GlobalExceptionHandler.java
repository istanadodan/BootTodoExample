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
import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    // BizException 처리
    @ExceptionHandler(BizException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleBizException(BizException ex) {
        return new ResponseEntity<>("Business error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<String> handleMethodNotAllowedException(HttpRequestMethodNotSupportedException ex) {
        return new ResponseEntity<>("Method not allowed: " + ex.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
    }
    // No Resource Found 에러 처리
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleNoResourceFoundException(NoResourceFoundException ex, HttpServletRequest request) {
        ModelAndView mView = new ModelAndView("forward:/login");
        return mView;
//        return new ResponseEntity<>("Method not allowed: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 일반 Exception 처리
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}

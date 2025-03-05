package ksd.sto.ndm.cmns.filters;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.IntStream;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    private Gson gson = new Gson();

    @Around("execution(public * ksd.sto.ndm.domain.*..*ServiceImpl.*(..))")
    public Object logMethodParameters(ProceedingJoinPoint joinPoint) throws Throwable {
        Map<String, Object> nameValueMap = getNameValueMap(joinPoint);
        String methodName = joinPoint.getSignature().toShortString();

        String loggingText = "";
        for (Entry<String, Object> arg : nameValueMap.entrySet()) {
            // String paramValue = gson.toJson(arg);
            // paramValue = (paramValue.length() < 1000) ? paramValue :
            // paramValue.substring(0, 1000);
            loggingText += "\n%s: %s".formatted(arg.getKey(), arg.getValue());
        }
        if (loggingText.equals("") == true) { loggingText = "No Parameters"; }

        logger.info("Method: {} {}", methodName, loggingText);

        return joinPoint.proceed();
    }

    /**
     * parameter map 작성
     * 
     * @param joinPoint
     * @param method
     * @return
     */
    private Map<String, Object> getNameValueMap(ProceedingJoinPoint joinPoint) {
        Parameter[] paramNames = ((MethodSignature) joinPoint.getSignature())
            .getMethod()
            .getParameters();
        Object[] paramValues = joinPoint.getArgs();

        Map<String, Object> nameValueMap = new HashMap<>();
        for (int i = 0; i < paramNames.length; ++i) {
            String v = gson.toJson(paramValues[i]);
            nameValueMap.put(paramNames[i].getName(), v.substring(0, Math.min(v.length(), 1000)));
        }
        return nameValueMap;
    }
}

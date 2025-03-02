package ksd.sto.ndm.cmns.filters;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
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
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        
        if (args.length == 0) {
            logger.info("\nMethod: {} - Parameter is null", methodName);
        }
        for (Object arg : args) {
            String paramValue = gson.toJson(arg);
            paramValue = (paramValue.length()<1000)? paramValue: paramValue.substring(0, 1000);
            if (arg != null) {
                logger.info("\nMethod: {} \nParameter Type: {} \nValue: {}", methodName, arg.getClass().getSimpleName(), paramValue);
            } else {
                logger.info("\nMethod: {} \nParameter is null", methodName);
            }
        }

        return joinPoint.proceed();
    }
}

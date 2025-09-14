package d.shunyaev.RemoteTrainingApp.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.core.containers.RequestContainer;
import org.core.context.RequestContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Order(1)
public class RequestInterceptor {

    @Around("execution(* d.shunyaev.RemoteTrainingApp.controllers.*.*(..))")
    public Object interceptRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        Arrays.stream(joinPoint.getArgs())
                .filter(arg -> arg instanceof RequestContainer<?>)
                .map(arg -> (RequestContainer<?>) arg)
                .findFirst()
                .ifPresent(RequestContext::setCurrentRequest);
        return joinPoint.proceed();
    }
}
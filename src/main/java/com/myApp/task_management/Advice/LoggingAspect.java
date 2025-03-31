package com.myApp.task_management.Advice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Around("execution(* com.myApp..*.*(..))")
    public Object logExecutionDetails(ProceedingJoinPoint jointPoint) throws Throwable {
        String functionName = jointPoint.getSignature().getName();
        log.info("Executing method - {}", functionName);
        
        long startTime = System.currentTimeMillis();
        Object proceed = jointPoint.proceed();
        long executionTime = System.currentTimeMillis() - startTime;

        log.info("Executed method - {} in {}ms", functionName, executionTime);
        return proceed;
    }
}

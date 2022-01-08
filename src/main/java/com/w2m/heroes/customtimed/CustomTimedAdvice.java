package com.w2m.heroes.customtimed;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@ConditionalOnExpression("${aspect.enabled:false}")
public class CustomTimedAdvice {

    @Around("@annotation(com.w2m.heroes.annotations.CustomTimed)")
    public Object executionTime(ProceedingJoinPoint point) throws Throwable {
        long start = System.currentTimeMillis();
        Object object = point.proceed();
        long end = System.currentTimeMillis();

        log.info("Class Name: "+ point.getSignature().getDeclaringTypeName() +". Method Name: "+ point.getSignature().getName() + ". Time taken for Execution is : " + (end-start) +"ms");
        return object;
    }
}

package com.talentgrid.app.aspects;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LoggingAndPerformanceAspect {


    @Around("execution(* com.talentgrid.app..*.*(..))")
    public Object logAndMeasureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();

        String methodName = joinPoint.getSignature().toShortString();

        Object[] methodArgs = joinPoint.getArgs();

        
        //These debug message will not be displayed because in logback.xml it is declared as INFO. Either change INFO to DEBUG or change log.debug to log.info
        log.debug("➡️ Entering method: {}", methodName);
        log.debug("📥 Arguments: {}", Arrays.toString(methodArgs));

        // Proceed with actual business method
        Object result = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - startTime;

        log.debug("✅ Method executed successfully: {}", methodName);
        log.debug("⏱ Execution time: {} ms", executionTime);

        return result;

    }

}

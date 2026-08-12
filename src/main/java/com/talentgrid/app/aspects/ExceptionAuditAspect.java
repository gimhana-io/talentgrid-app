package com.talentgrid.app.aspects;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class ExceptionAuditAspect {
    @AfterThrowing(
            pointcut = "execution(* com.talentgrid.app..*.*(..))",
            throwing = "ex"
    )
    public void logAfterException(JoinPoint joinPoint, Exception ex) {
        
        String methodName = joinPoint.getSignature().toShortString();
        Object[] methodArgs = joinPoint.getArgs();

        log.error("❌ Exception occurred in method: {}", methodName);
        log.error("📥 Arguments: {}", Arrays.toString(methodArgs));
        log.error("💥 Exception type: {}", ex.getClass().getSimpleName());
        log.error("🧾 Exception message: {}", ex.getMessage());


    }
}

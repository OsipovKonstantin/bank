package ru.neoflex.bank.common.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ExceptionLoggingAspect {
    @Around("@within(ru.neoflex.bank.common.logging.ExceptionLogging) || @annotation(ru.neoflex.bank.common.logging.ExceptionLogging)")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        log.error("{}", result);
        return result;
    }
}
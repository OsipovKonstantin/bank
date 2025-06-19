package ru.neoflex.bank.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Around("@within(ru.neoflex.bank.logging.Logging) || @annotation(ru.neoflex.bank.logging.Logging)")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getName();
        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        String params = IntStream.range(0, parameterNames.length).mapToObj(i -> parameterNames[i] + "=" + args[i])
                .collect(Collectors.joining(", "));

        log.info("{} вызван с аргументами {}", methodName, params);
        Object result = joinPoint.proceed();
        log.info("{} возвращает {}", methodName, result);
        return result;
    }
}
package com.winstar.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Aspect
@Slf4j
public class RetryAspect {

    AtomicInteger retryNum = new AtomicInteger(1);

    @Around("@annotation(retryable)")
    public Object doAround(ProceedingJoinPoint joinPoint, BinRetryable retryable) throws Throwable {
        Object obj = null;
        log.info("进入增强&& target=" + joinPoint.getTarget());
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Class<? extends Exception> value = retryable.value();
        int num = retryable.maxAttempts();
        if (ObjectUtils.isEmpty(value)) {
            return joinPoint.proceed();
        }
        while (retryNum.get() <= num) {
            try {
                obj = joinPoint.proceed();
                return obj;
            } catch (Exception e) {
                retryNum.incrementAndGet();
                Thread.sleep(200);
            }
        }

        return obj;
    }


}

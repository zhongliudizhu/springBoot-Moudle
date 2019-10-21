package com.winstar.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
@Slf4j
public class RetryAspect {


    @Pointcut("@annotation(com.winstar.aop.BinRetryable)")
    public void annotationPointcut() {
    }

    @Around("annotationPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("进入增强&& target=" + joinPoint.getTarget());
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Class<? extends Exception> clazz = method.getAnnotation(BinRetryable.class).value();
        int num = method.getAnnotation(BinRetryable.class).maxAttempts();
        if (num <= 1) {
            return joinPoint.proceed();
        }
        int retryNum = 0;
        while (retryNum <= num) {
            try {
                retryNum++;
                log.info("====进行第" + retryNum + "次调用====");
                return joinPoint.proceed();
            } catch (Exception e) {
                if (retryNum >= num || !e.getClass().isAssignableFrom(clazz)) {
                    throw e;
                }
                Thread.sleep(200);
            }
        }

        return null;
    }


}

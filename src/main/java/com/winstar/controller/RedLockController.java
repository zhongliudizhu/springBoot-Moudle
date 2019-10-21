package com.winstar.controller;

import com.winstar.common.Result;
import com.winstar.common.ServiceManager;
import com.winstar.entity.Activity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/redLock")
@Slf4j
public class RedLockController {

    private StringRedisTemplate stringRedisTemplate;
    private static final String key = "redis_lock";
    private ExecutorService pool = Executors.newCachedThreadPool();
    private static CountDownLatch cd = new CountDownLatch(1);
    private final DefaultRedisScript<Boolean> defaultRedisScript = new DefaultRedisScript<>();

    @PostConstruct
    public void init() {
        defaultRedisScript.setLocation(new ClassPathResource("redis.lua"));
        defaultRedisScript.setResultType(Boolean.class);
    }


    @Autowired
    public RedLockController(StringRedisTemplate redisTemplate) {
        this.stringRedisTemplate = redisTemplate;
    }

    @PostMapping("/getLock")
    public Result getLock(@RequestBody @Valid Activity activity) {
        String value = UUID.randomUUID().toString();
        try {
            Boolean execute = stringRedisTemplate.execute(defaultRedisScript, Arrays.asList(key), value, 300, TimeUnit.MILLISECONDS);
            log.info("execute=" + execute
            );
            if (execute) {
                log.info("{Thread}name 为" + Thread.currentThread().getName() + "开始执行业务逻辑");
                cd.countDown();
                pool.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            cd.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        log.info("=====定时器执行====");

                    }
                });
                ServiceManager.activityRepository.save(activity);

            }


        } finally {
            if (stringRedisTemplate.opsForValue().get(key).equals(value)) {
                stringRedisTemplate.delete(key);
            }
        }


        return new Result();
    }


}

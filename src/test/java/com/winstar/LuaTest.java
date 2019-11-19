package com.winstar;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class LuaTest {
    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void luaTest() {
        DefaultRedisScript<List> defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setResultType(List.class);
        defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("redis.lua")));
        List key = Lists.newArrayList("addOrder");
        //这里虽然使用List集合，但是暂时只存储一个值（Key+时间戳）
        List argList = Lists.newArrayList(10);
        //这里的StringRedisTemplate需要自己初始化并配置
        Object ret = redisTemplate.execute(defaultRedisScript, key, argList);
        log.info("ret is {}", ret);

    }

}

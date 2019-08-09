package com.winstar;

import com.winstar.entity.Goods;
import com.winstar.repository.GoodsRepository;
import com.winstar.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.*;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.core.script.ScriptExecutor;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class SpringbootModleApplicationTests {
    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void test1() {
        String date = "2018-10-25 23:59:59";
        List<Goods> goodsList = goodsRepository.findByStatusInAndAndCreateTimeBeforeOrderByCreateTimeDesc(Arrays.asList(1, 2), DateUtil.strToDate(date));
        log.info("====" + goodsList);
    }


    @Test
    public void test2() {
        DefaultRedisScript script = new DefaultRedisScript();
        script.setLocation(new ClassPathResource("redis.lua"));
        script.setResultType(String.class);
        Object execute = redisTemplate.execute(script, Collections.singletonList("customer"), "dvfdv", "fdgd", 12);
        log.info("{result}为" + execute);

    }


}

package com.winstar.controller;

import com.winstar.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/redis")
@Slf4j
public class RedisController {
    @Autowired
    RedisTemplate redisTemplate;
    private static String article_key_prefix = "article:score";

    @GetMapping("/getScoreRange")
    public Result getScoreRange() {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();


        return null;
    }



}

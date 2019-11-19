package com.winstar.controller;

import com.alibaba.fastjson.JSON;
import com.winstar.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/redis")
@Slf4j
public class RedisController {
    @Autowired
    RedisTemplate redisTemplate;
    private static String article_key_prefix = "article:score";

    @PostMapping("/addOrder")
    public Result addOrder(@RequestBody Map map) {
        log.info("入参为 {}", JSON.toJSONString(map));
        String accountId = MapUtils.getString(map, "accountId");
        String code = MapUtils.getString(map, "code");
        String itemId = MapUtils.getString(map, "itemId");
        String couponId = MapUtils.getString(map, "couponId");
        Result result = checkResultAndCode(accountId, code, itemId);
        if (!ObjectUtils.isEmpty(result)) {
            return result;
        }
        if (StringUtils.isEmpty(couponId)) {


        }

        return null;
    }

    private Result checkResultAndCode(String accountId, String code, String itemId) {
        if (StringUtils.isEmpty(accountId) || StringUtils.isEmpty(code) || StringUtils.isEmpty(itemId)) {
            return Result.fail("param_missing", "param缺失");
        }
        String key = accountId + "<-->" + accountId;
        if (!code.equals(redisTemplate.opsForValue().get(key).toString())) {
            return Result.fail("repeat_add_order", "您已经下过单了，请勿重复点击");
        }
        log.info("验证 code 成功");
        redisTemplate.delete(key);
        return null;
    }


}

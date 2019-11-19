package com.winstar.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.winstar.common.Result;
import com.winstar.service.RetryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/retry")
@AllArgsConstructor
@Slf4j
public class RetryController {

    RetryService retryService;
    RedisTemplate redisTemplate;
    private static final String envelop_pool = "envelop_";
    private static final String has_take_prefix = "has_take";
    private static final String envelop_record = "envelop_record";

    AlipayClient alipayClient;

    @GetMapping("/doRetry")
    public Result doRetry() {
        Map map = retryService.doRetry();
        return Result.success(map);
    }

    @PostMapping("/getCacheData")
    public Result getCacheData(@RequestBody Map map) {
        String userId = MapUtils.getString(map, "userId");
        String key = MapUtils.getString(map, "key");
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(key)) {
            return Result.fail("", "");
        }
        ListOperations listOperations = redisTemplate.opsForList();

        return Result.success(listOperations.leftPop(key));
    }

    @PostMapping("/initEnvelop")
    public Result initEnvelop(@RequestBody Map map) {


        String accountId = MapUtils.getString(map, "accountId");
        Double amount = MapUtils.getDouble(map, "amount");
        Integer count = MapUtils.getInteger(map, "count");
        Result result = validateParam(accountId, amount, count);
        if (!ObjectUtils.isEmpty(result)) {
            return result;
        }
        String listKey = envelop_pool + accountId;
        ListOperations listOperations = redisTemplate.opsForList();
        List<Double> moneyList = IntStream.range(0, count).mapToObj(i -> new Random()).map(Random::nextDouble).collect(Collectors.toList());
        if (listOperations.rightPushAll(listKey, moneyList).intValue() == count) {
            log.info("初始化红包信息成功，红包数为 {}", listOperations.size(listKey));
            redisTemplate.expire(listKey, 2, TimeUnit.HOURS);
            return Result.success(true);

        }
        return Result.fail("cache_error", "cache_error");
    }

    private Result validateParam(String accountId, Double amount, Integer count) {

        if (StringUtils.isEmpty(accountId) || StringUtils.isEmpty(amount) || StringUtils.isEmpty(count)) {
            return Result.fail("param_error", "参数错误");
        }
        if (amount <= 0 || count <= 0) {
            return Result.fail("", "");
        }
        return null;
    }


    @PostMapping("/takeEnvelop")
    public Result getRedPackage(@RequestParam String receiveAccountId, @RequestParam String makeAccountId) {
        //控制超抢问题
        ListOperations listOperations = redisTemplate.opsForList();
        SetOperations setOperations = redisTemplate.opsForSet();
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        if (setOperations.add(has_take_prefix, receiveAccountId) != 1) {
            return Result.fail("receive_failed", "无法重复领取");
        }
        Object value = listOperations.rightPop(envelop_pool + makeAccountId);
        if (ObjectUtils.isEmpty(value)) {
            log.info("红包已抢完，无法领取");
            return Result.fail("red_empty", "红包已抢完，无法领取");
        }
        //展示每个人个领了多钱
        Map<String, Object> data = new HashMap<>();
        data.put("accountId", receiveAccountId);
        data.put("money", value);
        zSetOperations.add(envelop_record, receiveAccountId, Double.valueOf(value.toString()));
        return Result.success(data);
    }

    @GetMapping("/getEnvelopInfo")
    public Result getEnvelopInfo() {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        Set set = zSetOperations.reverseRangeWithScores(envelop_record + "dd", 0, -1);
        return Result.success(set);
    }


    @PostMapping("/executeAliPay")
    public String executeAliPay(String orderNum, String totalMoney) {
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl("");
        request.setReturnUrl("");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("out_trade_no", orderNum);
        paramMap.put("product_code", "FAST_INSTANT_TRADE_PAY");
        paramMap.put("total_amount", totalMoney);
        paramMap.put("subject", "4343ret343543");
        request.setBizContent(JSON.toJSONString(paramMap));
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody(); //调用SDK生成表单
            log.info("form is {}", form);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return form;
    }



}

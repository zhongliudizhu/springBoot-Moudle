package com.winstar.controller;

import com.winstar.common.Result;
import com.winstar.entity.Customer;
import com.winstar.repository.CustomerRepository;
import com.winstar.service.RetryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/retry")
@AllArgsConstructor
@Slf4j
public class RetryController {

    RetryService retryService;
    RedisTemplate redisTemplate;
    CustomerRepository customerRepository;
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
        if (listOperations.size(key) == 0) {
            List<Customer> customerList = customerRepository.findAll(new PageRequest(1, 10, Sort.Direction.DESC, "createdAt")).getContent();
            if (!CollectionUtils.isEmpty(customerList)) {
                listOperations.rightPushAll(key, customerList);
                Long size = listOperations.size(key);
                log.info("size is {}", size);
            }
        }
        return Result.success(listOperations.leftPop(key));
    }


}

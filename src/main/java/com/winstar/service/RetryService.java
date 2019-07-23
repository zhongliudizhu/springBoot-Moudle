package com.winstar.service;

import com.winstar.aop.BinRetryable;
import com.winstar.utils.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class RetryService {

    private static final String url = "http://localhost:12002/api/v1/coupon/takeCoupons?";
    @Autowired
    RestTemplate restTemplate;


    //@Retryable(value = {Exception.class}, maxAttempts = 4,backoff = @Backoff(delay = 2000L, multiplier = 1.5))
    @BinRetryable(value = Exception.class, maxAttempts = 3)
    public Map doRetry() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("templateId", "4028f69c6b1add64016b207b9a3d004f");
        paramMap.put("num", "5");
        ResponseEntity<Map> responseEntity;
        try {
            responseEntity = restTemplate.getForEntity(url + SignUtil.getParameters(paramMap), Map.class);
        } catch (Exception e) {
            throw e;
        }
        if (!ObjectUtils.isEmpty(responseEntity)) {
            return responseEntity.getBody();
        }
        return new HashMap();
    }
}

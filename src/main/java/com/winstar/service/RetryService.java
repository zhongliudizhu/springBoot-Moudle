package com.winstar.service;

import com.winstar.utils.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class RetryService {

    private static final String url = "http://localhost:12002/api/v1/coupon/takeCoupons?";
    @Autowired
    RestTemplate restTemplate;

    @Retryable(value = {Exception.class}, maxAttempts = 4, backoff = @Backoff(delay = 2000L, multiplier = 1.5))
    // @BinRetryable(value = Exception.class, maxAttempts = 3)
    public Map doRetry() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("templateId", "4028f69c6b1add64016b207b9a3d004f");
        paramMap.put("num", "5");
        paramMap.put("merchant", SignUtil.merchant);
        ResponseEntity<Map> responseEntity;
        try {
            responseEntity = restTemplate.getForEntity(url + SignUtil.getParameters(paramMap), Map.class);
        } catch (Exception e) {
            throw e;
        }
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            Map body = responseEntity.getBody();
            if (body.get("code").equals("SUCCESS")) {
                List<Map<String, Object>> data = (List<Map<String, Object>>) body.get("data");
                for (Map<String, Object> map : data) {
                    Set<String> set = map.keySet();
                    for (String s : set) {
                        log.info("+++++key==" + s);
                        log.info("+++++value==" + map.get(s));
                    }

                }
            }
            return responseEntity.getBody();
        }
        return new HashMap();
    }
}

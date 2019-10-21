package com.winstar;

import com.alibaba.fastjson.JSON;
import com.winstar.utils.CJXSignUtil;
import com.winstar.utils.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class CarTownTest {
    @Autowired
    RestTemplate restTemplate;
    private static final String queryGoodsUrl = "https://test.chejiaxiang.com/docking/query_goods_list";
    private static final String createOrderUrl = "https://test.chejiaxiang.com/docking/created_order";
    private static final String appSecret = "pzadl53oyj2x9sxikwlyimv1ttmj7x1x";

    @Test
    public void testQuery() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("channelid", "1564469482");
        paramMap.put("timestamp", "trt");
        paramMap.put("appsecret", appSecret);
        paramMap.put("sign", CJXSignUtil.sign(paramMap));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity httpEntity = new HttpEntity<>(JSON.toJSONString(paramMap), httpHeaders);
        Map map = restTemplate.exchange(queryGoodsUrl, HttpMethod.POST, httpEntity, Map.class).getBody();
        log.info("body is {}, retCode is {},retMessage is {}", JSON.toJSONString(map), map.get("retcode"), map.get("retmessage"));
    }

    @Test
    public void testCreate() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("channelid", "1564469482");
        paramMap.put("user_phone", "15721653214");
        paramMap.put("user_name", "tommy");
        paramMap.put("order_date", "20190308185212");
        paramMap.put("amt", "600");
        paramMap.put("quantity", "20");
        paramMap.put("product_num", "by1601");
        paramMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
        paramMap.put("appsecret", appSecret);
        paramMap.put("sign", CJXSignUtil.sign(paramMap));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity httpEntity = new HttpEntity<>(JSON.toJSONString(paramMap), httpHeaders);
        Map map = restTemplate.exchange(createOrderUrl, HttpMethod.POST, httpEntity, Map.class).getBody();
        log.info("body is {}, retCode is {},retMessage is {}", JSON.toJSONString(map), map.get("retcode"), map.get("retmessage"));
    }


}


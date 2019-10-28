package com.winstar;

import com.alibaba.fastjson.JSON;
import com.winstar.utils.CJXSignUtil;
import com.winstar.vo.OrderParams;
import com.winstar.vo.ProductInfoVo;
import org.apache.commons.collections4.MapUtils;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CarTownTest {

    RestTemplate restTemplate = new RestTemplate();
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
        if (MapUtils.getString(map, "retcode").equals("00")) {
            List<ProductInfoVo> infoVoList = JSON.parseArray(map.get("data").toString(), ProductInfoVo.class);
            System.out.println(infoVoList);
        } else {
            System.out.println("retmessage is " + JSON.toJSONString(MapUtils.getString(map, "retmessage")));
        }
    }

    @Test
    public void testCreate() {
        OrderParams orderParams = new OrderParams("1564469482", "wefwfew", "15721653214", "张飞", "20190308185212", "630", "600", "xc1301", "123", String.valueOf(System.currentTimeMillis()), appSecret);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity httpEntity = new HttpEntity<>(JSON.toJSONString(orderParams), httpHeaders);
        Map map = restTemplate.exchange(createOrderUrl, HttpMethod.POST, httpEntity, Map.class).getBody();
        System.out.println("data is " + JSON.toJSONString(map));
    }


}


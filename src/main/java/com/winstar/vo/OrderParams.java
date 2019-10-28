package com.winstar.vo;

import com.alibaba.fastjson.JSON;
import com.winstar.utils.CJXSignUtil;
import lombok.*;

import java.net.URLEncoder;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderParams {

    private String channelid;
    private String order_no;
    private String user_phone;
    private String user_name;
    private String order_date;
    private String amt;
    private String quantity;
    private String product_num;
    private String product_type;
    private String timestamp;
    private String appsecret;
    private String sign;

    public OrderParams(String channelid, String order_no, String user_phone, String user_name, String order_date, String amt, String quantity, String product_num, String product_type, String timestamp, String appsecret) {
        this.channelid = channelid;
        this.order_no = order_no;
        this.user_phone = user_phone;
        this.user_name = URLEncoder.encode(user_name);
        this.order_date = order_date;
        this.amt = amt;
        this.quantity = quantity;
        this.product_num = product_num;
        this.product_type = product_type;
        this.timestamp = timestamp;
        this.appsecret = appsecret;
        this.sign = makeSign(this);
    }

    public OrderParams(String channelid, String user_phone, String user_name, String order_date, String amt, String quantity, String product_num, String timestamp, String appsecret) {
        this.channelid = channelid;
        this.user_phone = user_phone;
        this.user_name = URLEncoder.encode(user_name);
        this.order_date = order_date;
        this.amt = amt;
        this.quantity = quantity;
        this.product_num = product_num;
        this.timestamp = timestamp;
        this.appsecret = appsecret;
        this.sign = makeSign(this);
    }

    private String makeSign(OrderParams orderParams) {
        Map<String, Object> paramMap = JSON.parseObject(JSON.toJSONString(orderParams));
        Map<String, String> map = paramMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, m -> m.getValue().toString(), (a, b) -> b));
        return CJXSignUtil.sign(map);
    }

    public void setUser_name(String user_name) {
        this.user_name = URLEncoder.encode(user_name);
    }
}

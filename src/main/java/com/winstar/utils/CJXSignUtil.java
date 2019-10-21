package com.winstar.utils;

import org.apache.commons.collections4.MapUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by zl on 2019/5/16
 * 签名验签类
 */
public class CJXSignUtil {


    public static String sign(Map<String, String> parameters) {
        String unSigned = getParameters(parameters);
        System.out.println("待加密字符串：" + unSigned);
        String sign = DigestUtils.md5DigestAsHex(unSigned.getBytes()).toUpperCase();
        System.out.println("sign is " + sign);
        return sign;
    }

    public static String getParameters(Map<String, String> parameters) {
        StringBuilder param = new StringBuilder();
        TreeMap<String, Object> map = new TreeMap<>(parameters);
        Set es = map.entrySet();
        for (Object e : es) {
            Map.Entry entry = (Map.Entry) e;
            Object value = entry.getValue();
            String key = (String) entry.getKey();
            if (!StringUtils.isEmpty(value)) {
                param.append(key).append("=").append(value).append("&");
            }
        }
        return param.toString().substring(0, param.toString().length() - 1);
    }

    public static boolean checkSign(Map<String, String> parameters) {
        String sign = MapUtils.getString(parameters, "sign");
        parameters.remove("sign");
        return sign.equals(sign(parameters));
    }

}


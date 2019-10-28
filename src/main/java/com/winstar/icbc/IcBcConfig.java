package com.winstar.icbc;

import com.icbc.api.DefaultIcbcClient;
import com.icbc.api.IcbcClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IcBcConfig {

    public static String appId;
    public static String privateKey;
    public static String format = "json";
    public static String gateWayPubKey;
    public static String notifyUrl;
    public static String charset = "utf-8";


    @Value("${icbc.appId}")
    public static void setAppId(String appId) {
        IcBcConfig.appId = appId;
    }

    @Value("${icbc.privateKey}")
    public static void setPrivateKey(String privateKey) {
        IcBcConfig.privateKey = privateKey;
    }

    @Value("${icbc.gateWayPubKey}")
    public static void setGateWayPubKey(String gateWayPubKey) {
        IcBcConfig.gateWayPubKey = gateWayPubKey;
    }

    @Value("${icbc.notifyUrl}")
    public static void setNotifyUrl(String notifyUrl) {
        IcBcConfig.notifyUrl = notifyUrl;
    }

    @Bean
    public IcbcClient icbcClient() {
        return new DefaultIcbcClient(appId, privateKey, gateWayPubKey, charset, format);
    }
}

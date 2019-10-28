package com.winstar;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.winstar.aliPay.AliPayConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableRetry
@EnableAspectJAutoProxy
@EnableAsync
@EnableScheduling
public class SpringbootModleApplication {


    public static void main(String[] args) {
        SpringApplication.run(SpringbootModleApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    @Bean
    public AlipayClient alipayClient() {
        return new DefaultAlipayClient(AliPayConfig.APPID, AliPayConfig.RSA_PRIVATE_KEY, AliPayConfig.URL, AliPayConfig.FORMAT, AliPayConfig.CHARSET, AliPayConfig.ALIPAY_PUBLIC_KEY, AliPayConfig.SIGNTYPE);
    }

}

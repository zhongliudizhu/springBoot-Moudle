package com.winstar.aliPay;

public interface AliPayConfig {
    // 商户appid
    String APPID = "2016101300672964";
    // 私钥
    String RSA_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKCkH8iELFI2JJRQVrCXWozee1eYaWVyHwBWzLm0Sy8nFSCISi4Zm48iyJ//Eph/MRXMoZ53YAi5y101H6YL83IsksC4JZNWWUVTECMn8bKoVSzG6hOzoGUwL+u2PXTYxx2tUElny7/ZhdzehkTZXjfQRbey4ClZkj7P4tBgU6DdAgMBAAECgYANHRjTH6cWlwDpcp2BEPn9YfQjtHd2JpjNSEiyKDuy/7fDzhxwIvsF9kr2dLYp6MgzBV90Nj6Kt9AYLcwFnBglareoqqMiw918DHFjVagAujHw0HrhOOK5yu24iEPaLNJ8n858K16GigRRVG/nVUNC9g1+qcJN03yxYW0Bz4OSqQJBANoVg0kYWHzT5vLLW0rALy0vmOtrxqvUu1hHGBrZzBnSbfmLLTDWzBz4PR8f/CJF2zbkOFELSlBM7Qw6wGPVpecCQQC8kfDb34NwVW02ke0jLrhMaasXtDHc1A18D1L8UOF23IAf9GM7U2nON4ZBTWmtHSLUHpGBXdbQ4Mq5fl+VpKKbAkEAt31kQcsHILgV1/C79g/vYaBrlKDQvuC1ZFgk8uqPbKZ9u3mbYz1G7ZT7sEV0Gc4W3H921tGeDCQ9So3gM9+dvwJAK0fL4otgna3dzUKdDRAyGMfAD0a6kxbQqYLS5zqhJSji93KePAWfKUexNtfPw34Gem0xtGGXZoXRwdVxluUctQJBAJ1IXqeWHyhTRWNTMaIQcMFCrz/sQJpuL4y6AfLkVBPz/jlEsxOFnoPyCZylIPy2jNEDtY4RNgkwlLGePF4SkJs=";
    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    String notify_url = "http://商户网关地址/alipay.trade.wap.pay-JAVA-UTF-8/notify_url.jsp";
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    String return_url = "http://商户网关地址/alipay.trade.wap.pay-JAVA-UTF-8/return_url.jsp";
    // 请求网关地址
    String URL = "https://openapi.alipaydev.com/gateway.do";
    // 编码
    String CHARSET = "UTF-8";
    // 返回格式
    String FORMAT = "json";
    // 支付宝公钥
    String ALIPAY_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIgHnOn7LLILlKETd6BFRJ0GqgS2Y3mn1wMQmyh9zEyWlz5p1zrahRahbXAfCfSqshSNfqOmAQzSHRVjCqjsAw1jyqrXaPdKBmr90DIpIxmIyKXv4GGAkPyJ/6FTFY99uhpiq0qadD/uSzQsefWo0aTvP/65zi3eof7TcZ32oWpwIDAQAB";
    // 日志记录目录
    String log_path = "/log";
    // RSA2
    String SIGNTYPE = "RSA2";
}

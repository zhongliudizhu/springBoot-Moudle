package com.winstar;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.icbc.api.utils.IcbcEncrypt;
import com.icbc.api.utils.IcbcSignature;
import com.icbc.api.utils.WebUtils;

import cn.com.infosec.icbc.ReturnValue;

public class NotifyUrlServlet extends HttpServlet {

    private static final long serialVersionUID = 5702944193354207267L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        PrintWriter out = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String reqTime = sdf.format(new Date());
            String results = "Only support POST method. systime is :" + reqTime;
            resp.setContentType("application/json; charset=utf-8");
            out = resp.getWriter();
            out.write(results);
        } catch (Exception e) {
            e.printStackTrace();
            out.write(e.getMessage());
        } finally {
            out.flush();
            out.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        PrintWriter out = null;
        try {
            //网关公钥
            String APIGW_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwFgHD4kzEVPdOj03ctKM7KV+16bWZ5BMNgvEeuEQwfQYkRVwI9HFOGkwNTMn5hiJXHnlXYCX+zp5r6R52MY0O7BsTCLT7aHaxsANsvI9ABGx3OaTVlPB59M6GPbJh0uXvio0m1r/lTW3Z60RU6Q3oid/rNhP3CiNgg0W6O3AGqwIDAQAB";
            //商户证书，cert_sn=517722432651109523795125，有效期=20150302-20350302
            String cert = "MIIDDTCCAfWgAwIBAgIKbaHKEE0tAAAstTANBgkqhkiG9w0BAQUFADA3MRowGAYDVQQDExFjb3JiYW5rMTAzIHNkYyBDTjEZMBcGA1UEChMQY29yYmFuazQzLmNvbS5jbjAeFw0xNTAzMDIwMzA4MDFaFw0zNTAzMDIwMzA4MDFaMEMxFzAVBgNVBAMTDmxpdXdoMzEueS4wMjAwMQ0wCwYDVQQLEwQwMjAwMRkwFwYDVQQKExBjb3JiYW5rNDMuY29tLmNuMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDzvGvNMJKzqZHrEKKcboCLemlLGnVQ1Za28qL6IQXgT0W4zKG7tes7YMDd3JL6EngE/RXhTviymkzyZwp7RTbe4Hxhsjl3l6+HsCflRJv8dPH1PqQjtzq0gD2primFI/iGm7oSruTcE8/G1ONkVlCMbNysc1IhnCOVU66JR/XsyQIDAQABo4GSMIGPMB8GA1UdIwQYMBaAFKnyXV7yfyOkd7D4zZtPLyquqLWdME0GA1UdHwRGMEQwQqBAoD6kPDA6MQ4wDAYDVQQDEwVjcmwxMjENMAsGA1UECxMEY2NybDEZMBcGA1UEChMQY29yYmFuazQzLmNvbS5jbjAdBgNVHQ4EFgQUcL8GdzLHRtN6yiJUlDC/u63nTyAwDQYJKoZIhvcNAQEFBQADggEBABaLovTpH0ID9HH7nIrv4zMWGc5PWvDBdc8k/lmHo6HfdAVwI/050Lll0IyzZ19FZhfA5Frpnwmk49QLRMjIoVJvbO1Zs1Ey8fkxd2LBniBkKUbdmU6YbmkEnn2aIOi6fjyDPbLT7fVmOnZmNqNeA8/woo5DmW7/hcD0ImxApq9iMUGmWU+q3G7Wv9N77QhRGFefGvxQ9zRVb86xVv9yyCSA/ICx5Y9M68wiC/fIG3uC9t4kfE/+tikHCW2crY2BWQ/Q0vfo3gGbZx3zAxkd/HUnfKJTgkhNTX3tv8UxHg6DHX16SPkoXb9zjQsL+aJaLqbFruusDRvmbgJex+muE+0=";
            //商户私钥
            String CertPriKey = "AmGFOO6yoIabb6bYYp4luRMfRfzcYDlUWuys5VMcZDO3yi2W4f8oTyIz+mRGVMIYVWOiD7VaWZ9avkpAvdsosBj3D+M4Me9ZQCx1Y3TefnX3zu1ZLL3xk6Z+dH4Vt1yMLwaWfcF20jM+HkFF4DpCi1scv/6/XmTBae/unRscM26+zWqBVygOHUkXP2jnfOJAM1o3yp2mI5A+YxRD2JwR16lqgoW55xZ/1ufj/qd1MEBfA+aCeXMoqbdWUDsJGC8xyCMAi52TnrnROpcd4cHZFFbzwvaP3wyOmrWVkepOYj4RGGNeReCS4E7A2DTBEMTFyhgQdXzGCZm+tYXhsA4gqucI0yi3oQfAKlwGX4L77DB4Mula0wovAedvPpWseQouZfXSwt0N5yHG/3r/Du80s5gvCwgwncYvcsJm87Ot1ZH3PrHmSp50ndi703BkyuHIN7HjP27MO57crwEqxHvZ3getRt5+nkOGxxweHcZciMmNrLrB7huYBPcc7TarLHzUWQkC5fBqSrwDXjJCWZ7ZHd+5O0x/6uYnFCpKGXyOV4it+nzS6lo0RcYlM/eDxdQfkUaAKGtojNF5dZAdB1lCO/hGDD4KG0V5hZlI+ncZO8H2L1rJ4Rumz/qp9no8NgC+3OVVxnPd8jIsQFU+t68EhJ/oXkjmcSw+BhKtJLPxybZFkpx6rw903Og9qjcFu1tc7m+WKu0F8RC4a2cWI9Th6vR6Gk1gF34XNlkZVLolBbj7RkJ3/7fhBZlB5wnQIa0fZHw4qOoT86JItcxPrzbptYiqhCU18BA6Bna5EojumAy9zZo=";
			
			/* 获取证书序列号cert_sn的方法。
			byte[] merCert = ReturnValue.base64dec(cert.getBytes());
			ByteArrayInputStream bin = new ByteArrayInputStream(merCert);
			CertificateFactory cf = CertificateFactory.getInstance("X.509", "INFOSEC");
			Certificate certificate = cf.generateCertificate(bin);
			BigInteger sn = ((X509Certificate)certificate).getSerialNumber();
			bin.close();
			String serialNumber = sn.toString();
			System.out.print(serialNumber);
			*/

            Map<String, String> params = new HashMap<String, String>();
            String from = req.getParameter("from");
            String api = req.getParameter("api");
            String app_id = req.getParameter("app_id");
            String charset = req.getParameter("charset");
            String format = req.getParameter("format");
            String encrypt_type = req.getParameter("encrypt_type");
            String timestamp = req.getParameter("timestamp");
            String biz_content = req.getParameter("biz_content");
            String sign_type = req.getParameter("sign_type");
            String sign = req.getParameter("sign");
            params.put("from", from);
            params.put("api", api);
            params.put("app_id", app_id);
            params.put("charset", charset);
            params.put("format", format);
            params.put("encrypt_type", encrypt_type);
            params.put("timestamp", timestamp);
            params.put("biz_content", biz_content);
            params.put("sign_type", sign_type);//目前上行网关签名暂时仅支持RSA

            /**********验证工行上行网关RSA签名**********/

            //notify_url=http://122.20.29.133:9081/notifyUrlServlet
            String path = "/notifyUrlServlet";
            String signStr = WebUtils.buildOrderedSignStr(path, params);
            String results = null;
            String responseBizContent = null;
            boolean flag = IcbcSignature.verify(signStr, sign_type, APIGW_PUBLIC_KEY, charset, sign);
            if (!flag) {
                responseBizContent = "{\"return_code\":-12345,\"return_msg\":\"icbc sign not pass.\"}";
            } else {

                /**********biz_content解密**********/

                if ("AES".equals(encrypt_type)) {
                    String theKey = "12345678901234567890123456789012";
                    biz_content = IcbcEncrypt.decryptContent(biz_content, encrypt_type, theKey, charset);
                }

                /**********合作方/分行 业务逻辑处理**********/
                @SuppressWarnings("unchecked")
                Map<String, Object> respMap = (Map<String, Object>) JSON.parse(biz_content);
                String msg_id = respMap.get("msg_id").toString();
                //业务请求字段获取
                String busiParamRq = (String) respMap.get("busi_param_rq");
                //业务处理逻辑......
                System.out.print(busiParamRq);
                //业务返回参数设置
                int return_code = 0;
                String return_msg = "success.";
                responseBizContent = "{\"return_code\":" + return_code + ",\"return_msg\":\"" + return_msg + "\",\"msg_id\":\"" + msg_id + "\","
                        + "\"busi_param_rp\":\"thisisresponseparameter\"}";

                /**********response_biz_content加密**********/

                if ("AES".equals(encrypt_type)) {
                    String theKey = "12345678901234567890123456789012";
                    responseBizContent = IcbcEncrypt.encryptContent(responseBizContent, encrypt_type, theKey, charset);
                    responseBizContent = "\"" + responseBizContent + "\"";
                }

            }

            /**********商户对消息返回响应进行签名，签名方式需与在API平台登记APP的sign_type保持一致**********/
            //1、商户以CA证书签名为例，如下：
            signStr = "\"response_biz_content\":" + responseBizContent + "," + "\"sign_type\":" + "\"CA\"," + "\"ca\":\"" + cert + "\"";
            sign = IcbcSignature.sign(signStr, "CA", CertPriKey,
                    charset, "12345678");
            results = "{" + signStr + ",\"sign\":\"" + sign + "\"}";

            //2、商户以RSA签名为例，如下：其中，priKey为商户私钥；
			/*String priKey="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALAWAcPiTMRU906PTdy0ozspX7XptZnkEw2C8R64RDB9BiRFXAj0cU4aTA1MyfmGIlceeVdgJf7OnmvpHnYxjQ7sGxMItPtodrGwA2y8j0AEbHc5pNWU8Hn0zoY9smHS5e+KjSbWv+VNbdnrRFTpDeiJ3+s2E/cKI2CDRbo7cAarAgMBAAECgYABiA933q4APyTvf/uTYdbRmuiEMoYr0nn/8hWayMt/CHdXNWs5gLbDkSL8MqDHFM2TqGYxxlpOPwnNsndbW874QIEKmtH/SSHuVUJSPyDW4B6MazA+/e6Hy0TZg2VAYwkB1IwGJox+OyfWzmbqpQGgs3FvuH9q25cDxkWntWbDcQJBAP2RDXlqx7UKsLfM17uu+ol9UvpdGoNEed+5cpScjFcsB0XzdVdCpp7JLlxR+UZNwr9Wf1V6FbD2kDflqZRBuV8CQQCxxpq7CJUaLHfm2kjmVtaQwDDw1ZKRb/Dm+5MZ67bQbvbXFHCRKkGI4qqNRlKwGhqIAUN8Ynp+9WhrEe0lnxo1AkEA0flSDR9tbPADUtDgPN0zPrN3CTgcAmOsAKXSylmwpWciRrzKiI366DZ0m6KOJ7ew8z0viJrmZ3pmBsO537llRQJAZLrRxZRRV6lGrwmUMN+XaCFeGbgJ+lphN5/oc9F5npShTLEKL1awF23HkZD9HUdNLS76HCp4miNXbQOVSbHi2QJAUw7KSaWENXbCl5c7M43ESo9paHHXHT+/5bmzebq2eoBofn+IFsyJB8Lz5L7WciDK7WvrGC2JEbqwpFhWwCOl/w==";
			signStr="\"response_biz_content\":"+responseBizContent+","+"\"sign_type\":"+"\"RSA\"";
			sign=IcbcSignature.sign(signStr, "RSA", priKey,
					charset,"12345678");
			results="{"+signStr+",\"sign\":\""+sign+"\"}";*/

            resp.setContentType("application/json; charset=utf-8");
            out = resp.getWriter();
            out.write(results);

        } catch (Throwable e) {
            e.printStackTrace();
            out.write(e.getMessage());
        } finally {
            out.flush();
            out.close();
        }
    }
}

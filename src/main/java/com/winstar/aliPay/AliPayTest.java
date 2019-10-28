package com.winstar.aliPay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.KoubeiMarketingDataIndicatorQueryRequest;
import com.alipay.api.response.KoubeiMarketingDataIndicatorQueryResponse;
import com.winstar.icbc.IcBcConfig;

public class AliPayTest {
    private static final String appId = "2016101300672964";
    private static final String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJ1RYcEf2d9Zrr8SWUANnzKl9B25kqRWvBsSX6ntrt8DsXffVL98YcLvW1JwYxVyLv3lBMZ9hTTmJEzknpTCN5zM22PUnBzLepJ0XZ2nVQO3wPt2qABZstGs6qq1aYLptfSgHzZcW5jqXHfsDLE3wx7vnkHpGn6yIXqTKf5la24TAgMBAAECgYB0DoGrU7ALkcHUu9iT5Hi5iKxa8Xg5PLNw1VE1r17+r17RLDT9PulwQdyBKOAwBhicvcOUGy2WJOJKx64l2FHldc8OnXjt/ofIq+qyFyzuIEkZZZ98Rfgc0F/iM8ylNJsxlSoH2hedQRbdb9HKDYyY9RVTLztm6JqQZoE9Q+30oQJBAN0Slb4pLK6No0RxyQdoCSUR94oIGOk30WgfwWJ5vR7FbmFGI2MNozJSqW1xyQE5AORHDjN0OMAnYICyMd16VyUCQQC2LDMR8Kn36j+sBFMvZxgycmPP25mNukrsxAkYZtx4OvsdFuR7y8Vs3uIM6oh746b2cf403N0FQ5G2B91yjebXAkEA3ENVIQeDNlN2tY4tPozgd6fkHAofT9bGdNkW49fpn0wUwySPw3RmApCrsJjrLaWvBsLpGkkU9hcFppRk7IelZQJAG+LILobYfcqt1HcVBLyTR6WbAcZryvYpYfTgL4wPU5gzuFA4yXp3ziKQF8oO+mfuturcLttgOd4N//9UNqTiIQJAI+RSnoAxXvfH5zWi/xXK10z7Sb+Oe5BB7/v5bwjFZFsZFnE/2r3XVEUUqFDI+XLQJD0EXiq6X3NZmae1b0krrQ==";
    private static final String serverUrl = "https://openapi.alipaydev.com/gateway.do";
    private static final String aliPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIgHnOn7LLILlKETd6BFRJ0GqgS2Y3mn1wMQmyh9zEyWlz5p1zrahRahbXAfCfSqshSNfqOmAQzSHRVjCqjsAw1jyqrXaPdKBmr90DIpIxmIyKXv4GGAkPyJ/6FTFY99uhpiq0qadD/uSzQsefWo0aTvP/65zi3eof7TcZ32oWpwIDAQAB";

    public static void main(String[] args) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, appId, privateKey, "json", "UTF-8", aliPublicKey);
        KoubeiMarketingDataIndicatorQueryRequest request = new KoubeiMarketingDataIndicatorQueryRequest();
        request.setBizContent("{" +
                "\"out_biz_no\":\"0000001\"," +
                "\"operator_id\":\"2088102146931393\"," +
                "\"operator_type\":\"MER\"," +
                "\"name\":\"消费满10元送1元代金券\"," +
                "\"start_time\":\"2016-05-01 00:00:00\"," +
                "\"end_time\":\"2016-10-01 00:00:00\"," +
                "\"type\":\"DIRECT_SEND\"," +
                "\"desc\":\"该活动是用于挽回流失用户的\"," +
                "\"budget_info\":{" +
                "\"budget_type\":\"QUANTITY\"," +
                "\"budget_total\":\"10258\"," +
                "\"sub_budget_dimension\":\"D:表示每天的预算;\"," +
                "\"sub_value\":\"100:表示sub_dimension定义的纬度预算为100，若sub_dimension为D的话，则表示每天最多发放100张\"" +
                "    }," +
                "\"constraint_info\":{" +
                "\"user_win_count\":\"1\"," +
                "\"crowd_restriction_value\":\"2018012020075000000022612371,2018012020076000000022612371\"," +
                "\"user_win_frequency\":\"D||3\"," +
                "\"crowd_group_id\":\"12344556\"," +
                "\"crowd_restriction\":\"NEW_MEMBER_PROM" +
                "O:新会员" +
                "STUDENT：学生用户，仅对" +
                "DIRECT_SEND类型下的代金券，折扣券活动有效\"," +
                "        \"suit_shops\":[" +
                "          \"2015110600077000000002125023\",\"2015110600077000000002125022\"" +
                "        ]," +
                "\"min_cost\":\"100\"," +
                "        \"item_ids\":[" +
                "          \"212313\",\"221322\"" +
                "        ]" +
                "    }," +
                "\"auto_delay_flag\":\"N\"," +
                "      \"promo_tools\":[{" +
                "        \"voucher\":{" +
                "\"type\":\"MONEY\"," +
                "\"verify_mode\":\"MERCHANT_SCAN\"," +
                "\"voucher_note\":\"券的备注\"," +
                "            \"desc_detail_list\":[{" +
                "              \"title\":\"温馨提示\"," +
                "                \"details\":[" +
                "                  \"周一到周五可用\"" +
                "                ]," +
                "                \"images\":[" +
                "                  \"https://dl.django.t.taobao.com/rest/1.0/image?fileIds=RY7twkJVR26nz8OeXRIjvAAAACMAAQED&zoom=original\"" +
                "                ]," +
                "\"url\":\"www.alipay.com\"" +
                "              }]," +
                "\"multi_use_mode\":\"NO_MULTI\"," +
                "\"rounding_rule\":\"NOT_AUTO_ROUNDING\"," +
                "\"name\":\"券的名称\"," +
                "\"brand_name\":\"券副标题\"," +
                "            \"use_instructions\":[" +
                "              \"券的使用说明\"" +
                "            ]," +
                "\"logo\":\"1T8Pp00AT7eo9NoAJkMR3AAAACMAAQEC\"," +
                "\"item_info\":{" +
                "\"item_text\":\"单品券说明\"," +
                "\"sku_min_consume\":\"10\"," +
                "\"item_name\":\"单品名称\"," +
                "              \"item_imgs\":[" +
                "                \"1T8Pp00AT7eo9NoAJkMR3AAAACMAAQEC\"" +
                "              ]," +
                "\"item_link\":\"www.taobao.com\"," +
                "\"original_price\":\"28.88\"," +
                "\"max_discount_num\":\"1\"," +
                "\"min_consume_num\":\"1\"," +
                "              \"item_ids\":[" +
                "                \"0000123\",\"0000124\"" +
                "              ]," +
                "\"total_max_discount_num\":\"10\"" +
                "          }," +
                "\"voucher_img\":\"1T8Pp00AT7eo9NoAJkMR3AAAACMAAQEC\"," +
                "\"rate\":\"0.8\"," +
                "\"max_amount\":\"100\"," +
                "\"worth_value\":\"10\"," +
                "\"donate_flag\":\"true\"," +
                "\"validate_type\":\"FIXED\"," +
                "\"start_time\":\"2016-05-01 00:00:00\"," +
                "\"end_time\":\"2016-11-01 00:00:00\"," +
                "\"relative_time\":\"1\"," +
                "\"use_rule\":{" +
                "              \"use_time\":[{" +
                "                \"dimension\":\"W\"," +
                "\"values\":\"1,3,5\"," +
                "\"times\":\"16:00:00,20:00:00^21:00:00,22:00:00\"" +
                "                }]," +
                "\"pay_redirect_url\":\"https://mycar-parkingplatform.alipay-eco.com/pbizplatform/park/parking/stayPayCarList?entrance=1\"," +
                "\"forbidden_time\":{" +
                "\"days\":\"2016-03-03,2016-03-08^2016-10-01,2016-10-01\"" +
                "            }," +
                "              \"suit_shops\":[" +
                "                \"2015110600077000000002125023\",\"2015110600077000000002125022\"" +
                "              ]," +
                "\"min_consume\":\"10\"," +
                "\"ext_info\":\"\\\\\\\"key\\\\\\\":\\\\\\\"value\\\\\\\"\"," +
                "\"limit_rule\":\"USE_NO_LIMIT\"" +
                "          }," +
                "\"desc\":\"券的详细说明\"," +
                "\"display_config\":{" +
                "\"slogan\":\"券的宣传语\"," +
                "\"slogan_img\":\"1T8Pp00AT7eo9NoAJkMR3AAAACMAAQEC\"" +
                "          }," +
                "            \"clause_terms\":[{" +
                "              \"title\":\"说明title\"," +
                "                \"descriptions\":[" +
                "                  \"说明描述内容\"" +
                "                ]" +
                "              }]," +
                "\"effect_type\":\"IMMEDIATELY\"," +
                "\"delay_info\":{" +
                "\"type\":\"ABSOLUTELY\"," +
                "\"value\":\"1440\"" +
                "          }," +
                "\"ext_info\":\"\\\"key\\\":\\\"value\\\"\"," +
                "\"available_amount\":\"100\"," +
                "\"allow_split\":false" +
                "        }," +
                "\"status\":\"STARTED\"," +
                "\"voucher_no\":\"2342343245234234\"," +
                "\"point_card\":{" +
                "\"type\":\"POINT_CARD\"," +
                "\"name\":\"集点卡\"," +
                "\"desc\":\"集点卡的功能描述\"," +
                "\"start_time\":\"2016-05-01 00:00:00\"," +
                "\"end_time\":\"2016-10-01 00:00:00\"," +
                "\"logo\":\"1T8Pp00AT7eo9NoAJkMR3AAAACMAAQEC\"" +
                "        }," +
                "\"send_rule\":{" +
                "\"min_cost\":\"100\"," +
                "\"send_num\":\"1\"," +
                "\"send_budget\":\"1234\"," +
                "\"allow_repeat_send\":\"true\"" +
                "        }" +
                "        }]," +
                "      \"publish_channels\":[{" +
                "        \"type\":\"SHOP_DETAIL\"," +
                "\"name\":\"投放到店铺\"," +
                "\"config\":\"\\\"PASSWORD\\\":\\\"口令送密码\\\",\\\"BACKGROUND_LOGO\\\":\\\"1T8Pp00AT7eo9NoAJkMR3AAAACMAAQEC\\\"\"," +
                "\"ext_info\":\"\\\"key\\\":\\\"value\\\"\"" +
                "        }]," +
                "\"recruit_tool\":{" +
                "\"start_time\":\"2016-06-06 12:00:00\"," +
                "\"exclude_constraint_shops\":true," +
                "\"end_time\":\"2016-06-06 12:00:00\"," +
                "        \"pid_shops\":[{" +
                "          \"pid\":\"2088121232424234\"," +
                "            \"shop_ids\":[" +
                "              \"2015110600077000000002125023\",\"2015110600077000000002125022\"" +
                "            ]" +
                "          }]" +
                "    }," +
                "\"ext_info\":\"\\\\\\\"key\\\\\\\":\\\\\\\"value\\\\\\\"\"" +
                "  }");
        KoubeiMarketingDataIndicatorQueryResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
    }

}

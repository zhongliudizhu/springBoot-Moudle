package com.winstar.service;

import com.alibaba.fastjson.JSON;
import com.winstar.entity.AccountCoupon;
import com.winstar.repository.AccountCouponRepository;
import com.winstar.utils.SignUtil;
import com.winstar.vo.CouponVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AccountCouponService {

    AccountCouponRepository accountCouponRepository;

    RedisTemplate redisTemplate;
    @Value("${info.takeCouponUrl}")
    String takeCouponUrl;

    @Autowired
    public AccountCouponService(AccountCouponRepository accountCouponRepository, RedisTemplate redisTemplate) {
        this.accountCouponRepository = accountCouponRepository;
        this.redisTemplate = redisTemplate;
    }

    public void getRedisCoupons(String accountKeys, String accountId) {
        log.info("获取 accountId is {} 的优惠券入库", accountKeys);
        String templateKeys = "template_set";
        SetOperations setOperations = redisTemplate.opsForSet();
        Set members = setOperations.members(templateKeys);
        if (!CollectionUtils.isEmpty(members)) {
            for (Object m : members) {
                if (redisTemplate.opsForHash().hasKey(accountKeys, m)) {
                    //请求优惠券入库
                    sendCoupon(m.toString(), accountId);
                    redisTemplate.opsForHash().delete(m.toString(), accountId);
                } else {
                    setOperations.remove(templateKeys, m);
                }
            }
        }

    }

    private void sendCoupon(String templateId, String accountId) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> param = new HashMap<>();
        param.put("templateId", templateId);
        param.put("num", 1);
        ResponseEntity<Map> responseEntity = restTemplate.getForEntity(takeCouponUrl + SignUtil.getParameters(param, ""), Map.class);
        Map map = responseEntity.getBody();
        log.info("请求优惠券结果为 {}", JSON.toJSONString(map));
        if (!map.get("code").toString().equalsIgnoreCase("success")) {
            log.info("请求出错了");
            return;
        }
        getAndSaveAccountCouponInfo(JSON.toJSONString(map.get("data")), accountId);

    }

    private void getAndSaveAccountCouponInfo(String data, String accountId) {
        List<CouponVo> voList = JSON.parseArray(data, CouponVo.class);
        List<AccountCoupon> accountCoupons = new ArrayList<>();
        for (CouponVo couponVo : voList) {
            AccountCoupon accountCoupon = new AccountCoupon();
            accountCoupon.setState("normal");
            accountCoupon.setCouponId(couponVo.getId());
            accountCoupon.setAccountId(accountId);
            accountCoupon.setAmount(couponVo.getAmount());
            accountCoupon.setFullMoney(Double.valueOf(couponVo.getDoorSkill()));
            accountCoupon.setBeginTime(couponVo.getStartTime());
            accountCoupon.setEndTime(couponVo.getEndTime());
            accountCoupons.add(accountCoupon);
        }
        accountCouponRepository.save(accountCoupons);
    }

    public List<AccountCoupon> getAccountCouponFromCache(String accountId) {
        String hashKeys = "coupon_list_" + accountId;
        List<AccountCoupon> accountCouponList = new ArrayList<>();
        Map<String, Object> map = redisTemplate.opsForHash().entries(hashKeys);
        for (Map.Entry<String, Object> m : map.entrySet()) {
            AccountCoupon coupon = JSON.parseObject(m.getValue().toString(), AccountCoupon.class);
            accountCouponList.add(coupon);
        }
        return accountCouponList;
    }

    public void backSendingTimeOutCoupons(List<AccountCoupon> accountCoupons) {
        List<AccountCoupon> coupons = accountCoupons.stream().filter(s -> s.getState().equals("sending") && new Date().getTime()
                - s.getSendTime().getTime() > 60 * 60 * 24 * 1000).collect(Collectors.toList());

        coupons.forEach(coupon -> {
            coupon.setState("normal");
        });
        accountCouponRepository.save(coupons);

    }
}

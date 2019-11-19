package com.winstar.controller;

import com.winstar.common.Result;
import com.winstar.entity.AccountCoupon;
import com.winstar.repository.AccountCouponRepository;
import com.winstar.service.AccountCouponService;
import com.winstar.service.AccountService;
import com.winstar.utils.DateUtil;
import com.winstar.utils.Week;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import proguard.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/coupon")
@Slf4j
@AllArgsConstructor
public class AccountCouponController {
    private RedisTemplate redisTemplate;
    private AccountCouponRepository accountCouponRepository;
    private AccountCouponService accountCouponService;

    @RequestMapping("/getMyCoupons")
    public Result getMyCoupons(@RequestParam(defaultValue = "normal") String state, HttpServletRequest request,
                               @RequestParam(defaultValue = "0") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize) {

        log.info("入参为 state is {}", state);
        String accountId = (String) request.getAttribute("accountId");
        if (StringUtils.isEmpty(accountId)) {
            accountId = "";
        }
        String accountKeys = "coupon_list_" + accountId;
        if (!redisTemplate.hasKey(accountKeys)) {
            List<AccountCoupon> accountCoupons = accountCouponRepository.findByAccountIdAndShowStatus(accountId, "yes");
            if (!CollectionUtils.isEmpty(accountCoupons)) {
                log.info("accountCoupons is {}", accountCoupons);
                Map<String, Object> map = accountCoupons.stream().collect(Collectors.toMap(AccountCoupon::getCouponId, coupon -> coupon, (a, b) -> b));
                redisTemplate.opsForHash().putAll(accountKeys, map);
            }
        }
        accountCouponService.getRedisCoupons(accountKeys, accountId);
        List<AccountCoupon> accountCoupons = accountCouponService.getAccountCouponFromCache(accountId);
        if (CollectionUtils.isEmpty(accountCoupons)) {
            return Result.success(null);
        }
        String limitKey = "limit_user_" + accountId;
        if (redisTemplate.opsForValue().setIfAbsent(limitKey, "1")) {
            redisTemplate.expire(limitKey, 150, TimeUnit.HOURS);
            Week week = DateUtil.getWeek(new Date());
            int hour = DateUtil.getHour(new Date());
            if (!week.equals(Week.THURSDAY) || week.equals(Week.THURSDAY) && hour > 14) {
                log.info("检查赠送过期未领取的优惠券");
                accountCouponService.backSendingTimeOutCoupons(accountCoupons);
            }
            log.info("检查优惠券过期状态");
            List<AccountCoupon> timeOutCoupons = accountCoupons.stream().filter(s -> s.getState().equals("normal") && (new Date().getTime() - s.getEndTime().getTime() > 0)).collect(Collectors.toList());
            timeOutCoupons.forEach(accountCoupon -> accountCoupon.setState("expired"));
            accountCouponRepository.save(timeOutCoupons);
            redisTemplate.opsForHash().putAll(accountKeys, accountCoupons.stream().collect(Collectors.toMap(AccountCoupon::getCouponId, coupon -> coupon, (a, b) -> b)));
        }
        Pageable pageable = new PageRequest(pageNum, pageSize, Sort.Direction.DESC);
        Page page = new PageImpl(accountCoupons, pageable, accountCoupons.size());
        return Result.success(page);
    }
}
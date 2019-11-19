package com.winstar.repository;


import com.winstar.entity.AccountCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by zl on 2019/5/24
 */
public interface AccountCouponRepository extends JpaRepository<AccountCoupon, String> {


    List<AccountCoupon> findByAccountIdAndShowStatus(String accountId, String yes);
}

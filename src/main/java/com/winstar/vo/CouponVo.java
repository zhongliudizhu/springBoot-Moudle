package com.winstar.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * created by shibinbin on 2019/5/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponVo {

    /***
     *
     *主键id
     */
    private String id;

    /**
     * 优惠券编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 副标题
     */
    private String subTitle;

    /**
     * 金额
     */
    private Double amount;

    /**
     * 批次号
     */
    private String batchNumber;

    /**
     * 领取商户
     */
    private String merchant;

    /**
     * 使用规则表达式
     */
    private String useRuleExpression;

    /**
     * 显示状态（yes/no）
     */
    private String showStatus;

    /**
     * 状态  del/normal/receive/used/revoke
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 使用门槛
     */
    private Integer doorSkill;

    /**
     * 适用商品标识
     */
    private String suitItems;

    /**
     * 领取后几天可用
     */
    private Integer floatDate;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 模板ID
     */
    private String templateId;

}

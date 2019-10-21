package com.winstar.entity;


import com.winstar.common.ServiceManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stage_customer")
@Entity
public class Customer {
    /**
     * 主键
     */
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(columnDefinition = "varchar(50) COMMENT '主键id'")
    private String id;
    /**
     * 姓名
     */
    @Column(columnDefinition = "varchar(100) COMMENT '姓名'")
    private String name;

    /**
     * 身份证号
     */
    @Column(columnDefinition = "varchar(30) COMMENT '证件号码'")
    private String cardNumber;
    /**
     * 手机号
     */
    @Column(columnDefinition = "varchar(11) COMMENT '手机号'")
    private String mobile;

    /**
     * 所属二级行
     */
    @Column(columnDefinition = "varchar(50) COMMENT '二级行行号'")
    private String branchNumber;
    /**
     * 短信发送时间
     */
    @Column(columnDefinition = "datetime COMMENT '短信发送时间'")
    private Date messageSendTime;
    /**
     * 所属客户经理
     */
    @Column(columnDefinition = "varchar(50) COMMENT '用户id'")
    private String accountId;
    /**
     * 省内驾驶员 yes/no
     */
    @Column(columnDefinition = "varchar(5) COMMENT '是否省内驾驶员'")
    private String provinceDriver;
    /**
     * 备注
     */
    @Column(columnDefinition = "varchar(255) COMMENT '备注'")
    private String remark;
    /**
     * 营销结果 none/success/refuse/intention
     */
    @Column(columnDefinition = "varchar(30) COMMENT '营销结果'")
    private String marketResult;
    /**
     * 营销时间
     */
    @Column(columnDefinition = "datetime COMMENT '营销时间'")
    private Date marketTime;
    /**
     * 拒绝原因 none/loan/other
     */
    @Column(columnDefinition = "varchar(30) COMMENT '拒绝原因'")
    private String refuseReason;
    /**
     * 城市编码
     */
    @Column(columnDefinition = "varchar(50) COMMENT '城市编码'")
    private String cityNumber;
    /**
     * 排序值
     */
    @Column(columnDefinition = "integer COMMENT '排序值'")
    private Integer sortNumber;
    /**
     * 车辆品牌
     */
    @Column(columnDefinition = "varchar(50) COMMENT '车辆品牌'")
    private String plateType;
    /**
     * 挂牌时间
     */
    @Column(columnDefinition = "datetime COMMENT '挂牌时间'")
    private Date plateTime;

    /**
     * 挂牌地
     */
    @Column(columnDefinition = "varchar(255) COMMENT '挂牌地'")
    private String platePlace;
    /**
     * 信用额度
     */
    @Column(columnDefinition = "bigint COMMENT '信用额度'")
    private Long creditLimit;
    /**
     * 创建时间
     */
    @Column(columnDefinition = "datetime COMMENT '创建时间'")
    private Date createdAt;

    /**
     * 是否为交安卡客户 yes/no
     */
    @Column(columnDefinition = "varchar(6) COMMENT '是否为交安卡客户'")
    private String infoCardCustomer;


}

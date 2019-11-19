package com.winstar.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OilOrder {

    private String id;

    private String orderNo;

    private String salePrice;

    private String payMoney;

    private String discountMoney;

    private String accountId;


}

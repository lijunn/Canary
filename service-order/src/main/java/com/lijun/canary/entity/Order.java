package com.lijun.canary.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author : LiJun
 * @since : 2024-01-15 11:22
 **/
@Data
public class Order {

    private Long id;

    private String orderNumber;

    private BigDecimal total;

    private String goodsName;

    private Long uid;
}

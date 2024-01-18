package com.lijun.canary.dto.order;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author : LiJun
 * @date : 2024-01-15 14:13
 **/
@Data
public class OrderDto {

    private Long id;

    private String orderNumber;

    private BigDecimal total;

    private String goodsName;

    private Long uid;
}

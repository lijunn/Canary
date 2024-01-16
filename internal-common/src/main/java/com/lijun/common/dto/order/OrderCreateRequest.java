package com.lijun.common.dto.order;

import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;

/**
 * @author : LiJun
 * @date : 2024-01-16 11:12
 **/
@Data
public class OrderCreateRequest {

    private BigDecimal total;

    private String goodsName;

    private Long uid;
}

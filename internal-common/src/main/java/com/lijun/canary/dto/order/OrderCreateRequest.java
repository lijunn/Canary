package com.lijun.canary.dto.order;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author : LiJun
 * @date : 2024-01-16 11:12
 **/
@Data
public class OrderCreateRequest {

    @NotNull(message = "总金额不能为空")
    private BigDecimal total;

    @NotEmpty(message = "商品名称不能为空")
    private String goodsName;

    @NotNull(message = "uid 不能为空")
    private Long uid;
}

package com.lijun.order.service;

import com.lijun.common.dto.order.OrderCreateRequest;
import com.lijun.common.dto.order.OrderDto;

/**
 * @author : LiJun
 * @date : 2024-01-15 10:40
 **/
public interface OrderService {


    /**
     * 创建订单
     * @param addParam 参数
     * @return 订单编号
     */
    String createOrder(OrderCreateRequest addParam);
}

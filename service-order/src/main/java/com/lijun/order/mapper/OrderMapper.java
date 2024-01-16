package com.lijun.order.mapper;

import com.lijun.common.dto.order.OrderDto;
import com.lijun.order.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * @author : LiJun
 * @date : 2024-01-15 10:43
 **/
@Mapper
public interface OrderMapper {

    /**
     * 创建订单
     * @param order 订单
     */
    void insert(@Param("order") Order order);

    /**
     * 根据订单编号查询
     * @param orderNumber 订单编号
     * @return OrderDto
     */
    OrderDto selectByOrderNumber(String orderNumber);
}

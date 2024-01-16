package com.lijun.order.controller;

import com.lijun.common.dto.ResponseResult;
import com.lijun.common.dto.order.OrderCreateRequest;
import com.lijun.common.dto.order.OrderDto;
import com.lijun.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author : LiJun
 * @date : 2024-01-16 11:01
 **/
@RequestMapping("/orders")
@RestController
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping()
    public ResponseResult<String> addOrder(@Valid @RequestBody OrderCreateRequest request) {
        return ResponseResult.success(orderService.createOrder(request));
    }

}

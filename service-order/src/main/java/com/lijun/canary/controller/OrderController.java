package com.lijun.canary.controller;

import com.lijun.canary.constant.ResponseStatusEnum;
import com.lijun.canary.dto.ResponseResult;
import com.lijun.canary.dto.order.OrderCreateRequest;
import com.lijun.canary.exception.BusinessException;
import com.lijun.canary.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author : LiJun
 * @since : 2024-01-16 11:01
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

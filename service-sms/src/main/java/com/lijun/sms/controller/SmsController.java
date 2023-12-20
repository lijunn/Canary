package com.lijun.sms.controller;

import com.lijun.common.dto.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author : LiJun
 * @date : 2023-12-20 09:55
 **/
@RestController()
public class SmsController {


    @GetMapping("/send/{phone}")
    public ResponseResult<String> sendMsg(@PathVariable String phone) {

        return ResponseResult.success("发送成功:"+ phone);
    }
}

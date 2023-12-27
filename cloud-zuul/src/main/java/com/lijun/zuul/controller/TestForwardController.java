package com.lijun.zuul.controller;

import com.lijun.common.dto.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试 zuul forward
 * @author : LiJun
 * @date : 2023-12-26 17:25
 **/
@Slf4j
@RestController
public class TestForwardController {

    @GetMapping("/test")
    public ResponseResult<String> testForward(){
        return ResponseResult.success("testForward");
    }

}

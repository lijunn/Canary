package com.lijun.user.controller;

import cn.hutool.core.util.RandomUtil;
import com.lijun.common.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author : LiJun
 * @date : 2023-12-20 11:41
 **/
@RestController
public class TestController {

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/test")
    public ResponseResult<String> test() throws UnknownHostException {

//        String url = "http://service-sms/test"
//        restTemplate.getForObject();

        return ResponseResult.success("服务: "+ address + " random:"+random);
    }
}

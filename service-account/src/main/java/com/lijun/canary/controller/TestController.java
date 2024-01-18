package com.lijun.canary.controller;

import cn.hutool.core.util.RandomUtil;
import com.lijun.canary.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;

/**
 * 短信服务测试
 * @author : LiJun
 * @date : 2023-12-20 11:41
 **/
@RestController
public class TestController {

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/test")
    public ResponseResult<String> test() throws Exception {
        InetAddress localHost = InetAddress.getLocalHost();
        String ip = localHost.getHostAddress();
        String address = ip+":"+serverPort;

        int random = RandomUtil.getRandom().nextInt(5);

        return ResponseResult.success("短信服务: "+ address + " random:"+random);
    }
}

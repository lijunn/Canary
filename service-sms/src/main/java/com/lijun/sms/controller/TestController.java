package com.lijun.sms.controller;

import cn.hutool.core.util.RandomUtil;
import com.lijun.common.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/test")
    public ResponseResult<String> test() throws UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();
        String ip = localHost.getHostAddress();
        String address = ip+":"+serverPort;

        int random = RandomUtil.getRandom().nextInt(5);

        return ResponseResult.success("服务: "+ address + " random:"+random);
    }
}

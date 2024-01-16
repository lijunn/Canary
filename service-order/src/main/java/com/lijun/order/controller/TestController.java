package com.lijun.order.controller;

import cn.hutool.core.util.RandomUtil;
import com.lijun.common.dto.ResponseResult;
import com.lijun.common.http.MyRestTemplate;
import com.lijun.common.util.ServiceAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 用户服务测试
 * @author : LiJun
 * @date : 2023-12-20 11:41
 **/

@RestController
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
public class TestController {


    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private MyRestTemplate myRestTemplate;

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/test")
    public ResponseResult<String> test() throws UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();
        String ip = localHost.getHostAddress();
        String address = ip+":"+serverPort;

        int random = RandomUtil.getRandom().nextInt(5);

        return ResponseResult.success("用户服务: "+ address + " random:"+random);
    }

    @GetMapping("/test/sendMsg")
    public ResponseResult<String> testSendMsg() {
        //调用发送短信服务
        String url = ServiceAddress.getServiceAccountUrl("/test");

        ResponseResult<String> responseResult = myRestTemplate.sendGetRequest(url, null, new ParameterizedTypeReference<ResponseResult<String>>() {});
        return ResponseResult.success(responseResult.getData());
    }

}

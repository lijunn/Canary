package com.lijun.user.ribbon;

import com.netflix.loadbalancer.IRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 负载均衡策略配置
 *
 * @author : LiJun
 * @date : 2023-12-20 15:19
 **/
@Configuration
public class MyRibbonConfig {

    /**
     * 初始化复杂均衡策略
     */
    @Bean
    public IRule ribbonRule(){
        return new GrayRule();
    }


    /**
     * 初始化 RestTemplate
     * 并启用负载均衡
     */
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

}

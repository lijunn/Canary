package com.lijun.zuul.ribbon;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 负载均衡策略配置
 *
 * @author : LiJun
 * @date : 2023-12-20 15:19
 **/
@Configuration
public class MyRibbonConfig {

    @Bean
    public IRule ribbonRule(){
        return new GrayRule();
    }

}

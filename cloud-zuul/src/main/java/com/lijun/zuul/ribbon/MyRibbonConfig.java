package com.lijun.zuul.ribbon;

import com.lijun.canary.http.GrayServer;
import com.netflix.loadbalancer.IRule;
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

    /**
     * 初始化灰度
     */
    @Bean
    public GrayServer getGrayServer(){
        return new GrayServer();
    }

    /**
     * 初始化复杂均衡策略
     */
    @Bean
    public IRule ribbonRule(){
        MyRibbonRule myRibbonRule = new MyRibbonRule();
        myRibbonRule.setGrayServer(getGrayServer());
        return myRibbonRule;
    }

}

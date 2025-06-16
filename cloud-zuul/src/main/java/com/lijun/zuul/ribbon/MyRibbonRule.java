package com.lijun.zuul.ribbon;

import com.lijun.canary.http.GrayServer;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * 灰度发布-负载均衡
 *
 * @author : LiJun
 * @since : 2023-12-20 15:21
 **/
@Slf4j
public class MyRibbonRule extends AbstractLoadBalancerRule {

    private GrayServer grayServer;

    public void setGrayServer(GrayServer grayServer) {
        this.grayServer = grayServer;
    }

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {
    }

    @Override
    public Server choose(Object key) {
        List<Server> reachableServers = getLoadBalancer().getReachableServers();

        //灰度选择服务
        List<Server> servers = grayServer.chooseServers(reachableServers);

        //将灰度规则过滤后的服务列表进行负载均衡
        ILoadBalancer newLb = new BaseLoadBalancer();
        newLb.addServers(servers);
        RandomRule rule = new RandomRule();
        Server server = rule.choose(newLb,null);

        log.info("select-service: {}",server.getMetaInfo().getInstanceId());
        return server;
    }

}

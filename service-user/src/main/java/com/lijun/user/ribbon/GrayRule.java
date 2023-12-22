package com.lijun.user.ribbon;

import cn.hutool.json.JSONUtil;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

//import com.netflix.zuul.context.RequestContext;

/**
 * 灰度发布-负载均衡
 *
 * @author : LiJun
 * @date : 2023-12-20 15:21
 **/
@Slf4j
public class GrayRule extends AbstractLoadBalancerRule {

    /**
     * 是否开启灰度
     */
    public boolean isOpenGray = true;

    private final List<String> grayUserIds = Arrays.asList("1", "2", "3");

    private static final String GRAY_VERSION_KEY = "Gray-Version";
    private static final String GRAY_VERSION = "gray";

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {
    }

    @Override
    public Server choose(Object key) {
//        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
//        String uid = request.getHeader("uid");
//        boolean isGrayUser = grayUserIds.contains(uid);
        boolean isGrayUser = true;

        //根据灰度规则选取服务列表
        List<Server> chooseServers;
        List<Server> allServers = getLoadBalancer().getReachableServers();
        printLog("allServers",allServers);

        if (isOpenGray) {
            List<Server> grayServers = new ArrayList<>();
            List<Server> mainServers = new ArrayList<>();
            for (Server server : allServers) {
                DiscoveryEnabledServer enabledServer = (DiscoveryEnabledServer) server;
                Map<String, String> metadata = enabledServer.getInstanceInfo().getMetadata();
                String grayVersion = metadata.get(GRAY_VERSION_KEY);
                if (Objects.equals(grayVersion,GRAY_VERSION)){
                    grayServers.add(server);
                }else {
                    mainServers.add(server);
                }
            }

            chooseServers = isGrayUser ? grayServers : mainServers;
        } else {
            //不进行灰度
            chooseServers = allServers;
        }
        printLog("chooseServers",chooseServers);

        //将灰度规则过滤后的服务列表进行负载均衡
        ILoadBalancer newLb = new BaseLoadBalancer();
        newLb.addServers(chooseServers);
        RoundRobinRule robinRule = new RoundRobinRule();
        Server server = robinRule.choose(newLb,chooseServers);

        printLog("finalServer", Collections.singletonList(server));
        return server;
    }

    private void printLog(String prefix,List<Server> chooseServers) {
        List<String> chooseServersIds = chooseServers.stream().map(s -> s.getMetaInfo().getInstanceId()).collect(Collectors.toList());
        log.info(prefix+JSONUtil.toJsonPrettyStr(chooseServersIds));
    }

}

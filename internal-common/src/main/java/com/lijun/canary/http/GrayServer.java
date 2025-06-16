package com.lijun.canary.http;

import cn.hutool.json.JSONUtil;
import com.netflix.loadbalancer.Server;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 灰度发布服务
 * @author : LiJun
 * @since : 2023-12-25 11:19
 **/
@Slf4j
public class GrayServer {

    /**
     * eureka metadata 配置中灰度-key
     */
    private static final String GRAY_VERSION_KEY = "Gray-Version";
    /**
     * eureka metadata 配置灰度-value
     */
    private static final String GRAY_VERSION = "gray";
    /**
     * 灰度用户规则
     */
    private final List<Integer> grayUserIds = Arrays.asList(1,2,3);


    /**
     * 根据灰度规则选择服务
     * @param allServers 所有服务
     * @return List<Server> 选择服务
     */
    public List<Server> chooseServers(List<Server> allServers) {
        //从请求Header中获取用户ID
        int uid = 0;
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            String uidStr = requestAttributes.getRequest().getHeader("uid");
            if (uidStr != null){
                uid = Integer.parseInt(uidStr);
            }
        }
        boolean isGrayUser = grayUserIds.contains(uid);

        //根据灰度规则选取服务列表
        printLog("allServers",allServers);
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

        if (isGrayUser){
            printLog("choose-grayServers",grayServers);
            return grayServers;
        }else {
            printLog("choose-mainServers",mainServers);
            return mainServers;
        }
    }

    private void printLog(String prefix,List<Server> chooseServers) {
        List<String> chooseServersIds = chooseServers.stream().map(s -> s.getMetaInfo().getInstanceId()).collect(Collectors.toList());
        log.info(prefix+ JSONUtil.toJsonPrettyStr(chooseServersIds));
    }
}

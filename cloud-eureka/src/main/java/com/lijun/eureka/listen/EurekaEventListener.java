package com.lijun.eureka.listen;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceCanceledEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaServerStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Eureka 事件监听器
 * @author : LiJun
 * @date : 2023-12-19 11:55
 **/
@Slf4j
@Component
public class EurekaEventListener {

    @EventListener
    public void cancelEvent(EurekaInstanceCanceledEvent event){
        // 实例取消事件，发送短信、通知 运维人员
        log.info("下线：{}",event.getServerId());

    }

    @EventListener
    public void serverStartedEvent(EurekaServerStartedEvent event){
        log.info("服务启动：{}",event.getTimestamp());
    }
}

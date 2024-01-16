package com.lijun.account.listener;

import cn.hutool.json.JSONUtil;
import com.lijun.account.mapper.MemberPointsMapper;
import com.lijun.common.constant.RocketMqConstant;
import com.lijun.common.dto.order.OrderDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author : LiJun
 * @date : 2024-01-15 14:03
 **/
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = RocketMqConstant.TOPIC_NAME_ORDER_CREATED, topic = RocketMqConstant.TOPIC_NAME_ORDER_CREATED)
public class OrderCreatedListener implements RocketMQListener<MessageExt> {

    @Resource
    private MemberPointsMapper memberPointsMapper;

    @Override
    public void onMessage(MessageExt message) {
        OrderDto orderDto = JSONUtil.toBean(new String(message.getBody()), OrderDto.class);
        log.info("orderDto: {}",orderDto.toString());

        if (!memberPointsMapper.existByUid(orderDto.getUid())){
            throw new RuntimeException("积分记录不存在");
        }
        memberPointsMapper.updateByUid(orderDto.getUid(), 100d);
    }
}

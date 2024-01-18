package com.lijun.canary.listener;

import cn.hutool.json.JSONUtil;
import com.lijun.canary.entity.MemberPoints;
import com.lijun.canary.mapper.MemberPointsMapper;
import com.lijun.canary.constant.RocketMqConstant;
import com.lijun.canary.dto.order.OrderDto;
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

        MemberPoints memberPoints = memberPointsMapper.selectByUid(orderDto.getUid());
        if (memberPoints == null){
            throw new RuntimeException("积分记录不存在");
        }
        Double amount = memberPoints.getAmount();

        memberPointsMapper.updateByUid(orderDto.getUid(), amount+100d);
    }
}

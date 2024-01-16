package com.lijun.order.service.impl;

import com.lijun.common.constant.RocketMqConstant;
import com.lijun.common.dto.order.OrderCreateRequest;
import com.lijun.common.dto.order.OrderDto;
import com.lijun.order.entity.Order;
import com.lijun.order.mapper.OrderMapper;
import com.lijun.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionContextManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author : LiJun
 * @date : 2024-01-15 10:41
 **/
@Slf4j
@Service
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Autowired
    private RocketMQTemplate mqTemplate;

    @Autowired
    private PlatformTransactionManager transactionManager;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public String createOrder(OrderCreateRequest addParam) {
        if (addParam.getTotal().equals(BigDecimal.ZERO)){
            throw new RuntimeException("订单金额需大于0");
        }

        String orderNumber = generateOrderNumber();
        Order order = new Order();
        order.setUid(addParam.getUid());
        order.setTotal(addParam.getTotal());
        order.setGoodsName(addParam.getGoodsName());
        order.setOrderNumber(orderNumber);

        //事务消息监听器
        TransactionMQProducer producer = (TransactionMQProducer)mqTemplate.getProducer();
        producer.setTransactionListener(new TransactionListener(){

            @Override
            public LocalTransactionState executeLocalTransaction(org.apache.rocketmq.common.message.Message msg, Object arg) {
                //执行本地事务
                TransactionStatus transaction = transactionManager.getTransaction(new DefaultTransactionDefinition());
                try {
                    orderMapper.insert(order);
                    transactionManager.commit(transaction);

                    return LocalTransactionState.COMMIT_MESSAGE;
                }catch (Exception e){
                    log.error("订单创建失败：{}",e.getMessage());
                    transactionManager.rollback(transaction);

                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }
            }

            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                try {
                    OrderDto orderDto = orderMapper.selectByOrderNumber(orderNumber);
                    return orderDto == null ? LocalTransactionState.ROLLBACK_MESSAGE : LocalTransactionState.COMMIT_MESSAGE;
                }catch (Exception e){
                    log.error("订单创建事务消息回查失败：{}",e.getMessage());
                    return LocalTransactionState.UNKNOW;
                }
            }
        });
        //发送事务消息
        OrderDto orderMsg = new OrderDto();
        orderMsg.setUid(order.getUid());
        orderMsg.setOrderNumber(orderNumber);
        Message<OrderDto> message = MessageBuilder.withPayload(orderMsg).build();
        mqTemplate.sendMessageInTransaction(RocketMqConstant.TOPIC_NAME_ORDER_CREATED, message, null);

        return orderNumber;
    }

    private String generateOrderNumber() {
        // 这里以当前时间和随机数结合作为订单号

        // 1. 生成时间戳部分
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = dateFormat.format(new Date());

        // 2. 生成随机数部分
        Random random = new Random();
        String randomStr = String.format("%04d", random.nextInt(10000));

        // 3. 组合生成订单号
        return "ORD" + timestamp + randomStr;
    }

}

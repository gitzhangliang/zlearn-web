//package com.tzxx.common.rabbitmq;
//
//import com.google.gson.Gson;
//import com.rabbitmq.client.Channel;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//
//
//@Component
//public class RabbitReceiver {
//
//
//    @RabbitHandler
//    @RabbitListener(queues = RabbitSenderConfig.PROJECT_STRUCTURE_QUEUE_NAME)
//    public void greetecReceive(Message content, Channel channel) throws IOException {
//        try {
//            ...
//            channel.basicAck(content.getMessageProperties().getDeliveryTag(), true);
//
//        }
//        catch (Exception e) {
//            extracted(content, channel, e);
//        }
//    }
//
//    private void extracted(Message content, Channel channel, Exception e) throws IOException {
//        logger.error("消息队列异常", e);
//        if (Boolean.TRUE.equals(content.getMessageProperties().getRedelivered())) {
//            logger.info("消息已重复处理失败,拒绝再次接收");
//            // 拒绝消息，requeue=false 表示不再重新入队，如果配置了死信队列则进入死信队列
//            channel.basicReject(content.getMessageProperties().getDeliveryTag(), false);
//        } else {
//            logger.info("消息即将再次返回队列处理");
//            // requeue为是否重新回到队列，true重新入队
//            channel.basicNack(content.getMessageProperties().getDeliveryTag(), false, true);
//        }
//    }
//}

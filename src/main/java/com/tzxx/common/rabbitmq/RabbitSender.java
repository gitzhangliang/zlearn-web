//package com.tzxx.common.rabbitmq;
//
//import com.google.gson.Gson;
//import com.tzxx.common.constant.NetworkConstant;
//import com.tzxx.system.lenovo.context.RabbitContext;
//import org.apache.commons.lang3.SerializationUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.core.MessagePostProcessor;
//import org.springframework.amqp.rabbit.connection.CorrelationData;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
//import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.util.UUID;
//
//
//@Component
//public class RabbitSender implements ConfirmCallback, ReturnCallback {
//
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//    private final Gson gson = new Gson();
//    private final String x_ha_policy="x-ha-policy";
//
//    @PostConstruct
//    public void init() {
//        rabbitTemplate.setConfirmCallback(this);
//        rabbitTemplate.setReturnCallback(this);
//    }
//
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    public <T> void greetecSend(RabbitContext<T> context, String syncType) {
//        logger.debug("推送 mq : {}", context);
//        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
//        MessagePostProcessor messagePost = message -> {
//            message.getMessageProperties().setHeader(x_ha_policy, "all");
//            message.getMessageProperties().setHeader(NetworkConstant.SYNC_TYPE, syncType);
//            return message;
//        };
//
//        String message = gson.toJson(context);
//        rabbitTemplate.convertAndSend(RabbitSenderConfig.EXCHANGE_NAME, RabbitSenderConfig.PROJECT_STRUCTURE_QUEUE_KEY, message, messagePost, correlationId);
//    }
//
//    public void sendExchangerApprover(String context) {
//        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
//        MessagePostProcessor messagePost = message-> {
//            message.getMessageProperties().setHeader(x_ha_policy, "all");
//            return message;
//        };
//        rabbitTemplate.convertAndSend(RabbitSenderConfig.EXCHANGE_NAME,RabbitSenderConfig.EXCHANGER_SPLIT_GROUP_LEADER_ROUTING_KEY,context,messagePost,correlationId);
//    }
//
//
//
//    @Override
//    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
//        String mesBody = SerializationUtils.deserialize(message.getBody());
//        String mesBodyFormat = String.format("消息主体: %s", mesBody);
//        String replyTextFormat = String.format("描述: %s", replyText);
//        String exchangeFormat = String.format("消息使用的交换器: %s", exchange);
//        String routingKeyFormat = String.format("消息使用的路由键: %s", routingKey);
//        String replyCodeFormat = String.format("应答码: %d", replyCode);
//
//        logger.info(mesBodyFormat);
//        logger.info(replyCodeFormat);
//        logger.info(replyTextFormat);
//        logger.info(exchangeFormat);
//        logger.info(routingKeyFormat);
//    }
//
//    @Override
//    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//        if (ack) {
//            logger.debug("RabbitSender send success : {}", correlationData);
//        } else {
//            logger.debug("RabbitSender send failed : {}", cause);
//
//        }
//    }
//}

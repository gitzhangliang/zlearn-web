//package com.tzxx.common.rabbitmq;
//
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.TopicExchange;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
//@Configuration
//public class RabbitSenderConfig {
//
//
//    @Bean("exchangerGroupLeaderQueue")
//    public Queue queue() {
//        return new Queue("EXCHANGER_GROUP_LEADER", true);
//    }
//
//
//    @Bean
//    public Binding exchangerGroupLeaderExchange(@Qualifier("exchangerGroupLeaderQueue") Queue ecSoQueue, @Qualifier("topicExchange") TopicExchange topicExchange) {
//        return BindingBuilder.bind(ecSoQueue).to(topicExchange).with(EXCHANGER_GROUP_LEADER_ROUTING_KEY);
//    }
//
//    @Bean("topicExchange")
//    public TopicExchange topicExchange() {
//        return new TopicExchange(EXCHANGE_NAME);
//    }
//}
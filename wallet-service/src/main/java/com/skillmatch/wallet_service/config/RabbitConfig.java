
package com.skillmatch.wallet_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Map;

@Configuration
public class RabbitConfig {

    public static final String WALLET_QUEUE = "contract-paid-queue";
    public static final String WALLET_EXCHANGE = "wallet-exchange";
    public static final String WALLET_ROUTING_KEY = "contract.paid";

    @Bean
    public Queue walletQueue() {
        return new Queue(WALLET_QUEUE, true);
    }

    @Bean
    public TopicExchange walletExchange() {
        return new TopicExchange(WALLET_EXCHANGE);
    }

    @Bean
    public Binding walletBinding() {
        return BindingBuilder
                .bind(walletQueue())
                .to(walletExchange())
                .with(WALLET_ROUTING_KEY);
    }

    @Bean
    public DefaultClassMapper classMapper() {
        DefaultClassMapper mapper = new DefaultClassMapper();

        mapper.setIdClassMapping(Map.of(
                "com.skillmatch.events.ContractPaidEvent",
                com.skillmatch.events.ContractPaidEvent.class
        ));

        return mapper;
    }

    @Bean
    public MessageConverter messageConverter(DefaultClassMapper classMapper) {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setClassMapper(classMapper);
        return converter;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         MessageConverter messageConverter) {

        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }
}

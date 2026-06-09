
package com.skillmatch.contract_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    // Exchange per il wallet
    public static final String WALLET_EXCHANGE = "wallet-exchange";
    public static final String WALLET_ROUTING_KEY = "contract.paid";

    @Bean
    public TopicExchange walletExchange() {
        return new TopicExchange(WALLET_EXCHANGE);
    }

    // QUEUE
    @Bean
    public Queue walletQueue() {
        return new Queue("contract-paid-queue", true);
    }

    // BINDING
    @Bean
    public Binding walletBinding() {
        return BindingBuilder
                .bind(walletQueue())
                .to(walletExchange())
                .with(WALLET_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory,
            MessageConverter converter) {

        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter);
        return template;
    }
}
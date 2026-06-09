
package com.skillmatch.matching_service.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String MATCH_EXCHANGE = "match.exchange";
    public static final String MATCH_QUEUE = "match.events.queue";
    public static final String MATCH_ROUTING_KEY = "match.event";

    @Bean
    public TopicExchange matchExchange() {
        return new TopicExchange(MATCH_EXCHANGE);
    }

    @Bean
    public Queue matchQueue() {
        return new Queue(MATCH_QUEUE, true);
    }

    @Bean
    public Binding bindMatchQueue() {
        return BindingBuilder
                .bind(matchQueue())
                .to(matchExchange())
                .with(MATCH_ROUTING_KEY);
    }
}


package com.skillmatch.project_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String CONTRACT_EXCHANGE = "contract.exchange";
    public static final String CONTRACT_EVENT_QUEUE = "contract.events.project.queue";
    public static final String CONTRACT_EVENT_ROUTING_KEY = "contract.event";

    @Bean
    public TopicExchange contractExchange() {
        return new TopicExchange(CONTRACT_EXCHANGE);
    }

    @Bean
    public Queue contractProjectQueue() {
        // coda dedicata al project-service
        return new Queue(CONTRACT_EVENT_QUEUE, true);
    }

    @Bean
    public Binding contractProjectBinding(Queue contractProjectQueue,
                                          TopicExchange contractExchange) {
        return BindingBuilder
                .bind(contractProjectQueue)
                .to(contractExchange)
                .with(CONTRACT_EVENT_ROUTING_KEY);
    }

    @Bean
    public Queue matchQueue() {
        return new Queue("match.events.queue", true); // deve coincidere col matching-service
    }

    @Bean
    public TopicExchange matchExchange() {
        return new TopicExchange("match.exchange");
    }

    @Bean
    public Binding matchBinding() {
        return BindingBuilder.bind(matchQueue())
                .to(matchExchange())
                .with("match.event"); // anche questo deve coincidere
    }


}
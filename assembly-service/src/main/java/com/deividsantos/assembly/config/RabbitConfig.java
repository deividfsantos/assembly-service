package com.deividsantos.assembly.config;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Value("${rabbit.exchange.session-results}")
    private String exchange;

    @Bean
    public Exchange accountChargeDeadLetterExchange() {
        return ExchangeBuilder.topicExchange(exchange).build();
    }

}

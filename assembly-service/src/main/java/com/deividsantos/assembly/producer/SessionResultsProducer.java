package com.deividsantos.assembly.producer;

import com.deividsantos.assembly.exception.AgendaEventException;
import com.deividsantos.assembly.producer.event.AgendaResultEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SessionResultsProducer {
    private static final Logger logger = LoggerFactory.getLogger(SessionResultsProducer.class);

    private final String exchange;
    private final String routingKey;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public SessionResultsProducer(@Value("${rabbit.exchange.session-results}") String exchange,
                                  @Value("${rabbit.routing-key.session-results}") String routingKey,
                                  RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.exchange = exchange;
        this.routingKey = routingKey;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void produceEvent(AgendaResultEvent agendaResultEvent) {
        rabbitTemplate.convertSendAndReceive(exchange, routingKey, buildMessage(agendaResultEvent));
        logger.info("Session results event from agenda {} sended with success.", agendaResultEvent.getAgendaId());
    }

    private Message buildMessage(AgendaResultEvent agendaResultEvent) {
        try {
            return MessageBuilder.withBody(objectMapper.writeValueAsString(agendaResultEvent).getBytes())
                    .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                    .build();
        } catch (JsonProcessingException e) {
            logger.error("Error when mapping event message from agenda {}.", agendaResultEvent.getAgendaId());
            throw new AgendaEventException();
        }
    }
}

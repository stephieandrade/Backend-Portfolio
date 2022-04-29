package com.digitalFlix.seriesService.config.queue;

import com.digitalFlix.seriesService.config.RabbitMQSender;
import com.digitalFlix.seriesService.model.Serie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SeriesSender {

    private final RabbitTemplate template;

    @RabbitListener(queues = "${queue.serie.name}")
    public void send(@Payload Serie serie){
        template.convertAndSend(RabbitMQSender.EXCHANGE, RabbitMQSender.ROUTING_KEY, serie);
    }
}

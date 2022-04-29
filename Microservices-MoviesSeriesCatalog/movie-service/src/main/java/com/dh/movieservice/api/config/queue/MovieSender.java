package com.dh.movieservice.api.config.queue;

import com.dh.movieservice.api.config.RabbitMQSender;
import com.dh.movieservice.domain.model.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MovieSender {

    private final RabbitTemplate template;

    @RabbitListener(queues = { "${queue.movie.name}" })
    public void send(@Payload Movie movie) {
        template.convertAndSend(RabbitMQSender.EXCHANGE, RabbitMQSender.ROUTING_KEY, movie);
    }
}

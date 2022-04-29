package com.digitalFlix.seriesService.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Configuration
public class RabbitMQSender {

    @Value ("${queue.serie.name}")
    private String series;
    public static final String EXCHANGE = "queue2Exchange";
    public static final String ROUTING_KEY = "queue2RoutingKey";

    /** Constructor de la queue **/
    @Bean
    public Queue queue() {
        return new Queue(this.series, true);
    }

    /** Constructor del Exchange de la queue **/
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(EXCHANGE);
    }

    /** Binding de la Queue con su Exchange para poder publicar
     * elementos, guiandose por la routing key */
    @Bean
    public Binding binding(Queue queue, TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    /** Convertidor de elementos publicados a json **/
    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**generacion del template de Rabbit**/
    @Bean
    public RabbitTemplate template(ConnectionFactory connectionFactory){
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }





}

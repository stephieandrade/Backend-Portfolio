package com.dh.movieservice.api.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQSender {

    @Value ("${queue.movie.name}")
    private String movie;
    public static final String EXCHANGE = "queue1Exchange";
    public static final String ROUTING_KEY = "queue1RoutingKey";

    /** Constructor de la Queue MovieQueue **/
    @Bean
    public Queue queue() {
        return new Queue(this.movie, true);
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




//    public void send(String message1, Movie movie) {
//        return new exchange, routingkey, message1, (MessagePostProcessor) movie);
//    }
//    public void send(Movie movie){  //defines the send() method that calls the convertAndSend() method of the RabbitTemplate class and sets exchange routing user to it. This convertAndSend() method then pushes the message to exchange with the specified routingkey.
//        rabbitTemplate.convertAndSend(exchange,routingkey, movie);
//    }

}

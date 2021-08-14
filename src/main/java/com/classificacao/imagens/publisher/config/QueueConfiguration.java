package com.classificacao.imagens.publisher.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.classificacao.imagens.publisher.config.AMQPConstants.PROCESSAR_IMAGENS_QUEUE;

@Configuration
public class QueueConfiguration {

    private static final String EXCHANGE_NAME = "IMAGENS_EXCHANGE";

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding processarImagem(DirectExchange exchange) {
        return BindingBuilder.bind(processar())
                .to(exchange)
                .with(PROCESSAR_IMAGENS_QUEUE);
    }

    @Bean
    public Queue processar() {
        return new Queue(PROCESSAR_IMAGENS_QUEUE, true, false, false);
    }
}

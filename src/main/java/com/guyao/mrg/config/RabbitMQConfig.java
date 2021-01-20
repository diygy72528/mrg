package com.guyao.mrg.config;

import com.guyao.mrg.common.base.MrGConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author guyao
 * @date 2020/5/20 4:29 下午
 */
@Configuration
public class RabbitMQConfig {



    @Bean
    public Queue dictUpdateQueue() {
        return new Queue(MrGConstant.RABBIT_DICT_UPDATE, true);
    }

    @Bean
    public Queue dictDeleteQueue() {
        return new Queue(MrGConstant.RABBIT_DICT_DELETE, true);
    }

    @Bean
    public DirectExchange dictExchange() {
        return new DirectExchange(MrGConstant.RABBIT_DICT_EXCHANGE,true,false);
    }

    @Bean
    public Binding dictUpdateQueueBinding() {
        return BindingBuilder.bind(dictUpdateQueue()).to(dictExchange()).with(MrGConstant.RABBIT_DICT_UPDATE);
    }

    @Bean
    public Binding dictDeleteQueueBinding() {
        return BindingBuilder.bind(dictDeleteQueue()).to(dictExchange()).with(MrGConstant.RABBIT_DICT_DELETE);
    }

    @Bean
    public RabbitListenerErrorHandler dictErrorHandler() {
        return (amqpMessage, message, exception) -> {
            return "字典缓存修改异常";
        };
    }
}

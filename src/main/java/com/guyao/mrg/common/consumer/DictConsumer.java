package com.guyao.mrg.common.consumer;

import com.guyao.mrg.common.base.MrGConstant;
import com.guyao.mrg.common.cache.DictCache;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author guyao
 * @date 2020/5/20 4:37 下午
 */
@Component
public class DictConsumer {

    @Autowired
    private DictCache cache;


    @RabbitListener(queues = MrGConstant.RABBIT_DICT_UPDATE, errorHandler = "dictErrorHandler", returnExceptions = "true")
    public void dictUpdated(Message message, Channel channel, @Headers Map headers, @Payload Object load) {
        if(true) {
            throw new RuntimeException("异常测试");
        }
        cache.typeChanged(new String(message.getBody()));
    }

    @RabbitListener(queues = MrGConstant.RABBIT_DICT_DELETE)
    public void dictDeleted(Message message, Channel channel) {
        cache.typeDeleted(new String(message.getBody()));
    }
}

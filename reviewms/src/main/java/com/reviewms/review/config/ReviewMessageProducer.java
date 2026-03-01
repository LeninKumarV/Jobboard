package com.reviewms.review.config;

import com.reviewms.review.ReviewVO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewMessageProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(ReviewVO reviewVO){
        rabbitTemplate.convertSendAndReceive(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                reviewVO
        );
    }

}

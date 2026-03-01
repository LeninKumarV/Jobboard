package com.companyms.company.config;

import com.companyms.company.impl.CompanyServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewEventListener {

    private final CompanyServiceImpl companyService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void handleReviews(
            ReviewVO reviewVO
    ) throws IOException {
        try {
            System.out.println("Received event: " + reviewVO);
            companyService.updateCompanyReview(reviewVO);
        } catch (Exception ex) {
            log.info("AMQP config could not be sent", ex);
        }
    }
}

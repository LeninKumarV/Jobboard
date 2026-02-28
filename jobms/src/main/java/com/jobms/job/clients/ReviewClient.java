// ReviewClient.java
package com.jobms.job.clients;

import com.jobms.job.models.ReviewVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "REVIEW-SERVICE")
public interface ReviewClient {

    @GetMapping("/reviews/get/review")
    List<ReviewVO> getReviews(@RequestParam UUID companyId);
}
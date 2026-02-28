package com.reviewms.review;

import java.util.List;
import java.util.UUID;

public interface ReviewService {

    List<ReviewVO> getAllReviews(UUID companyId);

    boolean addReview(UUID companyId, ReviewVO reviewVO);

    ReviewVO getReview(UUID reviewId);

    boolean updateReview(UUID reviewId, ReviewVO reviewVO);

    boolean deleteReview(UUID reviewId);
}
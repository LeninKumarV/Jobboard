package com.reviewms.review.impl;

import com.reviewms.review.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public List<ReviewVO> getAllReviews(UUID companyId) {

        return reviewRepository.findByCompanyId(companyId)
                .stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean addReview(UUID companyId, ReviewVO reviewVO) {

        if (companyId != null && reviewVO != null) {

            Review review = convertToEntity(reviewVO);
            review.setCompanyId(companyId);

            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    @Override
    public ReviewVO getReview(UUID reviewId) {

        return reviewRepository.findById(reviewId)
                .map(this::convertToVO)
                .orElse(null);
    }

    @Override
    public boolean updateReview(UUID reviewId, ReviewVO reviewVO) {

        Review review = reviewRepository.findById(reviewId).orElse(null);

        if (review != null) {

            review.setTitle(reviewVO.getTitle());
            review.setDescription(reviewVO.getDescription());
            review.setRating(reviewVO.getRating());
            review.setCompanyId(reviewVO.getCompanyId());

            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteReview(UUID reviewId) {

        if (reviewRepository.existsById(reviewId)) {
            reviewRepository.deleteById(reviewId);
            return true;
        }
        return false;
    }


    private ReviewVO convertToVO(Review review) {
        if (review == null) return null;

        return ReviewVO.builder()
                .id(review.getId())
                .title(review.getTitle())
                .description(review.getDescription())
                .rating(review.getRating())
                .companyId(review.getCompanyId())
                .build();
    }

    private Review convertToEntity(ReviewVO vo) {
        if (vo == null) return null;

        return Review.builder()
                .id(vo.getId())
                .title(vo.getTitle())
                .description(vo.getDescription())
                .rating(vo.getRating())
                .companyId(vo.getCompanyId())
                .build();
    }
}
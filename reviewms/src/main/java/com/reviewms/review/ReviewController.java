package com.reviewms.review;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/get/review")
    public ResponseEntity<List<ReviewVO>> getAllReviews(@RequestParam UUID companyId) {

        return new ResponseEntity<>(
                reviewService.getAllReviews(companyId),
                HttpStatus.OK);
    }

    @PostMapping("/post/review")
    public ResponseEntity<String> addReview(@RequestParam UUID companyId,
                                            @RequestBody ReviewVO reviewVO) {

        boolean isSaved = reviewService.addReview(companyId, reviewVO);

        if (isSaved)
            return new ResponseEntity<>("Review Added Successfully", HttpStatus.CREATED);

        return new ResponseEntity<>("Review Not Saved", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewVO> getReview(@PathVariable UUID reviewId) {

        ReviewVO review = reviewService.getReview(reviewId);

        if (review != null)
            return new ResponseEntity<>(review, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable UUID reviewId,
                                               @RequestBody ReviewVO reviewVO) {

        boolean updated = reviewService.updateReview(reviewId, reviewVO);

        if (updated)
            return new ResponseEntity<>("Review updated successfully", HttpStatus.OK);

        return new ResponseEntity<>("Review not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable UUID reviewId) {

        boolean deleted = reviewService.deleteReview(reviewId);

        if (deleted)
            return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);

        return new ResponseEntity<>("Review not found", HttpStatus.NOT_FOUND);
    }
}
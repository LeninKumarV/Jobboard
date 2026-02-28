package com.reviewms.review;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewVO {

    private UUID id;
    private String title;
    private String description;
    private double rating;
    private UUID companyId;
}
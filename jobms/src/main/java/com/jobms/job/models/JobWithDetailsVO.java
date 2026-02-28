package com.jobms.job.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class JobWithDetailsVO {
    private UUID id;
    private String title;
    private String description;
    private Double minSalary;
    private Double maxSalary;
    private String location;

    private CompanyVO company;
    private List<ReviewVO> reviews;
}
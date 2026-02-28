package com.jobms.job;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobVO {

    private UUID id;
    private String title;
    private String description;
    private Double minSalary;
    private Double maxSalary;
    private String location;
    private UUID companyId;
}
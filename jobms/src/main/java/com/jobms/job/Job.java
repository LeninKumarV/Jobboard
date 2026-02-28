package com.jobms.job;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity(name = "job")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Job {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private String title;
    private String description;

    private Double minSalary;
    private Double maxSalary;

    private String location;

    private UUID companyId;
}
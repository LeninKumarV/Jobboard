package com.jobms.job.impl;

import com.jobms.job.Job;
import com.jobms.job.JobRepository;
import com.jobms.job.JobService;
import com.jobms.job.JobVO;
import com.jobms.job.clients.CompanyClient;
import com.jobms.job.clients.ReviewClient;
import com.jobms.job.models.CompanyVO;
import com.jobms.job.models.JobWithDetailsVO;
import com.jobms.job.models.ReviewVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final CompanyClient companyClient;
    private final ReviewClient reviewClient;

    @Override
    public List<JobWithDetailsVO> findAll() {
        return jobRepository.findAll()
                .stream()
                .map(this::enrichJob)
                .collect(Collectors.toList());
    }

    @Override
    public JobWithDetailsVO getJobById(UUID id) {
        return jobRepository.findById(id)
                .map(this::enrichJob)
                .orElse(null);
    }

    private JobWithDetailsVO enrichJob(Job job) {

        CompanyVO company = null;
        List<ReviewVO> reviews = List.of();

        try {
            company = companyClient.getCompany(job.getCompanyId());
        } catch (Exception e) {
            log.info("Could not get reviews for companyId {}: {}", job.getCompanyId(), e.getMessage());
        }

        try {
            reviews = reviewClient.getReviews(job.getCompanyId());
        } catch (Exception e) {
            log.info("Could not get reviews for: {}", e.getMessage());
        }

        return JobWithDetailsVO.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .minSalary(job.getMinSalary())
                .maxSalary(job.getMaxSalary())
                .location(job.getLocation())
                .company(company)
                .reviews(reviews)
                .build();
    }

    @Override
    public void createJob(JobVO jobVO) {
        jobRepository.save(convertToEntity(jobVO));
    }

    @Override
    public boolean deleteJobById(UUID id) {
        if (jobRepository.existsById(id)) {
            jobRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateJob(UUID id, JobVO jobVO) {
        Optional<Job> optionalJob = jobRepository.findById(id);
        if (optionalJob.isPresent()) {
            Job job = optionalJob.get();
            job.setTitle(jobVO.getTitle());
            job.setDescription(jobVO.getDescription());
            job.setMinSalary(jobVO.getMinSalary());
            job.setMaxSalary(jobVO.getMaxSalary());
            job.setLocation(jobVO.getLocation());
            job.setCompanyId(jobVO.getCompanyId());
            jobRepository.save(job);
            return true;
        }
        return false;
    }

    private Job convertToEntity(JobVO vo) {
        if (vo == null) return null;
        return Job.builder()
                .id(vo.getId())
                .title(vo.getTitle())
                .description(vo.getDescription())
                .minSalary(vo.getMinSalary())
                .maxSalary(vo.getMaxSalary())
                .location(vo.getLocation())
                .companyId(vo.getCompanyId())
                .build();
    }
}
package com.jobms.job;

import com.jobms.job.models.JobWithDetailsVO;

import java.util.List;
import java.util.UUID;

public interface JobService {

    List<JobWithDetailsVO> findAll();

    void createJob(JobVO jobVO);

    JobWithDetailsVO getJobById(UUID id);

    boolean deleteJobById(UUID id);

    boolean updateJob(UUID id, JobVO jobVO);
}
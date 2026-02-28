package com.jobms.job;

import com.jobms.job.models.JobWithDetailsVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @GetMapping("all/jobs")
    public ResponseEntity<List<JobWithDetailsVO>> getAllJobs() {
        return new ResponseEntity<>(jobService.findAll(), HttpStatus.OK);
    }

    @PostMapping("save/job")
    public ResponseEntity<String> createJob(@RequestBody JobVO jobVO) {
        jobService.createJob(jobVO);
        return new ResponseEntity<>("Job added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobWithDetailsVO> getJobById(@PathVariable UUID id) {
        JobWithDetailsVO job = jobService.getJobById(id);
        if (job != null)
            return new ResponseEntity<>(job, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable UUID id) {

        boolean deleted = jobService.deleteJobById(id);

        if (deleted)
            return new ResponseEntity<>("Job deleted successfully", HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateJob(@PathVariable UUID id,
                                            @RequestBody JobVO jobVO) {

        boolean updated = jobService.updateJob(id, jobVO);

        if (updated)
            return new ResponseEntity<>("Job updated successfully", HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
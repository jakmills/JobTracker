package com.jake.jobtrack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jake.jobtrack.model.Job;
import com.jake.jobtrack.service.JobService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping("/{email}")
    public ResponseEntity<List<Job>> getJobsByUserId(@PathVariable String email) {
        List<Job> jobs = jobService.getJobsByUserEmail(email);
        return ResponseEntity.ok(jobs);
    }

}

package com.jake.jobtrack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jake.jobtrack.model.Job;
import com.jake.jobtrack.service.JobService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobService jobService;


    /* CRUD Endpoints */

    @GetMapping("/{email}")
    public ResponseEntity<List<Job>> getJobsByUserId(@PathVariable String email) {
        List<Job> jobs = jobService.getJobsByUserEmail(email);
        return ResponseEntity.ok(jobs);
    }

    @PostMapping("/")
    public ResponseEntity<Job> addJob(@RequestBody Job job) {
        try {
            Job newJob = jobService.saveJob(job);
            return ResponseEntity.ok(newJob);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }    

    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable Long id, @RequestBody Job job) {
        Job existingJob = jobService.removeJob(id);
        if (existingJob == null) {
            return ResponseEntity.notFound().build();
        }
        job.setId(id);
        Job updatedJob = jobService.saveJob(job);
        return ResponseEntity.ok(updatedJob);   
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Job> removeJob(@PathVariable Long id) {
        Job job = jobService.removeJob(id);
        return ResponseEntity.ok(job);
    }


    /* Analytics Endpoints */

    @GetMapping("/analytics/total/{email}")
    public ResponseEntity<Integer> getTotalJobs(@PathVariable String email) {
        int total = jobService.getTotalJobsByUserEmail(email);
        return ResponseEntity.ok(total);
    }

    @GetMapping("/analytics/status/{email}/{status}")
    public ResponseEntity<Integer> getJobsByStatus(@PathVariable String email, @PathVariable String status) {
        int count = jobService.getJobsByStatus(email, status);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/analytics/company/{email}/{company}")
    public ResponseEntity<Integer> getJobsByCompany(@PathVariable String email, @PathVariable String company) {
        int count = jobService.getJobsByCompany(email, company);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/analytics/monthly/{email}")
    public ResponseEntity<Integer> getApplicationsThisMonth(@PathVariable String email) {
        int count = jobService.getApplicationsThisMonth(email);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/analytics/weekly/{email}")
    public ResponseEntity<Integer> getApplicationsThisWeek(@PathVariable String email) {
        int count = jobService.getApplicationsThisWeek(email);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/analytics/monthly/average/{email}")
    public ResponseEntity<Integer> getAverageApplicationsPerMonth(@PathVariable String email) {
        int average = jobService.getAverageApplicationsPerMonth(email);
        return ResponseEntity.ok(average);
    }

}

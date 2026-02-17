package com.jake.jobtrack.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jake.jobtrack.model.Job;
import com.jake.jobtrack.repo.JobRepo;

@Service
public class JobService {

    @Autowired
    private JobRepo jobRepo;


    public List<Job> getJobsByUserEmail(String email) {
        return jobRepo.findByUserEmail(email);
    }

    


}

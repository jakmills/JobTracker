package com.jake.jobtrack.service;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jake.jobtrack.model.Job;
import com.jake.jobtrack.repo.JobRepo;
import com.jake.jobtrack.repo.UserRepo;

@Service
public class JobService {

    @Autowired
    private JobRepo jobRepo;

    @Autowired
    private UserRepo userRepo;


    public List<Job> getJobsByUserEmail(String email) {
        return jobRepo.findByUserEmail(email);
    }

    public Job saveJob(Job job) {
        return jobRepo.save(job);
    }

    public Job removeJob(Long id) {
        Job job = jobRepo.findById(id).orElse(null);
        if (job != null) {
            jobRepo.delete(job);
        }
        return job;
    }


    // Data Analytics Methods

    public int getTotalJobsByUserEmail(String email) {
        return jobRepo.countByUserEmail(email);
    }

    public int getJobsByStatus(String email, String status) {
        return jobRepo.countByUserEmailAndStatus(email, status);
    }

    public int getJobsByCompany(String email, String company) {
        return jobRepo.countByUserEmailAndCompany(email, company);
    }

    public int getApplicationsThisMonth(String email) {
        int count = 0;
        java.time.LocalDate now = java.time.LocalDate.now();
        java.time.LocalDate startOfMonth = now.with(java.time.temporal.TemporalAdjusters.firstDayOfMonth());
        for (Job job : getJobsByUserEmail(email)) {
            if(job.getAppliedDate().isAfter(startOfMonth)) {
                count++;
            }
        }
        return count;
    }

    public int getApplicationsThisWeek(String email) {
        int count = 0;
        for (Job job : getJobsByUserEmail(email)) {
            if(job.getAppliedDate().isAfter(java.time.LocalDate.now().minusWeeks(1))) {
                count++;
            }
        }
        return count;
    }

    public int getAverageApplicationsPerMonth(String email) {
        int totalJobs = getTotalJobsByUserEmail(email);
        int months = userRepo.findByEmail(email)
                                .getCreatedAt()
                                .until(java.time.LocalDate.now())
                                .getMonths() + 1;

        return months > 0 ? totalJobs / months : 0;
    }

    public int getAverageApplicationsPerWeek(String email) {
        int totalJobs = getTotalJobsByUserEmail(email);
        int weeks = userRepo.findByEmail(email)
                                .getCreatedAt()
                                .until(java.time.LocalDate.now())
                                .getDays() / 7;

        return weeks > 0 ? totalJobs / weeks : 0;
    }

    public float getResponseRate(String email) {
        int totalJobs = getTotalJobsByUserEmail(email);
        int interviewed = getJobsByStatus(email, "INTERVIEWING");
        return totalJobs > 0 ? (float) interviewed / totalJobs * 100 : 0;
    }

    public float getInterviewRate(String email) {
        int totalJobs = getTotalJobsByUserEmail(email);
        int interviewed = getJobsByStatus(email, "INTERVIEWING");
        return totalJobs > 0 ? (float) interviewed / totalJobs * 100 : 0;
    }

    public float getOfferRate(String email) {
        int totalJobs = getTotalJobsByUserEmail(email);
        int offered = getJobsByStatus(email, "OFFERED");
        return totalJobs > 0 ? (float) offered / totalJobs * 100 : 0;
    }

    public float getRejectionRate(String email) {
        int totalJobs = getTotalJobsByUserEmail(email);
        int rejected = getJobsByStatus(email, "REJECTED");
        return totalJobs > 0 ? (float) rejected / totalJobs * 100 : 0;
    }

    public Duration getAverageTimeToResponse(String email) {
        List<Job> jobs = getJobsByUserEmail(email);
        if(jobs.isEmpty()) {
            return Duration.ZERO;
        }
        
        long totalSeconds = 0;

        for(Job job : jobs) {
            if(job.getFirstResponseAt() != null && job.getAppliedDate() != null) {
                totalSeconds += Duration.between(job.getAppliedDate().atStartOfDay(), job.getFirstResponseAt()).getSeconds();
            }
        }

        return Duration.ofSeconds(totalSeconds / jobs.size());
    }

}

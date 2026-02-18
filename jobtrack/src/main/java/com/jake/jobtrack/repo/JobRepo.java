package com.jake.jobtrack.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jake.jobtrack.model.Job;

@Repository
public interface JobRepo extends JpaRepository<Job, Long> {

    List<Job> findByUserEmail(String email);

    int countByUserEmail(String email);

    int countByUserEmailAndStatus(String email, String status);

    int countByUserEmailAndCompany(String email, String company);

}

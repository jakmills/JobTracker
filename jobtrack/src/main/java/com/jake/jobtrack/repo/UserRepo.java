package com.jake.jobtrack.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jake.jobtrack.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    
    User findByEmail(String email);
    
}
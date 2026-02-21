package com.jake.jobtrack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jake.jobtrack.model.User;
import com.jake.jobtrack.repo.UserRepo;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    
    public User newUser(User user) {
        if(user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        
        return userRepo.save(user);
    }

    public User removeUser(Long id) {
        User user = userRepo.findById(id).orElse(null);
        if (user != null) {
            userRepo.delete(user);
        }
        return user;
    }
}

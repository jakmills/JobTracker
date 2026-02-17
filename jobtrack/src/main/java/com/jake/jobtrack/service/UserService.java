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
        return userRepo.save(user);
    }
}

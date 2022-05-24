package com.tct.tctcampaign.controller;

import com.tct.tctcampaign.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/v1/auth/is-user-present")
    public Object checkIfUserAlreadyPresent(@RequestParam String user){
        return userRepository.checkIsUserValid(user);
    }

    @GetMapping("/v1/auth/get-all-users")
    @PreAuthorize("hasRole('ADMIN')")
    public Object getAllUsers(){
        return userRepository.getAllUsers();
    }

}

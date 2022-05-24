package com.tct.tctcampaign.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tct.tctcampaign.model.db.CampaignAnswerDao;
import com.tct.tctcampaign.model.request.CreateUserRequest;
import com.tct.tctcampaign.repo.UserRepository;
import com.tct.tctcampaign.service.UserCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserCreationService userCreationService;

    @GetMapping("/v1/auth/is-user-present")
    public Object checkIfUserAlreadyPresent(@RequestParam String user){
        return userRepository.checkIsUserValid(user);
    }

    @GetMapping("/v1/auth/get-all-users")
    @PreAuthorize("hasRole('ADMIN')")
    public Object getAllUsers(){
        return userRepository.getAllUsers();
    }

    @GetMapping("/v1/auth/get-roles")
    public Object getRoles(){
        return userRepository.getRoles();
    }

    @PostMapping("/v1/auth/create-user")
    @PreAuthorize("hasRole('ADMIN')")
    public void updateCampaignAnswer(@RequestBody CreateUserRequest createUserRequest) {
        userCreationService.createUser(createUserRequest);
    }

    @GetMapping("/v1/auth/delete-user")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@RequestParam int userId) {
        userRepository.deleteUser(userId);
    }

}

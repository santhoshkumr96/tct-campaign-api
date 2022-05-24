package com.tct.tctcampaign.service;


import com.tct.tctcampaign.model.request.CreateUserRequest;
import com.tct.tctcampaign.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserCreationService {

    @Autowired
    UserRepository userRepository;

    public void createUser(CreateUserRequest createUserRequest){
        int userId = userRepository.insertUser(createUserRequest.getUsername()
                ,createUserRequest.getUsername()
                ,createUserRequest.getPassword());
        userRepository.insertUserRoleMapping(userId, createUserRequest.getRoleId());
    }

}

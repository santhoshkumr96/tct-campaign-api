package com.tct.tctcampaign.repo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tct.tctcampaign.model.db.Role;
import com.tct.tctcampaign.model.db.User;
import com.tct.tctcampaign.rowmapper.RolesRowMapper;
import com.tct.tctcampaign.rowmapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class UserRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public User getUserByEmail(String email){

        String sql = "select * from  [dbo].[users] join [dbo].[users_roles]  on  [dbo].[users].USER_ID  =  [dbo].[users_roles].USER_ID join  [dbo].[roles] on  [dbo].[users_roles].ROLE_ID =  [dbo].[roles].ROLE_ID " +
                "where [dbo].[users].ENABLED = 1 AND [dbo].[users].USERNAME = '"+email+ "'";

        List<User> users =  jdbcTemplate.query(sql, new UserRowMapper());

        Set<Role> roles = new HashSet<>();

        for(User user: users){
            Role role = new Role();
            role.setId(user.getRoleId());
            role.setName(user.getRole());
            roles.add(role);
        }

        if(Objects.nonNull(users) && users.size() > 0){
            users.get(0).setRoles(roles);
            return users.get(0);
        }

        return null;
    }

    public List<User> getAllUsers(){
        String sql = "select * from  [dbo].[users] join [dbo].[users_roles]  on  [dbo].[users].USER_ID  =  [dbo].[users_roles].USER_ID join  [dbo].[roles] on  [dbo].[users_roles].ROLE_ID =  [dbo].[roles].ROLE_ID " +
                "where [dbo].[users].ENABLED = 1";
        List<User> users =  jdbcTemplate.query(sql, new UserRowMapper());
        return users;
    }

    public Boolean checkIsUserValid(String user){
        String sql = "select count(*) from  [dbo].[users] where [dbo].[users].ENABLED = 1 AND [dbo].[users].USERNAME = ?";
        int count = jdbcTemplate.queryForObject(sql, new Object[] { user }, Integer.class);
        return count > 0;
    }

    public List<Role> getRoles(){
        String sql = "select * from [dbo].[roles]";
        return jdbcTemplate.query(sql, new RolesRowMapper());
    }

}

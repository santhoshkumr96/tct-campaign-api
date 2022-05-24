package com.tct.tctcampaign.repo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tct.tctcampaign.model.db.Role;
import com.tct.tctcampaign.model.db.User;
import com.tct.tctcampaign.rowmapper.RolesRowMapper;
import com.tct.tctcampaign.rowmapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;
import java.util.Date;
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

    public int insertUser(String username,String email, String password){
        String query = "INSERT INTO users " +
                "(email," +
                "username, " +
                "password, " +
                "enabled," +
                "created_date) " +
                "VALUES " +
                "(?,?,?,?,?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,email);
                ps.setString(2,username);
                ps.setString(3,password);
                ps.setInt(4,1);
                ps.setTimestamp(5, new java.sql.Timestamp(new Date().getTime()));
                return ps;
            }
        }, holder);
        return (int) holder.getKey().longValue();
    }

    public void insertUserRoleMapping(int userId, int roleId){
        String query = "insert into users_roles (user_id, role_id) values (?,?)";
        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1,userId);
                ps.setInt(2,roleId);
                return ps;
            }
        }, holder);
    }

}

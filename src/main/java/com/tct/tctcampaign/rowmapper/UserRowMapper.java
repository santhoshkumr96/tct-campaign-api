package com.tct.tctcampaign.rowmapper;

import com.tct.tctcampaign.model.db.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        if(Objects.nonNull(rs)){
            user.setId(rs.getLong("user_id"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setUsername(rs.getString("username"));
            user.setRole(rs.getString("name"));
            user.setRoleId(rs.getInt("role_id"));
            user.setEnabled(rs.getInt("enabled") == 1 ?true:false);
        }
        return user;
    }
}

package com.tct.tctcampaign.rowmapper;

import com.tct.tctcampaign.model.db.Role;
import com.tct.tctcampaign.model.db.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class RolesRowMapper implements RowMapper<Role> {
    @Override
    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
        Role role = new Role();
        if(Objects.nonNull(rs)){
            role.setId(rs.getInt("role_id"));
            role.setName(rs.getString("name"));
        }
        return role;
    }
}

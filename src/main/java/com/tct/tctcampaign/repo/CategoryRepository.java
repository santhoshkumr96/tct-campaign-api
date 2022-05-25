package com.tct.tctcampaign.repo;

import com.tct.tctcampaign.model.db.CategoryDao;
import com.tct.tctcampaign.rowmapper.CategoryRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class CategoryRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<CategoryDao> getAllCategory(){
        String query = "select * from [dbo].[TBL_M_QUESTION_CATEGORY]";
        return jdbcTemplate.query(query, new CategoryRowMapper());
    }

    public void insertQuestionCategory(String questionCategory, String user){
        String query = "insert into TBL_M_QUESTION_CATEGORY " +
                "(CATEGORY_DESC,created_by)" +
                " values (?,?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,questionCategory);
                ps.setString(2,user);
                return ps;
            }
        }, holder);
    }
}

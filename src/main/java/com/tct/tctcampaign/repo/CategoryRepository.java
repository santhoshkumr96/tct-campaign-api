package com.tct.tctcampaign.repo;

import com.tct.tctcampaign.model.db.CategoryDao;
import com.tct.tctcampaign.rowmapper.CategoryRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<CategoryDao> getAllCategory(){
        String query = "select * from [dbo].[TBL_M_QUESTION_CATEGORY]";
        return jdbcTemplate.query(query, new CategoryRowMapper());
    }
}

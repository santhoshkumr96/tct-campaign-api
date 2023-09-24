package com.tct.tctcampaign.repo;

import com.tct.tctcampaign.model.db.ResponseTypeDao;
import com.tct.tctcampaign.rowmapper.ResponseTypeRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ResponseTypeRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<ResponseTypeDao> getAllReponseType(){
        String query = "select * from tbl_m_response_type";
        return jdbcTemplate.query(query, new ResponseTypeRowMapper());
    }
}

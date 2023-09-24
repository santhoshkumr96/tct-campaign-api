package com.tct.tctcampaign.repo;

import com.tct.tctcampaign.model.db.Response;
import com.tct.tctcampaign.rowmapper.ResponseRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class ResponseRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Response> deleteByQuestionId(Integer questionId, String user){
        String query = "update tbl_m_response_details set status_desc = ? , changed_by = ? , changed_date = ? where question_id = ?";
        jdbcTemplate.update(query,"DELETED",user, new Date(),  questionId);
        return null;
    };

    public List<Response> findByQuestionId(Integer questionId){
        String query = "select * from tbl_m_response_details where status_desc != 'DELETED' and question_id = "+questionId;
        List<Response> responses = jdbcTemplate.query(query, new ResponseRowMapper());
        return responses;
    };

    public void save(Response responseToStore, String user) {
        insert(responseToStore);
    }

    public void insert(Response responseToStore) {
        String query = "insert into tbl_m_response_details (" +
                "response_name," +
                "response_desc," +
                "question_id," +
                "created_by," +
                "changed_by," +
                "approved_by," +
                "status_desc," +
                "created_date," +
                "changed_date," +
                "approved_date," +
                "comments," +
                "response_desc_ta )" +
                "values (?,?,?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(query,
                responseToStore.getResponseName(),
                responseToStore.getResponseDesc(),
                responseToStore.getQuestionId(),
                responseToStore.getCreatedBy(),
                responseToStore.getChangedBy(),
                responseToStore.getApprovedBy(),
                responseToStore.getStatusDesc(),
                responseToStore.getCreatedDate(),
                responseToStore.getChangedDate(),
                responseToStore.getApprovedDate(),
                responseToStore.getComments(),
                ""
        );
    }
}

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
        String query = "update [dbo].[TBL_M_RESPONSE_DETAILS] set STATUS_DESC = ? , CHANGED_BY = ? , CHANGED_DATE = ? where QUESTION_ID = ?";
        jdbcTemplate.update(query,"DELETED",user, new Date(),  questionId);
        return null;
    };

    public List<Response> findByQuestionId(Integer questionId){
        String query = "select * from [dbo].[TBL_M_RESPONSE_DETAILS] where STATUS_DESC != 'DELETED' and QUESTION_ID = "+questionId;
        List<Response> responses = jdbcTemplate.query(query, new ResponseRowMapper());
        return responses;
    };

    public void save(Response responseToStore, String user) {
        insert(responseToStore);
    }

    public void insert(Response responseToStore) {
        String query = "insert into [dbo].[TBL_M_RESPONSE_DETAILS](" +
                "RESPONSE_NAME," +
                "RESPONSE_DESC," +
                "QUESTION_ID," +
                "CREATED_BY," +
                "CHANGED_BY," +
                "APPROVED_BY," +
                "STATUS_DESC," +
                "CREATED_DATE," +
                "CHANGED_DATE," +
                "APPROVED_DATE," +
                "COMMENTS," +
                "RESPONSE_DESC_TA )" +
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

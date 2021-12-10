package com.tct.tctcampaign.rowmapper;

import com.tct.tctcampaign.model.db.Response;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResponseRowMapper implements RowMapper<Response> {
    @Override
    public Response mapRow(ResultSet rs, int rowNum) throws SQLException {
        Response obj = new Response();
        if(rs != null) {
            obj.setResponseId(rs.getInt("RESPONSE_ID"));
            obj.setResponseName(rs.getString("RESPONSE_NAME"));
            obj.setResponseDesc(rs.getString("RESPONSE_DESC"));
            obj.setQuestionId(rs.getInt("QUESTION_ID"));
            obj.setStatusDesc(rs.getString("STATUS_DESC"));
            obj.setCreatedBy(rs.getString("CREATED_BY"));
            obj.setChangedBy(rs.getString("CHANGED_BY"));
            obj.setApprovedBy(rs.getString("APPROVED_BY"));
            obj.setCreatedDate(rs.getDate("CREATED_DATE"));
            obj.setChangedDate(rs.getDate("CHANGED_DATE"));
            obj.setApprovedDate(rs.getDate("APPROVED_DATE"));
            obj.setComments(rs.getString("COMMENTS"));
        }
        return obj;
    }
}

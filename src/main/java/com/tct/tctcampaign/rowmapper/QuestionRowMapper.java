package com.tct.tctcampaign.rowmapper;

import com.tct.tctcampaign.model.db.Question;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class QuestionRowMapper implements RowMapper<Question> {
    @Override
    public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
        Question obj = new Question();
        if(Objects.nonNull(rs)){
            obj.setQuestionId(rs.getInt("QUESTION_ID"));
            obj.setQuestionName(rs.getString("QUESTION_NAME"));
            obj.setQuestionDesc(rs.getString("QUESTION_DESC"));
            obj.setCreatedBy(rs.getString("CREATED_BY"));
            obj.setChangedBy(rs.getString("CHANGED_BY"));
            obj.setApprovedBy(rs.getString("APPROVED_BY"));
            obj.setStatusDesc(rs.getString("STATUS_DESC"));
            obj.setCreatedDate(rs.getDate("CREATED_DATE"));
            obj.setChangedDate(rs.getDate("CHANGED_DATE"));
            obj.setApprovedDate(rs.getDate("APPROVED_DATE"));
            obj.setComments(rs.getString("COMMENTS"));
            obj.setResponseType(rs.getString("RESPONSE_TYPE"));
        }
        return obj;
    }
}

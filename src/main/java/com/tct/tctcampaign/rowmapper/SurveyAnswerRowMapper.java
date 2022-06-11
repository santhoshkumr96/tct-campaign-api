package com.tct.tctcampaign.rowmapper;

import com.tct.tctcampaign.model.response.SurveyAnswerTO;
import com.tct.tctcampaign.model.response.SurveyPeopleTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class SurveyAnswerRowMapper implements RowMapper<SurveyAnswerTO> {
    @Override
    public SurveyAnswerTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        SurveyAnswerTO obj = new SurveyAnswerTO();
        if(Objects.nonNull(rs)){
            obj.setMemberName(rs.getString("Member_Name"));
            obj.setQuestionName(rs.getString("question_name"));
            obj.setAnswer(rs.getString("Answer"));
        }
        return obj;
    }
}

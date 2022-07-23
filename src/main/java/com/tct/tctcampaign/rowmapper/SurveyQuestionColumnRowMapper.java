package com.tct.tctcampaign.rowmapper;

import com.tct.tctcampaign.model.response.SurveyAnswerTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class SurveyQuestionColumnRowMapper implements RowMapper<SurveyAnswerTO> {
    @Override
    public SurveyAnswerTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        SurveyAnswerTO obj = new SurveyAnswerTO();
        if(Objects.nonNull(rs)){
            obj.setQuestionName(rs.getString("question_name"));
        }
        return obj;
    }
}

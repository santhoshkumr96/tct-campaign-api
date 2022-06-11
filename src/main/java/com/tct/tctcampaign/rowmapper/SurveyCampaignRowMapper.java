package com.tct.tctcampaign.rowmapper;

import com.tct.tctcampaign.model.db.Survey;
import com.tct.tctcampaign.model.response.SurveyCampaignTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class SurveyCampaignRowMapper implements RowMapper<SurveyCampaignTO> {
    @Override
    public SurveyCampaignTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        SurveyCampaignTO obj = new SurveyCampaignTO();
        if(Objects.nonNull(rs)){
            obj.setSurveyId(rs.getInt("survey_id"));
            obj.setSurveyName(rs.getString("survey_name"));
            obj.setCampaignId(rs.getInt("campaign_id"));
            obj.setCampaignName(rs.getString("campaign_name"));
            obj.setCreatedBy(rs.getString("created_by"));
        }
        return obj;
    }
}

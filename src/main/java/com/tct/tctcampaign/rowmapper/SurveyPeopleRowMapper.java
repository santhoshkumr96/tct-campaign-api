package com.tct.tctcampaign.rowmapper;

import com.tct.tctcampaign.model.response.SurveyCampaignTO;
import com.tct.tctcampaign.model.response.SurveyPeopleTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class SurveyPeopleRowMapper implements RowMapper<SurveyPeopleTO> {
    @Override
    public SurveyPeopleTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        SurveyPeopleTO obj = new SurveyPeopleTO();
        if(Objects.nonNull(rs)){
            obj.setPersonId(rs.getInt("PERSON_ID"));
            obj.setMemberName(rs.getString("Member_Name"));
            obj.setMobileNo(rs.getString("Mobile_No"));
            obj.setDistrict(rs.getString("District"));
            obj.setBlock(rs.getString("Block"));
            obj.setStatusDesc(rs.getString("STATUS_DESC"));
        }
        return obj;
    }
}

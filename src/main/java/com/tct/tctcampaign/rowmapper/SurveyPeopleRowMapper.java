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
            obj.setPersonId(rs.getString("PERSON_ID"));
            obj.setMemberName(rs.getString("member_name"));
            obj.setMobileNo(rs.getString("mobile_number"));
            obj.setVillageName(rs.getString("village_name"));
            obj.setPanchayatCode(rs.getString("panchayatCode"));
//            obj.setStatusDesc(rs.getString("STATUS_DESC"));
          obj.setStatusDesc("OPEN");
        }
        return obj;
    }
}

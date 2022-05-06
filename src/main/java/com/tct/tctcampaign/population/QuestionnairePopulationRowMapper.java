package com.tct.tctcampaign.population;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionnairePopulationRowMapper implements RowMapper<QuestionnairePopulationEntity> {


    @Override
    public QuestionnairePopulationEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        QuestionnairePopulationEntity obj = new QuestionnairePopulationEntity();
        if(rs != null) {
            obj.setRowId(rs.getInt("ROW_ID"));
            obj.setObjectiveId(rs.getInt("OBJECTIVE_ID"));
            obj.setCampaignId(rs.getInt("CAMPAIGN_ID"));
            obj.setVillageCode(rs.getString("VILLAGE_CODE"));
            obj.setFamilyHeadId(rs.getString("FAMILY_HEAD_ID"));
            obj.setFamilyPersonId(rs.getString("PERSON_FAMILY_ID"));
            obj.setCreatedBy(rs.getString("CREATED_BY"));
            obj.setChangedBy(rs.getString("CHANGED_BY"));
            obj.setCreatedOn(rs.getDate("CREATED_DATE"));
            obj.setChangedOn(rs.getDate("CHANGED_DATE"));
            obj.setComments(rs.getString("COMMENTS"));
            obj.setMobileNum(rs.getString("MOBILE_NUM"));
            obj.setFamilyHeadName(rs.getString("FAMILY_HEAD_NAME"));
            obj.setFamilyPersonName(rs.getString("PERSON_FAMILY_NAME"));
        }
        return obj;
    }
}

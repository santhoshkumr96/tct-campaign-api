package com.tct.tctcampaign.rowmapper;

import com.tct.tctcampaign.model.db.SectionConditionDao;
import com.tct.tctcampaign.model.db.SectionDao;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SectinConditionDaoRowMapper implements RowMapper<SectionConditionDao> {
    @Override
    public SectionConditionDao mapRow(ResultSet rs, int rowNum) throws SQLException {
        SectionConditionDao obj = new SectionConditionDao();
        if(rs != null){
            obj.setSectionId(rs.getInt("SECTION_ID"));
            obj.setCampaignId(rs.getInt("CAMPAIGN_ID"));
            obj.setStatusDesc(rs.getString("STATUS_DESC"));
            obj.setQuestionId(rs.getInt("QUESTION_ID"));
            obj.setResponseId(rs.getInt("RESPONSE_ID"));
            obj.setSectionNameToGo(rs.getString("SECTION_NAME_TO_GO"));
        }
        return obj;
    }
}

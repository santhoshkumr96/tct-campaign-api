package com.tct.tctcampaign.rowmapper;

import com.tct.tctcampaign.model.db.CampainQuestionDao;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CampainQuestionDaoRowMapper implements RowMapper<CampainQuestionDao> {
    @Override
    public CampainQuestionDao mapRow(ResultSet rs, int rowNum) throws SQLException {
        CampainQuestionDao obj = new CampainQuestionDao();
        if(rs != null) {
            obj.setQuestionOrder(rs.getInt("ROW_ID"));
            obj.setSectionId(rs.getInt("SECTION_ID"));
            obj.setQuestionId(rs.getInt("QUESTION_ID"));
            obj.setCampaignId(rs.getInt("CAMPAIGN_ID"));
        }
        return obj;
    }
}

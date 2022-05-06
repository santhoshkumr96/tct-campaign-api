package com.tct.tctcampaign.rowmapper;

import com.tct.tctcampaign.model.db.SectionDao;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SectinDaoRowMapper implements RowMapper<SectionDao> {
    @Override
    public SectionDao mapRow(ResultSet rs, int rowNum) throws SQLException {
        SectionDao obj = new SectionDao();
        if(rs != null){
            obj.setSectionId(rs.getInt("SECTION_ID"));
            obj.setSectionName(rs.getString("SECTION_NAME"));
            obj.setCampaignId(rs.getInt("CAMPAIGN_ID"));
            obj.setStatusDesc(rs.getString("STATUS_DESC"));
            obj.setCreatedBy(rs.getString("CREATED_BY"));
            obj.setChangedBy(rs.getString("CHANGED_BY"));
            obj.setApprovedBy(rs.getString("APPROVED_BY"));
            obj.setCreatedDate(rs.getDate("CREATED_DATE"));
            obj.setChangedDate(rs.getDate("CHANGED_DATE"));
            obj.setApprovedDate(rs.getDate("APPROVED_DATE"));
        }
        return obj;
    }
}

package com.tct.tctcampaign.rowmapper;

import com.tct.tctcampaign.model.db.CampaignDao;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CampaignDaoRowMapper implements RowMapper<CampaignDao> {
    @Override
    public CampaignDao mapRow(ResultSet rs, int rowNum) throws SQLException {
        CampaignDao obj = new CampaignDao();
        if (rs != null) {
            obj.setCampaignId(rs.getInt("CAMPAIGN_ID"));
            obj.setCampaignName(rs.getString("CAMPAIGN_NAME"));
            obj.setCampaignDesc(rs.getString("CAMPAIGN_DESC"));
            obj.setCreatedBy(rs.getString("CREATED_BY"));
            obj.setChangedBy(rs.getString("CHANGED_BY"));
            obj.setApprovedBy(rs.getString("APPROVED_BY"));
            obj.setStatusDesc(rs.getString("STATUS_DESC"));
            obj.setCreatedDate(rs.getDate("CREATED_DATE"));
            obj.setChangedDate(rs.getDate("CHANGED_DATE"));
            obj.setApprovedDate(rs.getDate("APPROVED_DATE"));
            obj.setComments(rs.getString("COMMENTS"));
            obj.setCampaignObjective(rs.getString("CAMPAIGN_OBJECTIVE"));
        }
        return obj;
    }
}

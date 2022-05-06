package com.tct.tctcampaign.repo;


import com.tct.tctcampaign.model.db.CampaignDao;
import com.tct.tctcampaign.rowmapper.CampaignDaoRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Date;
import java.util.List;

@Repository
public class CampaignRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<CampaignDao> findAll() {
        String query = "select *  from [dbo].[TBL_T_CAMPAIGN] where STATUS_DESC != 'DELETED'";
        List<CampaignDao>  campaignDaos = jdbcTemplate.query(query, new CampaignDaoRowMapper());
        return campaignDaos;
    }


    public CampaignDao getByCampaignId(Integer campaignId){
        String query = "select *  from [dbo].[TBL_T_CAMPAIGN] where STATUS_DESC != 'DELETED' " +
                "and CAMPAIGN_ID ="+campaignId+"";
        List<CampaignDao>  campaignDaos = jdbcTemplate.query(query, new CampaignDaoRowMapper());
        if(campaignDaos.size() > 0){
            return  campaignDaos.get(0);
        }
        return new CampaignDao();
    };

    public List<CampaignDao> deleteByCampaignId(Integer campaignId, String user){
        String query = "update [dbo].[TBL_T_CAMPAIGN] set STATUS_DESC = ? , CHANGED_BY = ? , CHANGED_DATE = ? where CAMPAIGN_ID = ?";
        jdbcTemplate.update(query,"DELETED",user, new Date(),  campaignId);
        return findAll();
    };

    public CampaignDao insert(CampaignDao campaignDao) {
        String query = "insert into [dbo].[TBL_T_CAMPAIGN](" +
                "CAMPAIGN_NAME," +
                "CAMPAIGN_DESC," +
                "OBJECTIVE_ID," +
                "CREATED_BY," +
                "CHANGED_BY," +
                "APPROVED_BY," +
                "STATUS_DESC," +
                "CREATED_DATE," +
                "CHANGED_DATE," +
                "APPROVED_DATE," +
                "COMMENTS," +
                "CAMPAIGN_NAME_TA," +
                "CAMPAIGN_DESC_TA," +
                "CAMPAIGN_OBJECTIVE) "+
                "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,campaignDao.getCampaignName());
                ps.setString(2,campaignDao.getCampaignDesc());
                ps.setInt(3,3);
                ps.setString(4,campaignDao.getCreatedBy());
                ps.setString(5,campaignDao.getChangedBy());
                ps.setString(6,campaignDao.getApprovedBy());
                ps.setString(7,campaignDao.getStatusDesc());
                ps.setTimestamp(8, new java.sql.Timestamp(campaignDao.getCreatedDate().getTime()));
                ps.setTimestamp(9,new java.sql.Timestamp(campaignDao.getChangedDate().getTime()));
                ps.setNull(10, Types.NULL);
                ps.setString(11,campaignDao.getComments());
                ps.setString(12,"");
                ps.setString(13,"");
                ps.setString(14,campaignDao.getCampaignObjective());
                return ps;
            }
        }, holder);

        campaignDao.setCampaignId((int) holder.getKey().longValue());

        return campaignDao;
    }

    public CampaignDao update(CampaignDao campaignDao) {
        String query = "update  [dbo].[TBL_T_CAMPAIGN] set "+
                "CAMPAIGN_NAME = ?,"+
                "CAMPAIGN_DESC = ?,"+
                "OBJECTIVE_ID = ?,"+
                "CREATED_BY = ?,"+
                "CHANGED_BY = ?,"+
                "APPROVED_BY = ?,"+
                "STATUS_DESC = ?,"+
                "CREATED_DATE = ?,"+
                "CHANGED_DATE = ?,"+
                "APPROVED_DATE = ?,"+
                "COMMENTS = ?,"+
                "CAMPAIGN_NAME_TA = ?,"+
                "CAMPAIGN_DESC_TA = ?,"+
                "CAMPAIGN_OBJECTIVE = ? "+
                "where CAMPAIGN_ID = ?";
        jdbcTemplate.update(query,
                campaignDao.getCampaignName(),
                campaignDao.getCampaignDesc(),
                3,
                campaignDao.getCreatedBy(),
                campaignDao.getChangedBy(),
                campaignDao.getApprovedBy(),
                campaignDao.getStatusDesc(),
                campaignDao.getCreatedDate(),
                campaignDao.getChangedDate(),
                campaignDao.getApprovedDate(),
                campaignDao.getComments(),
                "",
                "",
                campaignDao.getCampaignObjective(),
                campaignDao.getCampaignId()
                );
        return campaignDao;
    }

}

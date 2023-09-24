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
        String query = "select *  from tbl_t_campaign where status_desc != 'DELETED'";
        List<CampaignDao>  campaignDaos = jdbcTemplate.query(query, new CampaignDaoRowMapper());
        return campaignDaos;
    }


    public CampaignDao getByCampaignId(Integer campaignId){
        String query = "select *  from tbl_t_campaign where status_desc != 'DELETED' " +
                "and campaign_id ="+campaignId+"";
        List<CampaignDao>  campaignDaos = jdbcTemplate.query(query, new CampaignDaoRowMapper());
        if(campaignDaos.size() > 0){
            return  campaignDaos.get(0);
        }
        return new CampaignDao();
    };

    public List<CampaignDao> deleteByCampaignId(Integer campaignId, String user){
        String query = "update tbl_t_campaign set status_desc = ? , changed_by = ? , changed_date = ? where campaign_id = ?";
        jdbcTemplate.update(query,"DELETED",user, new Date(),  campaignId);
        return findAll();
    };

    public CampaignDao insert(CampaignDao campaignDao) {
        String query = "insert into tbl_t_campaign (" +
                "campaign_name," +
                "campaign_desc," +
                "objective_id," +
                "created_by," +
                "changed_by," +
                "approved_by," +
                "status_desc," +
                "created_date," +
                "changed_date," +
                "approved_date," +
                "comments," +
                "campaign_name_ta," +
                "campaign_desc_ta," +
                "campaign_objective) "+
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
        String query = "update  tbl_t_campaign set "+
                "campaign_name = ?,"+
                "campaign_desc = ?,"+
                "objective_id = ?,"+
                "created_by = ?,"+
                "changed_by = ?,"+
                "approved_by = ?,"+
                "status_desc = ?,"+
                "created_date = ?,"+
                "changed_date = ?,"+
                "approved_date = ?,"+
                "comments = ?,"+
                "campaign_name_ta = ?,"+
                "campaign_desc_ta = ?,"+
                "campaign_objective = ? "+
                "where campaign_id = ?";
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

    public int checkIfValidCampaignId(int campaignId){
        String query = "select count(campaign_id) from tbl_t_campaign WHERE status_desc  = 'APPROVED' and campaign_id = "+campaignId;
        return jdbcTemplate.queryForObject(query,new Object[]{}, Integer.class);
    }

}

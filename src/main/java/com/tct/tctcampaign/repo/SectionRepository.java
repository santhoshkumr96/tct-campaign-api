package com.tct.tctcampaign.repo;


import com.tct.tctcampaign.model.db.SectionDao;
import com.tct.tctcampaign.rowmapper.SectinDaoRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Date;
import java.util.List;

@Repository
public class SectionRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<SectionDao> getByCampaignId(Integer campaignId){
        String query = "select *  from tbl_t_section where status_desc != 'DELETED' and campaign_id="+campaignId;
        List<SectionDao> sectionDaos = jdbcTemplate.query(query, new SectinDaoRowMapper());
        return sectionDaos;
    };

    public List<SectionDao> deleteByCampaignId(Integer campaignId, String user){
        String query = "update tbl_t_section set status_desc = ? , changed_by = ? , changed_date = ? where campaign_id = ?";
        jdbcTemplate.update(query,"DELETED",user, new Date(),  campaignId);
        return getByCampaignId(campaignId);
    };


    public SectionDao save(SectionDao sectionDao) {
        String query = "insert into tbl_t_section ("+
        "section_name ,"+
        "campaign_id ,"+
        "created_by ,"+
        "changed_by ,"+
        "approved_by ,"+
        "status_desc ,"+
        "created_date ,"+
        "changed_date ,"+
        "approved_date ,"+
        "comments ,"+
        "after_section ,"+
        "section_name_ta ) "+
        "values (?,?,?,?,?,?,?,?,?,?,?,?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,sectionDao.getSectionName());
                ps.setInt(2,sectionDao.getCampaignId());
                ps.setString(3,sectionDao.getCreatedBy());
                ps.setString(4,sectionDao.getChangedBy());
                ps.setString(5,sectionDao.getApprovedBy());
                ps.setString(6,"PENDING");
                ps.setTimestamp(7, new java.sql.Timestamp(sectionDao.getCreatedDate().getTime()));
                ps.setTimestamp(8,new java.sql.Timestamp(sectionDao.getChangedDate().getTime()));
                ps.setNull(9, Types.NULL);
                ps.setString(10,"");
                ps.setString(11,sectionDao.getAfterSection());
                ps.setString(12,"");
                return ps;
            }
        }, holder);

        sectionDao.setSectionId((int) holder.getKey().longValue());

        return sectionDao;
    }
}

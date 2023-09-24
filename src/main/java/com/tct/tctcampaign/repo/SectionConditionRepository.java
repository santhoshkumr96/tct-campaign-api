package com.tct.tctcampaign.repo;


import com.tct.tctcampaign.model.db.SectionConditionDao;
import com.tct.tctcampaign.model.db.SectionDao;
import com.tct.tctcampaign.rowmapper.SectinConditionDaoRowMapper;
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
public class SectionConditionRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<SectionConditionDao> getByCampaignId(Integer campaignId){
        String query = "select *  from tbl_t_section_conditional where status_desc != 'DELETED' and campaign_id="+campaignId;
        List<SectionConditionDao> SectionConditionDaos = jdbcTemplate.query(query, new SectinConditionDaoRowMapper());
        return SectionConditionDaos;
    };

    public List<SectionConditionDao> getByCampaignIdAndSectionId(Integer campaignId, Integer sectionId){
        String query = "select *  from tbl_t_section_conditional where status_desc != 'DELETED' and section_id="+sectionId+" and campaign_id="+campaignId;
        List<SectionConditionDao> SectionConditionDaos = jdbcTemplate.query(query, new SectinConditionDaoRowMapper());
        return SectionConditionDaos;
    };

    public List<SectionConditionDao> deleteByCampaignId(Integer campaignId, String user){
        String query = "update tbl_t_section_conditional set status_desc = ? where campaign_id = ?";
        jdbcTemplate.update(query,"DELETED",  campaignId);
        return getByCampaignId(campaignId);
    };


    public void save(int campaignId, int sectionId, int questionId, int responseId, String sectionToGo) {
        String query = "insert into tbl_t_section_conditional ("+
        "campaign_id ,"+
        "section_id ,"+
        "question_id ,"+
        "response_id ,"+
        "section_name_to_go ,"+
        "status_desc ) "+
        "values (?,?,?,?,?,?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1,campaignId);
                ps.setInt(2,sectionId);
                ps.setInt(3,questionId);
                ps.setInt(4,responseId);
                ps.setString(5,sectionToGo);
                ps.setString(6,"CREATED");
                return ps;
            }
        }, holder);

    }
}

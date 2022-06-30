package com.tct.tctcampaign.repo;

import com.tct.tctcampaign.model.db.CampainQuestionDao;
import com.tct.tctcampaign.model.db.SectionDao;
import com.tct.tctcampaign.rowmapper.CampainQuestionDaoRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Date;
import java.util.List;


@Repository
public class CampaignQuestionRepository{

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void  deleteByCampaignId(Integer campaignId ,String user){
        String query = "update [TBL_T_CAMPAIGN_QUESTIONNAIRE] set STATUS_DESC = ? , CHANGED_BY = ? , CHANGED_DATE = ? where CAMPAIGN_ID = ?";
        jdbcTemplate.update(query,"DELETED",user, new Date(),  campaignId);
    };

    public List<CampainQuestionDao> getBySectionId(Integer sectionId){
        String query = "select  * from [dbo].[TBL_T_CAMPAIGN_QUESTIONNAIRE] where SECTION_ID = "+sectionId+"";
        List<CampainQuestionDao> campainQuestionDaos = jdbcTemplate.query(query, new CampainQuestionDaoRowMapper());
        return campainQuestionDaos;
    };

    public void save(CampainQuestionDao campainQuestionDao, SectionDao sectionDao) {
        String query = "insert into [dbo].[TBL_T_CAMPAIGN_QUESTIONNAIRE] ( "+
                "OBJECTIVE_ID ,"+
                "CAMPAIGN_ID ,"+
                "SECTION_ID ,"+
                "QUESTION_ID ,"+
                "CREATED_BY ,"+
                "CHANGED_BY ,"+
                "APPROVED_BY ,"+
                "STATUS_DESC ,"+
                "CREATED_DATE ,"+
                "CHANGED_DATE ,"+
                "APPROVED_DATE ,"+
                "COMMENTS ,"+
                "isRequired )  "+
                "values (?,?,?,?,?,?,?,?,?,?,?,?,?) ";
        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1,3);
                ps.setInt(2,campainQuestionDao.getCampaignId());
                ps.setInt(3,campainQuestionDao.getSectionId());
                ps.setInt(4,campainQuestionDao.getQuestionId());
                ps.setString(5,sectionDao.getCreatedBy());
                ps.setString(6,sectionDao.getChangedBy());
                ps.setString(7,sectionDao.getApprovedBy());
                ps.setString(8,"PENDING");
                ps.setTimestamp(9, new java.sql.Timestamp(sectionDao.getCreatedDate().getTime()));
                ps.setTimestamp(10,new java.sql.Timestamp(sectionDao.getChangedDate().getTime()));
                ps.setNull(11, Types.NULL);
                ps.setString(12,"");
                ps.setInt(13,campainQuestionDao.getIsRequired()==true?1:0);
                return ps;
            }
        }, holder);

        sectionDao.setCampaignId((int) holder.getKey().longValue());
    }
}


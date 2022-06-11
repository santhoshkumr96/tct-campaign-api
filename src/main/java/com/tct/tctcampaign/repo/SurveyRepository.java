package com.tct.tctcampaign.repo;

import com.tct.tctcampaign.constants.Constants;
import com.tct.tctcampaign.model.db.Survey;
import com.tct.tctcampaign.model.request.PaginationModel;
import com.tct.tctcampaign.model.response.SurveyCampaignTO;
import com.tct.tctcampaign.model.response.SurveyPeopleTO;
import com.tct.tctcampaign.population.PopulationPaginationModel;
import com.tct.tctcampaign.population.QuestionnairePopulationEntity;
import com.tct.tctcampaign.population.QuestionnairePopulationRowMapper;
import com.tct.tctcampaign.rowmapper.SurveyCampaignRowMapper;
import com.tct.tctcampaign.rowmapper.SurveyPeopleRowMapper;
import com.tct.tctcampaign.rowmapper.SurveyRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Date;
import java.util.List;

@Repository
public class SurveyRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Survey> findAll(){
        String query = "select * from [dbo].[Survey]";

        List<Survey> surveyList = jdbcTemplate.query(query,new SurveyRowMapper());
        return surveyList;
    };

    public List<Survey> findAllWithQuery(PaginationModel paginationModel ){
        String query = "select * from [dbo].[Survey] "+
                "order by ID "+
                "OFFSET "+paginationModel.getNumberOfRows()*paginationModel.getPageNumber()+" ROWS "+
                "FETCH NEXT "+paginationModel.getNumberOfRows()+" ROWS ONLY ";

        List<Survey> surveyList = jdbcTemplate.query(query,new SurveyRowMapper());
        return surveyList;
    };

    public List<Survey> findAllWithQueryWithClause(PaginationModel paginationModel ){
        String query = "select * from [dbo].[Survey] where "+ paginationModel.getSqlCondition() + " "+
                "order by ID "+
                "OFFSET "+paginationModel.getNumberOfRows()*paginationModel.getPageNumber()+" ROWS "+
                "FETCH NEXT "+paginationModel.getNumberOfRows()+" ROWS ONLY ";

        List<Survey> surveyList = jdbcTemplate.query(query,new SurveyRowMapper());
        return surveyList;
    };

    public List<Survey> findAllOnlyWithClauseWithoutPagination(PaginationModel paginationModel ){
        String query = "select * from [dbo].[Survey] where "+ paginationModel.getSqlCondition() + " "+
                "order by ID ";
        List<Survey> surveyList = jdbcTemplate.query(query,new SurveyRowMapper());
        return surveyList;
    };

    public Integer countOfRecordsWithQuery(String clause){
        String query = "select count(ID) from [dbo].[Survey] where "+ clause;
        return jdbcTemplate.queryForObject(query,new Object[]{}, Integer.class);
    };

    public Integer countOfRecords(){
        String query = "select count(ID) from [dbo].[Survey]";
        return jdbcTemplate.queryForObject(query,new Object[]{}, Integer.class);
    };

    public Integer insertNewSurveyPopulationAssociation(PaginationModel paginationModel, String userName){
        String query = "INSERT INTO [dbo].[TBL_T_SURVEY_POPULATION_ASSOCIATION] (survey_name,campaign_id,created_by,status_desc,enabled,created_date) " +
                "values" +
                " (?,?,?,?,?,?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,paginationModel.getSurveyName());
                ps.setInt(2,paginationModel.getCampaignId());
                ps.setString(3,userName);
                ps.setString(4, Constants.OPEN);
                ps.setInt(5,1);
                ps.setTimestamp(6,new java.sql.Timestamp(new Date().getTime()));
                return ps;
            }
        }, holder);

       return (int) holder.getKey().longValue();
    };


    public void insertToSurveyPeopleAssociation(int surveyId, PaginationModel paginationModel){
        String query = "INSERT INTO TBL_T_SURVEY_POPULATION_PERSON (SURVEY_ID, PERSON_ID, STATUS_DESC) " +
                "SELECT " +
                surveyId+" as SURVEY_ID, " +
                " ID , '" +
                Constants.OPEN+ "' as STATUS_DESC "+
                "FROM " +
                " [dbo].[Survey] " +
                "WHERE " +
                paginationModel.getSqlCondition();
        jdbcTemplate.execute(query);
    }

    public List<SurveyCampaignTO> getSurveyAndCampaign(){
        String query = "select SPA.survey_id, SPA.survey_name, CAM.campaign_id , CAM.campaign_name , SPA.created_by from TBL_T_SURVEY_POPULATION_ASSOCIATION SPA " +
                "JOIN [dbo].[TBL_T_CAMPAIGN] CAM on SPA.campaign_id = CAM.campaign_id";
        List<SurveyCampaignTO> surveyCampaignList = jdbcTemplate.query(query,new SurveyCampaignRowMapper());
        return surveyCampaignList;
    }

    public List<SurveyPeopleTO> getSurveyAndPeopleList(PaginationModel paginationModel){
        String query = "select SP.PERSON_ID, SUR.Member_Name, SUR.Mobile_No , SUR.District  , SUR.Block , SP.STATUS_DESC from [dbo].[TBL_T_SURVEY_POPULATION_PERSON] SP " +
                "JOIN [dbo].[TBL_T_SURVEY_POPULATION_ASSOCIATION] SPA ON SP.SURVEY_ID = SPA.SURVEY_ID " +
                "JOIN [dbo].[Survey] SUR ON  SP.PERSON_ID = SUR.ID " +
                "WHERE SP.SURVEY_ID = "+paginationModel.getSurveyId()+
                " order by SP.ID "+
                "OFFSET "+paginationModel.getNumberOfRows()*paginationModel.getPageNumber()+" ROWS "+
                "FETCH NEXT "+paginationModel.getNumberOfRows()+" ROWS ONLY ";

        List<SurveyPeopleTO> surveyCampaignList = jdbcTemplate.query(query,new SurveyPeopleRowMapper());
        return surveyCampaignList;
    }

    public Integer countOfRecordsForSurveyPeople(int id){
        String query = "select count(PERSON_ID) from [dbo].[TBL_T_SURVEY_POPULATION_PERSON] WHERE SURVEY_ID = "+id;
        return jdbcTemplate.queryForObject(query,new Object[]{}, Integer.class);
    };


    public void insertNewSurveyAnswer(int sId, int pId, int qId, String anwer){
        String query = "INSERT INTO [dbo].[TBL_T_SURVEY_ANSWER] (SURVEY_ID, PERSON_ID, QUESTION_ID, ANSWER) VALUES (?,?,?,?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1,sId);
                ps.setInt(2,pId);
                ps.setInt(3,qId);
                ps.setString(4,anwer);
             return ps;
            }
        }, holder);
    };


    public void updateSurveyPersonTableToClosed(int sId, int pId){
       String query = "UPDATE [dbo].[TBL_T_SURVEY_POPULATION_PERSON] SET STATUS_DESC = '"+Constants.CLOSED+"' WHERE SURVEY_ID = "+sId+" AND PERSON_ID = "+pId;
       jdbcTemplate.execute(query);
    }

    public Integer checkIfSurveyClosed(int sId, int pId){
        String query = "select count(PERSON_ID) from [dbo].[TBL_T_SURVEY_POPULATION_PERSON] WHERE STATUS_DESC ='"+Constants.CLOSED+"' AND SURVEY_ID = "+sId+" AND PERSON_ID = "+pId;
        return jdbcTemplate.queryForObject(query,new Object[]{}, Integer.class);
    };

}

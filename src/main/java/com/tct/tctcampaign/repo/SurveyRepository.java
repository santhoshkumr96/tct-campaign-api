package com.tct.tctcampaign.repo;

import com.tct.tctcampaign.constants.Constants;
import com.tct.tctcampaign.model.db.Survey;
import com.tct.tctcampaign.model.request.PaginationModel;
import com.tct.tctcampaign.population.PopulationPaginationModel;
import com.tct.tctcampaign.population.QuestionnairePopulationEntity;
import com.tct.tctcampaign.population.QuestionnairePopulationRowMapper;
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
        String query = "INSERT INTO TBL_T_SURVEY_POPULATION_PERSON (SURVEY_ID, PERSON_ID) " +
                "SELECT " +
                surveyId+" as SURVEY_ID, " +
                " ID " +
                "FROM " +
                " [dbo].[Survey] " +
                "WHERE " +
                paginationModel.getSqlCondition();
        System.out.println(query);
        jdbcTemplate.execute(query);
    }


}

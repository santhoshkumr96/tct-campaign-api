package com.tct.tctcampaign.repo;

import com.tct.tctcampaign.model.db.Survey;
import com.tct.tctcampaign.model.request.PaginationModel;
import com.tct.tctcampaign.population.PopulationPaginationModel;
import com.tct.tctcampaign.population.QuestionnairePopulationEntity;
import com.tct.tctcampaign.population.QuestionnairePopulationRowMapper;
import com.tct.tctcampaign.rowmapper.SurveyRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SurveyRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Survey> findAll(){
        String query = "select top 10 * from [dbo].[Survey]";

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
                "order by ROW_ID "+
                "OFFSET "+paginationModel.getNumberOfRows()*paginationModel.getPageNumber()+" ROWS "+
                "FETCH NEXT "+paginationModel.getNumberOfRows()+" ROWS ONLY ";

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

}

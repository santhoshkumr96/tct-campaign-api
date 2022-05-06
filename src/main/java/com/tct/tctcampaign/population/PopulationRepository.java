package com.tct.tctcampaign.population;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PopulationRepository{

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<QuestionnairePopulationEntity> findAll(){
        String query = "select * from [dbo].[TBL_T_QUESTIONNAIRE_POPULATION]";
        List<QuestionnairePopulationEntity> questionnairePopulationEntities = jdbcTemplate.query(query,new QuestionnairePopulationRowMapper());

        return questionnairePopulationEntities;
    };

    public List<QuestionnairePopulationEntity> findAllWithQuery(PopulationPaginationModel populationPaginationModel ){
        String query = "select * from [dbo].[TBL_T_QUESTIONNAIRE_POPULATION] "+
                "order by ROW_ID "+
                "OFFSET "+populationPaginationModel.getNumberOfRows()*populationPaginationModel.getPageNumber()+" ROWS "+
                "FETCH NEXT "+populationPaginationModel.getNumberOfRows()+" ROWS ONLY ";

        List<QuestionnairePopulationEntity> questionnairePopulationEntities = jdbcTemplate.query(query,new QuestionnairePopulationRowMapper());
        return questionnairePopulationEntities;
    };

    public List<QuestionnairePopulationEntity> findAllWithQueryWithClause(PopulationPaginationModel populationPaginationModel ){
        String query = "select * from [dbo].[TBL_T_QUESTIONNAIRE_POPULATION] where "+ populationPaginationModel.getSqlCondition() + " "+
                "order by ROW_ID "+
                "OFFSET "+populationPaginationModel.getNumberOfRows()*populationPaginationModel.getPageNumber()+" ROWS "+
                "FETCH NEXT "+populationPaginationModel.getNumberOfRows()+" ROWS ONLY ";

        List<QuestionnairePopulationEntity> questionnairePopulationEntities = jdbcTemplate.query(query,new QuestionnairePopulationRowMapper());
        return questionnairePopulationEntities;
    };

    public Integer countOfRecordsWithQuery(String clause){
        String query = "select * from [dbo].[TBL_T_QUESTIONNAIRE_POPULATION] where "+ clause;
        List<QuestionnairePopulationEntity> questionnairePopulationEntities = jdbcTemplate.query(query,new QuestionnairePopulationRowMapper());
        return questionnairePopulationEntities.size();
    };

    public Integer countOfRecords(String clause){
        String query = "select * from [dbo].[TBL_T_QUESTIONNAIRE_POPULATION]";
        List<QuestionnairePopulationEntity> questionnairePopulationEntities = jdbcTemplate.query(query,new QuestionnairePopulationRowMapper());

        return questionnairePopulationEntities.size();
    };


    public void setCampaignId(PopulationPaginationModel populationPaginationModel){
        String query = "update  [dbo].[TBL_T_QUESTIONNAIRE_POPULATION] "+
                "set CAMPAIGN_ID =" + populationPaginationModel.getCampaignId()+" "+
                "where "+populationPaginationModel.getSqlCondition();
        jdbcTemplate.execute(query);
    }

}

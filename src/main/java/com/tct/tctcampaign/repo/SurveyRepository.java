package com.tct.tctcampaign.repo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tct.tctcampaign.constants.Constants;
import com.tct.tctcampaign.model.db.Survey;
import com.tct.tctcampaign.model.request.PaginationModel;
import com.tct.tctcampaign.model.response.SurveyAnswerTO;
import com.tct.tctcampaign.model.response.SurveyCampaignTO;
import com.tct.tctcampaign.model.response.SurveyPeopleTO;
import com.tct.tctcampaign.rowmapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

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


    public void insertNewSurveyAnswer(int sId, int pId, int qId, String anwer, Integer uniqueEntry){
        String query = "INSERT INTO [dbo].[TBL_T_SURVEY_ANSWER] (SURVEY_ID, PERSON_ID, QUESTION_ID, ANSWER,  CREATED_DATE, unique_entry) VALUES (?,?,?,?,?,?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1,sId);
                ps.setInt(2,pId);
                ps.setInt(3,qId);
                ps.setString(4,anwer);
                ps.setTimestamp(5,new java.sql.Timestamp(new Date().getTime()));
                ps.setInt(6,uniqueEntry);
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


    public List<Map<String, Object>> getSurveyAnswer(int sId){
        List<SurveyAnswerTO> questionColumnList = getQuestionsInAnswerColumn(sId);
        String questionColumn = "";
        for (SurveyAnswerTO surveyAnswerTO: questionColumnList){
            questionColumn += "[" +surveyAnswerTO.getQuestionName()+"],";
        }
        String personColumns = "[membername],[Form_no],[District],[Taluk],[Block],[Panchayat],[Area_Code],[Village_Code],[Village],[Street_Name],[Door_No],[Respondent_Name],[Mobile_No]";


        String queryActual ="WITH expression_name ( membername, Form_no,District,Taluk,Block,Panchayat,Area_Code,Village_Code,Village,Street_Name,Door_No,Respondent_Name,Mobile_No, questionname, answer, unique_entry )\n" +
                "AS \n" +
                "( \n" +
                "\tselect SUR.Member_Name,SUR.[Form_no],SUR.[District],SUR.[Taluk],SUR.[Block],SUR.[Panchayat],SUR.[Area_Code],SUR.[Village_Code],SUR.[Village],SUR.[Street_Name],SUR.[Door_No],SUR.[Respondent_Name],SUR.[Mobile_No] ,\n" +
                "\tQA.question_name ,SA.Answer , SA.unique_entry\n" +
                "\tfrom [dbo].[TBL_T_SURVEY_ANSWER] SA\n" +
                "\tJOIN [dbo].[TBL_M_QUESTIONS_REPO] QA ON SA.QUESTION_ID = QA.question_id AND SA.SURVEY_ID = "+sId+"\n" +
                "\tJOIN [dbo].[Survey] SUR ON SUR.ID = SA.PERSON_ID\n" +
                ")\n" +
                "\n" +
                "SELECT "+questionColumn+personColumns+
                "FROM (\n" +
                "    SELECT  * FROM expression_name\n" +
                ") as t\n" +
                "PIVOT (\n" +
                "    MAX(answer) FOR questionname IN ("+removeLastCharacter(questionColumn,1)+")\n" +
                ") as pvt";

//        System.out.println(queryActual);

       return jdbcTemplate.queryForList(queryActual);

//        try {
//            System.out.println(new ObjectMapper().writeValueAsString(resultSet));
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//        String query = "select SUR.Member_Name ,QA.question_name ,SA.Answer  from [dbo].[TBL_T_SURVEY_ANSWER] SA" +
//                " JOIN [dbo].[TBL_M_QUESTIONS_REPO] QA ON SA.QUESTION_ID = QA.question_id AND SA.SURVEY_ID = " +sId+
//                " JOIN [dbo].[Survey] SUR ON SUR.ID = SA.PERSON_ID";
//        List<SurveyAnswerTO> surveyCampaignList = jdbcTemplate.query(query,new SurveyAnswerRowMapper());
//        return surveyCampaignList;
    }

    private String removeLastCharacter(String str, int chars){
        return str.substring(0, str.length() - chars);
    }

    private List<SurveyAnswerTO> getQuestionsInAnswerColumn(int sId){
        String query = "select DISTINCT QA.question_name from [dbo].[TBL_T_SURVEY_ANSWER] SA" +
                " JOIN [dbo].[TBL_M_QUESTIONS_REPO] QA ON SA.QUESTION_ID = QA.question_id AND SA.SURVEY_ID = " +sId+
                " JOIN [dbo].[Survey] SUR ON SUR.ID = SA.PERSON_ID";
        List<SurveyAnswerTO> surveyCampaignList = jdbcTemplate.query(query,new SurveyQuestionColumnRowMapper());
        return surveyCampaignList;
    }

    public Integer getUniqueSurveyAnswerValue(){
        String query = "select max(unique_entry) from [dbo].[TBL_T_SURVEY_ANSWER]";
        return jdbcTemplate.queryForObject(query,new Object[]{}, Integer.class);
    }

}

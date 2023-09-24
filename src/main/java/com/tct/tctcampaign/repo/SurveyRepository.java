package com.tct.tctcampaign.repo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tct.tctcampaign.constants.Constants;
import com.tct.tctcampaign.model.db.Survey;
import com.tct.tctcampaign.model.db.SurveyPopulationCampaignAssociation;
import com.tct.tctcampaign.model.db.SurveyView;
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
import java.util.Optional;

@Repository
public class SurveyRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    SurveyPopulationCampaignAssociationRepo surveyPopulationCampaignAssociationRepo;

    private static String COLUMN_NAME = "FD.formNo,FD.projectCode,FD.locationDetails,FD.villageCode,FD.panchayatNo,FD.panchayatCode,FD.villageName,FD.[zone],FD.streetName,FD.doorNo,FD.contactPerson,FD.numberOfFamilyMembers,FD.statusOfHouse,FD.typeOfHouse,FD.toiletFacilityAtHome,FD.ownsAnyLand,FD.wetLandInAcres,FD.dryLandInAcres,FD.ownsAnyVechicles,FD.noOfVechiclesOwned,FD.twoWheeler,FD.threeWheeler,FD.fourWheeler,FD.[others],FD.ownsAnyLiveStocks,FD.hen,FD.cow,FD.pig,FD.buffalo,FD.goat,FD.othersLiveStocks,FD.livestockCount,FD.isCompleted,FD.actualMemberCount,FD.familyHeadName,MD.id,MD.name,MD.aadhaarNumber,MD.relationShip,MD.gender,MD.dateOfBirth,MD.memberAge,MD.bloodGroup,MD.physicallyChallenged,MD.physicallyChallengedReason,MD.maritalStatus,MD.educationQualification,MD.occupation,MD.annualIncome,MD.mobileNumber,MD.email,MD.smartPhone,MD.communtiy,MD.caste,MD.govtInsurance,MD.healthInsurance,MD.oldAgePension,MD.widowedPension,MD.retirementPension,MD.smoking,MD.drinking,MD.tobacco,MD.vacinattionDone,MD.firstDose,MD.secondDose,MD.patientId";

    public List<Survey> findAll(){
        String query = "select "+COLUMN_NAME+" from [demographic].[dbo].[formDetails] FD join [demographic].[dbo].[memberDetails] MD on MD.formId = FD.id where FD.is_deleted ='N' and MD.is_deleted ='N' ";

        List<Survey> surveyList = jdbcTemplate.query(query,new SurveyRowMapper());
        return surveyList;
    };

    public List<SurveyView> findAllWithQuery(PaginationModel paginationModel ){
        String query = "select * from demographics.demographics_view "+
                "LIMIT "+paginationModel.getNumberOfRows()+
                " OFFSET "+paginationModel.getNumberOfRows()*paginationModel.getPageNumber();

        List<SurveyView> surveyList = jdbcTemplate.query(query,new SurveyViewRowMapper());
        return surveyList;
    };

    public List<SurveyView> findAllWithQueryWithClause(PaginationModel paginationModel ){
            String query = "select * from demographics.demographics_view where "+ paginationModel.getSqlCondition() + " "+
                "LIMIT "+paginationModel.getNumberOfRows()+
                " OFFSET "+paginationModel.getNumberOfRows()*paginationModel.getPageNumber();

        System.out.println("the pagination data query ====> "+ query);

        List<SurveyView> surveyList = jdbcTemplate.query(query,new SurveyViewRowMapper());
        return surveyList;
    };

    public List<Survey> findAllOnlyWithClauseWithoutPagination(PaginationModel paginationModel ){
        String query = "select "+COLUMN_NAME+" from [demographic].[dbo].[formDetails] FD join [demographic].[dbo].[memberDetails] MD on MD.formId = FD.id where FD.is_deleted ='N' and MD.is_deleted ='N' and "+ paginationModel.getSqlCondition() + " "+
                "order by MD.id ";
        List<Survey> surveyList = jdbcTemplate.query(query,new SurveyRowMapper());
        return surveyList;
    };

    public Integer countOfRecordsWithQuery(String clause){
        String query = "select count(*) from demographics.demographics_view where "+ clause;
        System.out.println("the count query ====> "+ query);
        return jdbcTemplate.queryForObject(query,new Object[]{}, Integer.class);
    };

    public Integer countOfRecords(){
        String query = "select count(*) from demographics.demographics_view";
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
                " MD.id , '" +
                Constants.OPEN+ "' as STATUS_DESC "+
                "FROM " +
                "[demographic].[dbo].[formDetails] FD join [demographic].[dbo].[memberDetails] MD on MD.formId = FD.id where FD.is_deleted ='N' and MD.is_deleted ='N' and " +
                paginationModel.getSqlCondition();
        jdbcTemplate.execute(query);
    }

    public List<SurveyCampaignTO> getSurveyAndCampaign(){
        String query = "select SPA.id as 'survey_id', SPA.survey_name, CAM.campaign_id , CAM.campaign_name , SPA.created_by  " +
                "from survey_population_campaign_association SPA " +
                "join tbl_t_campaign CAM on SPA.campaign_id = CAM.campaign_id";
        List<SurveyCampaignTO> surveyCampaignList = jdbcTemplate.query(query,new SurveyCampaignRowMapper());
        return surveyCampaignList;
    }

    public List<SurveyPeopleTO> getSurveyAndPeopleList(PaginationModel paginationModel){
        Long surveyId = new Long(paginationModel.getSurveyId());
        Optional<SurveyPopulationCampaignAssociation> clause = surveyPopulationCampaignAssociationRepo.findById(surveyId);
        String clauseString  = clause.get().getClause();

        System.out.println("the clauseString ====> "+ clauseString);

        String query = "select member_id as 'person_id', member_name,mobile_number,village_name,village_code as 'panchayatCode' from demographics.demographics_view where "+ clauseString + " " +
                "LIMIT "+paginationModel.getNumberOfRows()+
                " OFFSET "+paginationModel.getNumberOfRows()*paginationModel.getPageNumber();

        System.out.println("the claus equery ====> "+ query);

        List<SurveyPeopleTO> surveyCampaignList = jdbcTemplate.query(query,new SurveyPeopleRowMapper());
        return surveyCampaignList;
    }

    public Integer countOfRecordsForSurveyPeople(int id){
        Long surveyId = new Long(id);
        Optional<SurveyPopulationCampaignAssociation> clause = surveyPopulationCampaignAssociationRepo.findById(surveyId);
        String clauseString  = clause.get().getClause();
        String query = "select count(*) from demographics.demographics_view where "+ clauseString ;
        return jdbcTemplate.queryForObject(query,new Object[]{}, Integer.class);
    };


    public void insertNewSurveyAnswer(int sId, String pId, int qId, String anwer, Integer uniqueEntry){
        String query = "insert into tbl_t_survey_answer (survey_id, person_id, question_id, answer, unique_entry) values (?,?,?,?,?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1,sId);
                ps.setString(2,pId);
                ps.setInt(3,qId);
                ps.setString(4,anwer);
                ps.setInt(5,uniqueEntry);
                return ps;
            }
        }, holder);
    };


    public void updateSurveyPersonTableToClosed(int sId, String pId){
        String query = "UPDATE [dbo].[TBL_T_SURVEY_POPULATION_PERSON] SET STATUS_DESC = '"+Constants.CLOSED+"' WHERE SURVEY_ID = "+sId+" AND PERSON_ID = '"+pId+"'";
        jdbcTemplate.execute(query);
    }

    public Integer checkIfSurveyClosed(int sId, String pId){
        String query = "select count(PERSON_ID) from [dbo].[TBL_T_SURVEY_POPULATION_PERSON] WHERE STATUS_DESC ='"+Constants.CLOSED+"' AND SURVEY_ID = "+sId+" AND PERSON_ID = '"+pId+"'";
        return jdbcTemplate.queryForObject(query,new Object[]{}, Integer.class);
    };


    public List<Map<String, Object>> getSurveyAnswer(int sId){

        String query = "select question_id from tbl_t_campaign_questionnaire where campaign_id = (select campaign_id from survey_population_campaign_association where id = "+sId+") order by question_id ";
        List<Integer> questionId = jdbcTemplate.queryForList(query,Integer.class);
        String pivotClause = "";
        for(Integer qId : questionId){
            pivotClause = pivotClause + " MAX(CASE WHEN question_id = "+qId+" THEN answer END) AS '"+qId+"',";
        }
        String pivotQuery = "WITH pivot_questions AS ( "+
                "SELECT "+pivotClause+" person_id FROM tbl_t_survey_answer  where survey_id = "+
                sId + " group by person_id ) select * from pivot_questions PQ join demographics.demographics_view DV on DV.member_id = PQ.person_id ";
        return jdbcTemplate.queryForList(pivotQuery);
    }

    private String removeLastCharacter(String str, int chars){
        return str.substring(0, str.length() - chars);
    }

    private List<SurveyAnswerTO> getQuestionsInAnswerColumn(int sId){
        String query = "select DISTINCT QA.question_name from [dbo].[TBL_T_SURVEY_ANSWER] SA" +
                " JOIN [dbo].[TBL_M_QUESTIONS_REPO] QA ON SA.QUESTION_ID = QA.question_id AND SA.SURVEY_ID = " +sId+
                " join [demographic].[dbo].[memberDetails] MD on MD.id = SA.PERSON_ID " +
                " JOIN [demographic].[dbo].[formDetails] FD  on MD.formId = FD.id " +
                " WHERE FD.is_deleted ='N' and MD.is_deleted ='N'";
//       System.out.println(query);
        List<SurveyAnswerTO> surveyCampaignList = jdbcTemplate.query(query,new SurveyQuestionColumnRowMapper());
        return surveyCampaignList;
    }

    public Integer getUniqueSurveyAnswerValue(){
        String query = "select max(unique_entry) from tbl_t_survey_answer";
        return jdbcTemplate.queryForObject(query,new Object[]{}, Integer.class);
    }

}

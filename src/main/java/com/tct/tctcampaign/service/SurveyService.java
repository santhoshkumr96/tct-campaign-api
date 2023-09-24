package com.tct.tctcampaign.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tct.tctcampaign.constants.Constants;
import com.tct.tctcampaign.errorhandler.InvalidCampaignIdException;
import com.tct.tctcampaign.model.db.SurveyPopulationCampaignAssociation;
import com.tct.tctcampaign.model.request.PaginationModel;
import com.tct.tctcampaign.model.request.SurveyAnswerModel;
import com.tct.tctcampaign.repo.CampaignRepository;
import com.tct.tctcampaign.repo.SurveyPopulationCampaignAssociationRepo;
import com.tct.tctcampaign.repo.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
public class SurveyService {

    @Autowired
    SurveyRepository surveyRepository;

    @Autowired
    CampaignRepository campaignRepository;

    @Autowired
    SurveyPopulationCampaignAssociationRepo surveyPopulationCampaignAssociationRepo;

    public void setSurveyToPopulationAssociation(PaginationModel paginationModel) throws Exception{
        if(campaignRepository.checkIfValidCampaignId(paginationModel.getCampaignId()) > 0){
//            int surveyId =surveyRepository.insertNewSurveyPopulationAssociation(paginationModel,getUserNameFromContext());
//            surveyRepository.insertToSurveyPeopleAssociation(surveyId,paginationModel);
            SurveyPopulationCampaignAssociation surveyPopulationCampaignAssociation = new SurveyPopulationCampaignAssociation();
            surveyPopulationCampaignAssociation.setCampaignId(paginationModel.getCampaignId());
            surveyPopulationCampaignAssociation.setSurveyName(paginationModel.getSurveyName());
            surveyPopulationCampaignAssociation.setCreatedBy(getUserNameFromContext());
            surveyPopulationCampaignAssociation.setClause(paginationModel.getSqlCondition());
            surveyPopulationCampaignAssociation.setStatus(Constants.OPEN);
            surveyPopulationCampaignAssociationRepo.save(surveyPopulationCampaignAssociation);
            return;
        }
        throw new InvalidCampaignIdException("Enter valid campaign Id");
    }

    private String getUserNameFromContext(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();

        return username;
    }

    public void setSurveyAnswer(SurveyAnswerModel surveyAnswerModel) throws Exception{
        Integer uniqueEntry = surveyRepository.getUniqueSurveyAnswerValue();
        for (Map.Entry<Integer, String> pair : surveyAnswerModel.getData().entrySet()) {
            surveyRepository.insertNewSurveyAnswer(
                    surveyAnswerModel.getSurveyId(),
                    surveyAnswerModel.getPersonId(),
                    pair.getKey(),
                    pair.getValue(),
                    Objects.isNull(uniqueEntry)?1:(uniqueEntry+1)
            );
        }
//        surveyRepository.updateSurveyPersonTableToClosed(surveyAnswerModel.getSurveyId(),surveyAnswerModel.getPersonId());
    }
}

package com.tct.tctcampaign.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tct.tctcampaign.errorhandler.InvalidCampaignIdException;
import com.tct.tctcampaign.model.request.PaginationModel;
import com.tct.tctcampaign.model.request.SurveyAnswerModel;
import com.tct.tctcampaign.repo.CampaignRepository;
import com.tct.tctcampaign.repo.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SurveyService {

    @Autowired
    SurveyRepository surveyRepository;

    @Autowired
    CampaignRepository campaignRepository;

    public void setSurveyToPopulationAssociation(PaginationModel paginationModel) throws Exception{
        if(campaignRepository.checkIfValidCampaignId(paginationModel.getCampaignId()) > 0){
            int surveyId =surveyRepository.insertNewSurveyPopulationAssociation(paginationModel,getUserNameFromContext());
            surveyRepository.insertToSurveyPeopleAssociation(surveyId,paginationModel);
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
        for (Map.Entry<Integer, String> pair : surveyAnswerModel.getData().entrySet()) {
            surveyRepository.insertNewSurveyAnswer(
                    surveyAnswerModel.getSurveyId(),
                    surveyAnswerModel.getPersonId(),
                    pair.getKey(),
                    pair.getValue()
            );
        }
        surveyRepository.updateSurveyPersonTableToClosed(surveyAnswerModel.getSurveyId(),surveyAnswerModel.getPersonId());
    }
}

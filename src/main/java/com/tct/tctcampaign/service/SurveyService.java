package com.tct.tctcampaign.service;

import com.tct.tctcampaign.errorhandler.InvalidCampaignIdException;
import com.tct.tctcampaign.model.request.PaginationModel;
import com.tct.tctcampaign.repo.CampaignRepository;
import com.tct.tctcampaign.repo.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

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
}

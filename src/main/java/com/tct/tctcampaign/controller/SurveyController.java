package com.tct.tctcampaign.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tct.tctcampaign.model.db.CampaignAnswerDao;
import com.tct.tctcampaign.model.db.CampaignDao;
import com.tct.tctcampaign.model.request.PaginationModel;
import com.tct.tctcampaign.model.response.CampaignTO;
import com.tct.tctcampaign.model.response.CampaignViewTO;
import com.tct.tctcampaign.population.PopulationPaginationModel;
import com.tct.tctcampaign.repo.SurveyRepository;
import com.tct.tctcampaign.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class SurveyController {

    @Autowired
    SurveyRepository surveyRepository;

    @GetMapping("/v1/getAllSurveyList")
    @PreAuthorize("hasRole('USER')")
    public Object getAllSurvey() throws JsonProcessingException {
        return surveyRepository.findAll();
    }

    @PostMapping("/v1/get-survey-count")
    public Integer getCount(@RequestBody PaginationModel paginationModel) throws Exception {
        if(Objects.isNull(paginationModel.getSqlCondition()) ||
                paginationModel.getSqlCondition().isEmpty() ||
                paginationModel.getSqlCondition() == ""
        ){
            return surveyRepository.countOfRecords();
        }
        return surveyRepository.countOfRecordsWithQuery(paginationModel.getSqlCondition());
    }

    @PostMapping("/v1/survey-data-pagination")
    public Object getDataPagniated(@RequestBody PaginationModel paginationModel) throws Exception {
        if(Objects.isNull(paginationModel.getSqlCondition()) ||
                paginationModel.getSqlCondition().isEmpty() ||
                paginationModel.getSqlCondition() == ""
        ) {
            return surveyRepository.findAllWithQuery(paginationModel);
        }
        return surveyRepository.findAllWithQueryWithClause(paginationModel);
    }

}

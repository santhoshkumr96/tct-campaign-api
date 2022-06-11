package com.tct.tctcampaign.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tct.tctcampaign.model.db.CampaignAnswerDao;
import com.tct.tctcampaign.model.db.CampaignDao;
import com.tct.tctcampaign.model.db.Survey;
import com.tct.tctcampaign.model.request.PaginationModel;
import com.tct.tctcampaign.model.response.CampaignTO;
import com.tct.tctcampaign.model.response.CampaignViewTO;
import com.tct.tctcampaign.population.PopulationPaginationModel;
import com.tct.tctcampaign.population.QuestionnairePopulationEntity;
import com.tct.tctcampaign.repo.SurveyRepository;
import com.tct.tctcampaign.service.CampaignService;
import com.tct.tctcampaign.service.SurveyService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class SurveyController {

    @Autowired
    SurveyRepository surveyRepository;

    @Autowired
    SurveyService surveyService;

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

    @PostMapping("/v1/set-survey-population-campaign")
    public void setSurveyToPopulationAssociation(@RequestBody PaginationModel paginationModel) throws Exception {
        surveyService.setSurveyToPopulationAssociation(paginationModel);
    }

    @GetMapping("/v1/get-survey-campaign-list")
    public Object getSurveyCampaign() throws Exception {
        return surveyRepository.getSurveyAndCampaign();
    }

    @PostMapping("/v1/get-survey-people-list")
    public Object getSurveyPeople(@RequestBody PaginationModel paginationModel) throws Exception {
        return surveyRepository.getSurveyAndPeopleList(paginationModel.getSurveyId());
    }

    @PostMapping("/v1/survey-download")
    public ResponseEntity<Resource> getFile(@RequestBody PaginationModel paginationModel){
        List<Survey> questionnairePopulationEntities = new ArrayList<>();

        if(Objects.isNull(paginationModel.getSqlCondition()) ||
                paginationModel.getSqlCondition().isEmpty() ||
                paginationModel.getSqlCondition() == ""
        ) {
            System.out.println("coming here");
            questionnairePopulationEntities = surveyRepository.findAll();
        } else {
            questionnairePopulationEntities = surveyRepository.findAllOnlyWithClauseWithoutPagination(paginationModel);
        }

        ByteArrayInputStream in = null;

        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.ALL.MINIMAL);
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {

            List<String> Header = Arrays.asList(
                    "Id",
                    "Member Name"
            );

            csvPrinter.printRecord(Header);

            for (Survey population : questionnairePopulationEntities) {
                List<String> data = Arrays.asList(
                        String.valueOf(population.getId()),
                        population.getMemberName()
                );

                csvPrinter.printRecord(data);
            }

            csvPrinter.flush();
            in = new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }

        InputStreamResource file = new InputStreamResource(in);

        String csvFileName = "people.csv";

        // setting HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + csvFileName);
        // defining the custom Content-Type
        headers.set(HttpHeaders.CONTENT_TYPE, "text/csv");

        return new ResponseEntity<>(
                file,
                headers,
                HttpStatus.OK
        );
    }

}

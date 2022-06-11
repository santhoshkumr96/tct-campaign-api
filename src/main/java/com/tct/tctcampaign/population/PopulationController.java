package com.tct.tctcampaign.population;


import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class PopulationController {

    @Autowired
    PopulationRepository populationRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @PostMapping("/v1/get-population-count")
    public Integer getCount(@RequestBody PopulationPaginationModel populationPaginationModel) throws Exception {
        if(Objects.isNull(populationPaginationModel.getSqlCondition()) ||
                populationPaginationModel.getSqlCondition().isEmpty() ||
                populationPaginationModel.getSqlCondition() == ""
        ){
            return populationRepository.countOfRecords(populationPaginationModel.getSqlCondition());
        }
        return populationRepository.countOfRecordsWithQuery(populationPaginationModel.getSqlCondition());
    }


    @PostMapping("/v1/set-population-campaign")
    public void set(@RequestBody PopulationPaginationModel populationPaginationModel) throws Exception {
        populationRepository.setCampaignId(populationPaginationModel);
    }

    @PostMapping("/v1/population-data-pagination")
    public Object getDataPagniated(@RequestBody PopulationPaginationModel populationPaginationModel) throws Exception {
      if(Objects.isNull(populationPaginationModel.getSqlCondition()) ||
                populationPaginationModel.getSqlCondition().isEmpty() ||
                populationPaginationModel.getSqlCondition() == ""
        ) {
            return populationRepository.findAllWithQuery(populationPaginationModel);
        }
        return populationRepository.findAllWithQueryWithClause(populationPaginationModel);
    }

    @PostMapping("/v1/download")
    public ResponseEntity<Resource> getFile(@RequestBody PopulationPaginationModel populationPaginationModel){
          List<QuestionnairePopulationEntity> questionnairePopulationEntities = new ArrayList<>();
          questionnairePopulationEntities = populationRepository.findAll();


        ByteArrayInputStream in = null;

        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.ALL.MINIMAL);
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {

            List<String> Header = Arrays.asList(
                    "Row Id",
                    "Village Code"
            );

            csvPrinter.printRecord(Header);

            for (QuestionnairePopulationEntity population : questionnairePopulationEntities) {
                List<String> data = Arrays.asList(
                        String.valueOf(population.getRowId()),
                        population.getVillageCode()
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

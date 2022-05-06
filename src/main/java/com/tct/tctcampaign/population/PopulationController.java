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

//    @GetMapping("/v1/populationdata")
//    public Object getData() throws Exception {
//        return populationRepository.findAll()
//                .stream().map(e -> e.toView()).collect(Collectors.toList());
//    }

    @PostMapping("/v1/get-population-count")
    public Integer getCount(@RequestBody PopulationPaginationModel populationPaginationModel) throws Exception {
        if(Objects.isNull(populationPaginationModel.getSqlCondition()) ||
                populationPaginationModel.getSqlCondition().isEmpty() ||
                populationPaginationModel.getSqlCondition() == ""
        ){
            return populationRepository.countOfRecords(populationPaginationModel.getSqlCondition());
        }
//        return jdbcTemplate.queryForObject("select count(*) from demographic_data where "+populationPaginationModel.getSqlCondition(),Integer.class);
////        return populationRepository.countOfRecordsWithQuery(populationPaginationModel.getSqlCondition());
        return populationRepository.countOfRecordsWithQuery(populationPaginationModel.getSqlCondition());
    }


    @PostMapping("/v1/set-population-campaign")
    public void set(@RequestBody PopulationPaginationModel populationPaginationModel) throws Exception {
        System.out.println(new ObjectMapper().writeValueAsString(populationPaginationModel));
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
//            return populationRepository.findAll(PageRequest.of(populationPaginationModel.getPageNumber(), populationPaginationModel.getNumberOfRows(), Sort.by("form_no")))
//                    .stream().map(e -> e.toView()).collect(Collectors.toList());
//        }
//
//        ObjectMapper mapper = new ObjectMapper();
//        String query = "select * from demographic_data where " +
//                populationPaginationModel.getSqlCondition() +
//                " limit "+ populationPaginationModel.getNumberOfRows();
//        if(populationPaginationModel.getPageNumber() > 0)
//            query = query +  " offset "+ (populationPaginationModel.getPageNumber() * populationPaginationModel.getPageNumber());
////        List<Population> populations = jdbcTemplate
////                .queryForList("select * from demographic_data where "+populationPaginationModel.getSqlCondition(),Population.class);
////        return populations.stream().map(e -> e.toView()).collect(Collectors.toList());
////        return jdbcTemplate.queryForList(query);
////                .stream()
////                .map(e ->mapper.convertValue(e,Population.class).toView())
////                .collect(Collectors.toList());
//        return jdbcTemplate.query(query,new PopulationRowMapper())
//                .stream()
//                .map(e ->mapper.convertValue(e, com.tcthospital.campain.population.Population.class).toView())
//                .collect(Collectors.toList());
        return populationRepository.findAllWithQueryWithClause(populationPaginationModel);
    }

//    @GetMapping("/v1/populationpush")
//    public String testApiConnection() throws Exception {
//        InputStream serviceAccount = new FileInputStream("src/main/resources/google_service_account/tct-app-ca81f-3a9304d6e4f2.json");
//        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
//        FirebaseOptions options = new FirebaseOptions.Builder()
//                .setCredentials(credentials)
//                .setProjectId("tct-app-ca81f")
//                .build();
//        FirebaseApp.initializeApp(options);
//
//        Firestore db = FirestoreClient.getFirestore();
//
//        ApiFuture<QuerySnapshot> query = db
//                .collection("demographicData")
////                .whereIn("Location.taluk", Arrays.asList("KATPADI"))
////                .whereIn("Location.contactPerson", Arrays.asList("Neesam. C"))
////                .whereArrayContains("Location.taluk", Arrays.asList("KATPADI"))
////                .whereEqualTo("Location.taluk" , "KATPADI")
////                .whereEqualTo("Location.taluk", "Neesam. C")
//                .limit(1000)
//                .get();
//        QuerySnapshot querySnapshot = query.get();
//        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
//
//        int i =0 ;
//        for(QueryDocumentSnapshot document: documents){
//            com.tcthospital.campain.population.Population population = new com.tcthospital.campain.population.Population();
//            try{
//                population.setTaluk((String) document.get("Location.taluk"));
//                population.setBlock((String) document.get("Location.block"));
//                population.setFormNo(Integer.parseInt((String) document.get("Location.formNo")));
//                population.setContactNumber((String) document.get("Location.contactNumber"));
//                population.setContactPerson((String) document.get("Location.contactPerson"));
//                population.setDoorNumber((String) document.get("Location.doorNumber"));
//                population.setPanchayat((String) document.get("Location.panchayat"));
//                i++;
//                if(i%100 == 0){
//                    System.out.println("100 inserted");
//                }
//                populationRepository.save(population);
//            } catch (Exception e){
//                System.out.println(e.getMessage());
//            }
//
//
//        }
//        return "working";
//
//    }

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

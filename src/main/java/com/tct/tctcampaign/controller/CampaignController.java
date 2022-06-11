package com.tct.tctcampaign.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tct.tctcampaign.model.db.CampaignAnswerDao;
import com.tct.tctcampaign.model.db.CampaignDao;
import com.tct.tctcampaign.model.response.CampaignTO;
import com.tct.tctcampaign.model.response.CampaignViewTO;
import com.tct.tctcampaign.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class CampaignController {

    @Autowired
    CampaignService campaignService;

    @PostMapping("/v1/create-campaign")
    @PreAuthorize("hasRole('USER')")
    public void createCampaign(@RequestBody Object obj) throws JsonProcessingException {
        CampaignTO campaign = new ObjectMapper().readValue( new ObjectMapper().writeValueAsString(obj),CampaignTO.class);
        campaignService.createCampaign(campaign);
    }

    @PostMapping("/v1/edit-campaign")
    @PreAuthorize("hasRole('USER')")
    public void editCampaign(@RequestBody Object obj) throws JsonProcessingException {
        CampaignTO campaign = new ObjectMapper().readValue( new ObjectMapper().writeValueAsString(obj),CampaignTO.class);
        campaignService.editCampaign(campaign);
    }

    @GetMapping("/v1/get-all-campaign")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<CampaignViewTO> getCampaignList() {
        return campaignService.fetchAll();
//                .stream().map(e -> e.toView()).collect(Collectors.toList());
    }


    @PostMapping("/v1/delete-campaign")
    @PreAuthorize("hasRole('USER')")
    public void deleteCampign(@RequestBody CampaignDao campaignDao) {
         campaignService.deleteCampign(campaignDao.getCampaignId());
    }

    @PostMapping("/v1/view-campaign")
    public CampaignTO viewCampign(@RequestBody CampaignDao campaignDao) {
       return campaignService.viewCampaign(campaignDao.getCampaignId());
    }


    @PostMapping("/v1/campaign-answer-update")
    @PreAuthorize("hasRole('USER')")
    public void updateCampaignAnswer(@RequestBody List<CampaignAnswerDao> campaignAnswerDaos) throws JsonProcessingException {
        campaignService.insertCampainAnswers(campaignAnswerDaos);
    }


    @GetMapping("/v1/get-campaign-population")
    @PreAuthorize("hasRole('USER')")
    public List<CampaignAnswerDao> getCampaignPopulation(){
       return campaignService.getCampaignPopulation();
    }

    @PostMapping("/v1/udpate-campaign-status")
    @PreAuthorize("hasRole('ADMIN')")
    public void updateCampaignStatus(@RequestBody CampaignDao campaignDao){
        campaignService.updateCampaignStatus(campaignDao);
    }

}

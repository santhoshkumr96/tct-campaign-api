package com.tct.tctcampaign.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tct.tctcampaign.model.db.*;
import com.tct.tctcampaign.model.response.CampaignTO;
import com.tct.tctcampaign.model.response.CampaignViewTO;
import com.tct.tctcampaign.model.response.QuestionTO;
import com.tct.tctcampaign.model.response.SectionTO;
import com.tct.tctcampaign.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CampaignService {

    @Autowired
    private Validator validator;

    @Autowired
    ResponseRepository responseRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    CampaignAnswerRepository campaignAnswerRepository;

    @Autowired
    CampaignRepository campaignRepository;

    @Autowired
    SectionRepository sectionRepository;

    @Autowired
    CampaignQuestionRepository campaignQuestionRepository;

    @Autowired
    SectionConditionRepository sectionConditionRepository;

    public void createCampaign(CampaignTO campaignDetails){
        //saving campaign
        CampaignDao campaignDao = new CampaignDao(
                campaignDetails.getCampaignName(),
                campaignDetails.getCampaignDesc(),
                campaignDetails.getCampaignObjective(),
                new Date(),
                getUserNameFromContext()
        );
        Set<ConstraintViolation<CampaignDao>> validations = validator.validate(campaignDao);
        if(validations.size() > 0)
            throw new ValidationException("please check inputs");
        campaignDao.setChangedDate(new Date());
        campaignDao.setChangedBy(getUserNameFromContext());
        CampaignDao campaignDaoResponse = campaignRepository.insert(campaignDao);

        //saving section
        int sectionIndex = 1;
        for(SectionTO sectionTO : campaignDetails.getSections()){
            SectionDao sectionDao = new SectionDao(
                    sectionTO.getTitle(),
                    campaignDaoResponse.getCampaignId(),
                    getUserNameFromContext(),
                    new Date()
            );
            Set<ConstraintViolation<CampaignDao>> validationSection = validator.validate(campaignDao);
            if(validationSection.size() > 0)
                throw new ValidationException("please check inputs");
            sectionDao.setChangedBy(getUserNameFromContext());
            sectionDao.setChangedDate(new Date());
            sectionDao.setAfterSection(sectionTO.getAfterSection());
            SectionDao sectionDaoResponse = sectionRepository.save(sectionDao);

            //save section condition
            if (Objects.nonNull(sectionTO.getSectionCondition())){
                for (Map.Entry<Integer, Map<Integer,String>> pair : sectionTO.getSectionCondition().entrySet()) {
                    for (Map.Entry<Integer,String> basePair :  pair.getValue().entrySet()) {
                        sectionConditionRepository.save(campaignDao.getCampaignId(),
                                sectionDao.getSectionId(),
                                pair.getKey(),
                                basePair.getKey(),
                                basePair.getValue()
                        );
                    }
                }
            }

            //saving the questions in section
            for(QuestionTO questionTO : sectionTO.getQuestions()){
                CampainQuestionDao campainQuestionDao = new CampainQuestionDao(
                        sectionDaoResponse.getSectionId(),
                        questionTO.getQuestionId(),
                        campaignDaoResponse.getCampaignId(),
                        questionTO.getIsRequired()
                );
                campaignQuestionRepository.save(campainQuestionDao,sectionDao);
            }
        }
    }

    public void editCampaign(CampaignTO campaignDetails){

        campaignQuestionRepository.deleteByCampaignId(campaignDetails.getCampaignId(),getUserNameFromContext());
        sectionRepository.deleteByCampaignId(campaignDetails.getCampaignId(),getUserNameFromContext());
        sectionConditionRepository.deleteByCampaignId(campaignDetails.getCampaignId(),getUserNameFromContext());
        //saving campaign
        CampaignDao campaignDao = new CampaignDao(
                campaignDetails.getCampaignName(),
                campaignDetails.getCampaignDesc(),
                campaignDetails.getCampaignObjective(),
                new Date(),
                getUserNameFromContext()
        );
        campaignDao.setChangedDate(new Date());
        campaignDao.setCampaignId(campaignDetails.getCampaignId());
        Set<ConstraintViolation<CampaignDao>> validations = validator.validate(campaignDao);
        if(validations.size() > 0)
            throw new ValidationException("please check inputs");
        campaignDao.setChangedBy(getUserNameFromContext());
        campaignDao.setChangedDate(new Date());
        CampaignDao campaignDaoResponse = campaignRepository.update(campaignDao);

        //saving section
        for(SectionTO sectionTO : campaignDetails.getSections()){
            SectionDao sectionDao = new SectionDao(
                    sectionTO.getTitle(),
                    campaignDaoResponse.getCampaignId(),
                    getUserNameFromContext(),
                    new Date()
            );
            Set<ConstraintViolation<CampaignDao>> validationSection = validator.validate(campaignDao);
            if(validationSection.size() > 0)
                throw new ValidationException("please check inputs");
            sectionDao.setChangedBy(getUserNameFromContext());
            sectionDao.setChangedDate(new Date());
            sectionDao.setAfterSection(sectionTO.getAfterSection());
            SectionDao sectionDaoResponse = sectionRepository.save(sectionDao);


            //save section condition
            if (Objects.nonNull(sectionTO.getSectionCondition())){
                for (Map.Entry<Integer, Map<Integer,String>> pair : sectionTO.getSectionCondition().entrySet()) {
                    for (Map.Entry<Integer,String> basePair :  pair.getValue().entrySet()) {
                        sectionConditionRepository.save(campaignDao.getCampaignId(),
                                sectionDao.getSectionId(),
                                pair.getKey(),
                                basePair.getKey(),
                                basePair.getValue()
                        );
                    }
                }
            }

            //saving the questions in section
            for(QuestionTO questionTO : sectionTO.getQuestions()){
                CampainQuestionDao campainQuestionDao = new CampainQuestionDao(
                        sectionDaoResponse.getSectionId(),
                        questionTO.getQuestionId(),
                        campaignDaoResponse.getCampaignId(),
                        questionTO.getIsRequired()
                );
                campaignQuestionRepository.save(campainQuestionDao,sectionDao);
            }
        }
    }


    private String getUserNameFromContext(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();

        return username;
    }

    public List<CampaignViewTO> fetchAll() {
//        return campaignRepository.findAll();
          return campaignRepository.findAll().stream()
                  .map(e->e.toView()).collect(Collectors.toList());
    }

    public void deleteCampign(Integer campaignId) {
        campaignRepository.deleteByCampaignId(campaignId,getUserNameFromContext());
        campaignQuestionRepository.deleteByCampaignId(campaignId,getUserNameFromContext());
        sectionRepository.deleteByCampaignId(campaignId,getUserNameFromContext());
    }

    public CampaignTO viewCampaign(Integer campaignId) {
        CampaignTO campaign = new CampaignTO();
        CampaignDao campaignDao = campaignRepository.getByCampaignId(campaignId);
        campaign.setCampaignName(campaignDao.getCampaignName());
        campaign.setCampaignDesc(campaignDao.getCampaignDesc());
        campaign.setCampaignObjective(campaignDao.getCampaignObjective());
        campaign.setCampaignId(campaignDao.getCampaignId());
        List<SectionDao> sectionDaos = sectionRepository.getByCampaignId(campaignId);
        Collections.sort(sectionDaos, Comparator.comparing(SectionDao::getSectionId));
        List<SectionTO> sectionTOS = new ArrayList<>();
        for(SectionDao sectionDao: sectionDaos){
            SectionTO sectionTO = new SectionTO();
            sectionTO.setTitle(sectionDao.getSectionName());
            sectionTO.setSectionId(sectionDao.getSectionId());
            sectionTO.setAfterSection(sectionDao.getAfterSection());
            List<CampainQuestionDao> campainQuestionDaos = campaignQuestionRepository.getBySectionId(sectionDao.getSectionId());
            Collections.sort(campainQuestionDaos, Comparator.comparing(CampainQuestionDao::getQuestionOrder));
            List<QuestionTO> questionTOS = new ArrayList<>();
            for(CampainQuestionDao campainQuestionDao: campainQuestionDaos){
                Question question = questionRepository.findByQuestionId(campainQuestionDao.getQuestionId());
                QuestionTO questionTO =  question.toView();
                questionTO.setResponse(responseRepository.findByQuestionId(campainQuestionDao.getQuestionId()));
                questionTO.setIsRequired(campainQuestionDao.getIsRequired());
                questionTOS.add(questionTO);
            }
            sectionTO.setQuestions(questionTOS);

            //section conditions
            Map<Integer, Map<Integer,String>> sectionConditionMap = new HashMap<>();
            for(SectionConditionDao sectionConditionDao : sectionConditionRepository.getByCampaignIdAndSectionId(campaignId,sectionTO.getSectionId())){
                if (sectionConditionMap.containsKey(sectionConditionDao.getQuestionId())){
                    Map<Integer, String> temp = sectionConditionMap.get(sectionConditionDao.getQuestionId());
                    temp.put(sectionConditionDao.getResponseId(), sectionConditionDao.getSectionNameToGo());
                } else {
                    Map<Integer, String> temp = new HashMap<>();
                    temp.put(sectionConditionDao.getResponseId(), sectionConditionDao.getSectionNameToGo());
                    sectionConditionMap.put(sectionConditionDao.getQuestionId(),temp);
                }
            }

            sectionTO.setSectionCondition(sectionConditionMap);
            sectionTOS.add(sectionTO);
        }
        campaign.setSections(sectionTOS);
        return campaign;
    }

    public void insertCampainAnswers(List<CampaignAnswerDao> campaignAnswerDaos) {
        for(CampaignAnswerDao campaignAnswerDao:campaignAnswerDaos){
            campaignAnswerDao.setCreatedDate(new Date());
            campaignAnswerRepository.save(campaignAnswerDao);
        }
    }

    public List<CampaignAnswerDao> getCampaignPopulation() {
        return campaignAnswerRepository.findAll();
    }


    public void updateCampaignStatus(CampaignDao campaignDao){
        CampaignDao campaignDaoData = campaignRepository.getByCampaignId(campaignDao.getCampaignId());
        campaignDaoData.setComments(campaignDao.getComments());
        campaignDaoData.setStatusDesc(campaignDao.getStatusDesc());
        if(campaignDao.getStatusDesc() == "APPROVED"){
            campaignDaoData.setApprovedBy(getUserNameFromContext());
            campaignDaoData.setApprovedDate(new Date());
        }
        campaignDaoData.setChangedBy(getUserNameFromContext());
        campaignDaoData.setChangedDate(new Date());
        campaignRepository.update(campaignDaoData);
    }
}

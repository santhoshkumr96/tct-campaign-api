package com.tct.tctcampaign.model.db;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CampainQuestionDao {
    private Integer questionOrder;
    private Integer sectionId;
    private Integer questionId;
    private Integer campaignId;
    private Boolean isRequired;
    public CampainQuestionDao(){}
    public CampainQuestionDao(Integer sectionId , Integer questionId  , Integer campaignId, Boolean isRequired){
        this.questionId = questionId;
        this.sectionId = sectionId;
        this.campaignId = campaignId;
        this.isRequired = isRequired;
    }


}

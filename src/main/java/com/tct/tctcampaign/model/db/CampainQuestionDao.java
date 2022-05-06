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

    public CampainQuestionDao(){}
    public CampainQuestionDao(Integer sectionId , Integer questionId  , Integer campaignId){
        this.questionId = questionId;
        this.sectionId = sectionId;
        this.campaignId = campaignId;
    }


}

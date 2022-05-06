package com.tct.tctcampaign.model.db;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CampaignAnswerDao {
    private Integer answerOrder;
    private Integer campaignId;
    private Integer sectionId;
    private Integer questionId;
    private String answer;
    private Date createdDate;
}

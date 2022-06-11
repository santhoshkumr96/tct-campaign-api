package com.tct.tctcampaign.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SurveyCampaignTO {
    public Integer surveyId;
    public String surveyName;
    public Integer campaignId;
    public String campaignName;
    public String createdBy;
}

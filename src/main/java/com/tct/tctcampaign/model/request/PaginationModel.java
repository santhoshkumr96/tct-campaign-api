package com.tct.tctcampaign.model.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PaginationModel {
    private Integer numberOfRows;
    private Integer pageNumber;
    private String sqlCondition;
    private Integer campaignId;
    private String surveyName;
    private Integer surveyId;
}

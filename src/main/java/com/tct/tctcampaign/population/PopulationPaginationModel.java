package com.tct.tctcampaign.population;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PopulationPaginationModel {
    private Integer numberOfRows;
    private Integer pageNumber;
    private String sqlCondition;
    private Integer campaignId;
}

package com.tct.tctcampaign.model.db;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "survey_population_campaign_association")
@Setter
@Getter
public class SurveyPopulationCampaignAssociation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String surveyName;
    private int campaignId;
    private String clause;
    private String createdBy;
    private String status;
}

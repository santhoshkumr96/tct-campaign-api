package com.tct.tctcampaign.model.response;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CampaignViewTO {
    private Integer campaignId;
    private String campaignName;
    private String campaignDesc;
    private String campaignObjective;

    private String createdBy;
    private String comments;
    private String statusDesc;

}

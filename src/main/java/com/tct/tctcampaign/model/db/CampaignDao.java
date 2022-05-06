package com.tct.tctcampaign.model.db;

import com.tct.tctcampaign.model.response.CampaignViewTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CampaignDao {
    private Integer campaignId;
    @NotBlank
    private String campaignName;
    private String campaignDesc;
    private String campaignObjective;
    @NotEmpty
    private String createdBy;
    private String changedBy;
    private String approvedBy;
    private String statusDesc;
    @NotNull
    private Date createdDate;
    private Date changedDate;
    private Date approvedDate;
    private Boolean enabled;
    private String comments;

    public CampaignDao(){}

    public CampaignDao(String campaignName, String campaignDesc, String campaignObjective, Date createdDate , String createdBy){
        this.campaignName = campaignName;
        this.campaignDesc = campaignDesc;
        this.campaignObjective = campaignObjective;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.enabled  = true ;
        this.statusDesc = "PENDING";
    }

    public CampaignViewTO toView(){
        return CampaignViewTO
                .builder()
                .campaignId(campaignId)
                .campaignDesc(campaignDesc)
                .campaignName(campaignName)
                .campaignDesc(campaignDesc)
                .createdBy(createdBy)
                .campaignObjective(campaignObjective)
                .statusDesc(statusDesc)
                .comments(comments)
                .build();
    }
}

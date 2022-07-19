package com.tct.tctcampaign.model.db;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class SectionDao {
    private Integer sectionId;
    @NotBlank
    private String sectionName;
    private Integer campaignId;
    @NotEmpty
    private String createdBy;
    private String changedBy;
    private String approvedBy;
    @NotNull
    private Date createdDate;
    private Date changedDate;
    private Date approvedDate;
    private String statusDesc;
    private String afterSection;

    public SectionDao(){}

    public SectionDao(
            String sectionName,
            Integer campaignId,
            String createdBy,
            Date createdDate
    ){
        this.createdBy = createdBy;
        this.campaignId= campaignId;
        this.createdDate = createdDate;
        this.sectionName = sectionName;
    }

}

package com.tct.tctcampaign.model.db;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class SectionConditionDao {
    private Integer sectionId;
    private Integer campaignId;
    private Integer questionId;
    private Integer responseId;
    private String sectionNameToGo;
    private String statusDesc;
}

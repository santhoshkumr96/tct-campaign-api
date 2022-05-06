package com.tct.tctcampaign.model.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class CategoryDao {
    private int categoryId;
    private String categoryDesc;
    private String createdBy;
    private String changedBy;
    private String approvedBy;
    private String statusDesc;
    private Date createdDate;
    private Date changedDate;
    private Date approvedDate;
    private Boolean enabled;
    private String comments;
    private String categoryDescTa;
}

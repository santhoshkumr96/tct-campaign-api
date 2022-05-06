package com.tct.tctcampaign.model.db;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ResponseTypeDao {
    private int responseId;
    private String responseDesc;
    private String createdBy;
    private String changedBy;
    private String approvedBy;
    private String statusDesc;
    private Date createdDate;
    private Date changedDate;
    private Date approvedDate;
    private Boolean enabled;
    private String comments;
}

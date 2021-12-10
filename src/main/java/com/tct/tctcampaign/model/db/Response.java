package com.tct.tctcampaign.model.db;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Getter
@Setter
public class Response {

    private Integer responseId;
    @NotBlank
    private String responseName;
    private String responseDesc;
    private Integer questionId;
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
    public Response(){};
    public Response(String responseName,String responseDesc, Integer questionId , String createdBy , Date createdDate){
        this.responseName = responseName;
        this.responseDesc = responseDesc;
        this.questionId = questionId;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }
}

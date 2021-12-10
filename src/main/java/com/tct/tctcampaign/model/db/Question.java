package com.tct.tctcampaign.model.db;

import com.tct.tctcampaign.model.response.QuestionListTO;
import com.tct.tctcampaign.model.response.QuestionTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class Question {
    private Integer questionId;
    @NotBlank
    private String questionName;
    @NotBlank
    private String questionDesc;
    @NotBlank
    private String responseType;
    private Integer responseId;
    private Integer categoryId;
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

    public Question(){}

    public Question(String questionName, String questionDesc, String createdBy, Date createdDate, Boolean enabled,String responseType){
        this.questionName = questionName;
        this.questionDesc = questionDesc;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.enabled = enabled;
        this.responseType = responseType;
        this.statusDesc = "PENDING";
    }


    public QuestionTO toView(){
      return QuestionTO.builder()
              .questionId(questionId)
              .questionName(questionName)
              .questionDesc(questionDesc)
              .comments(comments)
              .statusDesc(statusDesc)
              .responseType(responseType)
              .createdBy(createdBy)
              .build();
    };

    public QuestionListTO toViewList(){
        return QuestionListTO.builder()
                .questionId(questionId)
                .questionName(questionName)
                .questionDesc(questionDesc)
                .responseType(responseType)
                .comments(comments)
                .statusDesc(statusDesc)
                .build();
    };
}

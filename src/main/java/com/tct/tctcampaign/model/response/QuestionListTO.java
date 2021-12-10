package com.tct.tctcampaign.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class QuestionListTO {
    @NotBlank
    @NotNull
    private String questionName;
    private Integer questionId;
    @NotBlank
    @NotNull
    private String questionDesc;
    private String comments;
    private String responseType;
    private String statusDesc;
}
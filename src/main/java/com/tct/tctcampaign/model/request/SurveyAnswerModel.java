package com.tct.tctcampaign.model.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SurveyAnswerModel {
    private Map<Integer, String> data;
    private Integer surveyId;
    private String user;
    private Integer personId;
}

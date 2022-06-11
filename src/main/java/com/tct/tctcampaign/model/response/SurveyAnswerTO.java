package com.tct.tctcampaign.model.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SurveyAnswerTO {
    private String memberName;
    private String questionName;
    private String answer;
}

package com.tct.tctcampaign.model.response;

import com.tct.tctcampaign.model.response.QuestionTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class SectionTO {
    private String title;
    private Integer sectionId;
    private String afterSection;
    private List<QuestionTO> questions;
    private Map<Integer, Map<Integer,String>> sectionCondition;
}

package com.tct.tctcampaign.model.response;

import com.tct.tctcampaign.model.response.QuestionTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SectionTO {
    private String title;
    private Integer sectionId;
    private List<QuestionTO> questions;
}

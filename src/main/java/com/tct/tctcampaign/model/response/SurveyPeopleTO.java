package com.tct.tctcampaign.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SurveyPeopleTO {
    public Integer personId;
    public String memberName;
    public String mobileNo;
    public String district;
    public String block;
    public String statusDesc;
}

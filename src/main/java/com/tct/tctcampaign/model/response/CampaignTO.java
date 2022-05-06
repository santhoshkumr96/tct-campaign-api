package com.tct.tctcampaign.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CampaignTO {
    public Integer campaignId;
    public String campaignName;
    public String campaignDesc;
    public String campaignObjective;
    public List<SectionTO> sections;
}

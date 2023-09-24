package com.tct.tctcampaign.repo;

import com.tct.tctcampaign.model.db.SurveyPopulationCampaignAssociation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SurveyPopulationCampaignAssociationRepo extends CrudRepository<SurveyPopulationCampaignAssociation, Long> {
}
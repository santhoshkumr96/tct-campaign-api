package com.tct.tctcampaign.population;

import java.io.Serializable;
import java.util.Date;

public class QuestionnairePopulationEntity implements Serializable {

	private static final long serialVersionUID = -2774133410771132882L;

	private int rowId;
	private int objectiveId;
	private int campaignId;
	private String villageCode;
	private String familyHeadId;
	private String familyPersonId;
	private String familyHeadName;

	public String getFamilyHeadName() {
		return familyHeadName;
	}

	public void setFamilyHeadName(String familyHeadName) {
		this.familyHeadName = familyHeadName;
	}

	public String getFamilyPersonName() {
		return familyPersonName;
	}

	public void setFamilyPersonName(String familyPersonName) {
		this.familyPersonName = familyPersonName;
	}

	private String familyPersonName;
	private String createdBy;
	private String changedBy;
	private Date createdOn;
	private Date changedOn;
	private String comments;
	private String mobileNum;

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	public int getObjectiveId() {
		return objectiveId;
	}

	public void setObjectiveId(int objectiveId) {
		this.objectiveId = objectiveId;
	}

	public int getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(int campaignId) {
		this.campaignId = campaignId;
	}

	public String getVillageCode() {
		return villageCode;
	}

	public void setVillageCode(String villageCode) {
		this.villageCode = villageCode;
	}

	public String getFamilyHeadId() {
		return familyHeadId;
	}

	public void setFamilyHeadId(String familyHeadId) {
		this.familyHeadId = familyHeadId;
	}

	public String getFamilyPersonId() {
		return familyPersonId;
	}

	public void setFamilyPersonId(String familyPersonId) {
		this.familyPersonId = familyPersonId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getChangedOn() {
		return changedOn;
	}

	public void setChangedOn(Date changedOn) {
		this.changedOn = changedOn;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}
package com.tct.tctcampaign.rowmapper;

import com.tct.tctcampaign.model.db.Survey;
import com.tct.tctcampaign.model.db.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class SurveyRowMapper implements RowMapper<Survey> {
    @Override
    public Survey mapRow(ResultSet rs, int rowNum) throws SQLException {
        Survey obj = new Survey();
        if(Objects.nonNull(rs)){
            obj.setFormNo(rs.getInt("formNo"));
            obj.setProjectCode(rs.getInt("projectCode"));
            obj.setLocationDetails(rs.getString("locationDetails"));
            obj.setVillageCode(rs.getString("villageCode"));
            obj.setPanchayatNo(rs.getString("panchayatNo"));
            obj.setPanchayatCode(rs.getString("panchayatCode"));
            obj.setVillageName(rs.getString("villageName"));
            obj.setZone(rs.getString("zone"));
            obj.setStreetName(rs.getString("streetName"));
            obj.setDoorNo(rs.getString("doorNo"));
            obj.setContactPerson(rs.getString("contactPerson"));
            obj.setNumberOfFamilyMembers(rs.getInt("numberOfFamilyMembers"));
            obj.setStatusOfHouse(rs.getString("statusOfHouse"));
            obj.setTypeOfHouse(rs.getString("typeOfHouse"));
            obj.setToiletFacilityAtHome(rs.getString("toiletFacilityAtHome"));
            obj.setOwnsAnyLand(rs.getString("ownsAnyLand"));
            obj.setWetLandInAcres(rs.getString("wetLandInAcres"));
            obj.setDryLandInAcres(rs.getString("dryLandInAcres"));
            obj.setOwnsAnyVechicles(rs.getString("ownsAnyVechicles"));
            obj.setNoOfVechiclesOwned(rs.getString("noOfVechiclesOwned"));
            obj.setTwoWheeler(rs.getString("twoWheeler"));
            obj.setThreeWheeler(rs.getString("threeWheeler"));
            obj.setFourWheeler(rs.getString("fourWheeler"));
            obj.setOthers(rs.getString("others"));
            obj.setOwnsAnyLiveStocks(rs.getString("ownsAnyLiveStocks"));
            obj.setHen(rs.getInt("hen"));
            obj.setCow(rs.getInt("cow"));
            obj.setPig(rs.getInt("pig"));
            obj.setBuffalo(rs.getInt("buffalo"));
            obj.setGoat(rs.getInt("goat"));
            obj.setOthersLiveStocks(rs.getInt("othersLiveStocks"));
            obj.setLivestockCount(rs.getInt("livestockCount"));
            obj.setIsCompleted(rs.getString("isCompleted"));
            obj.setActualMemberCount(rs.getInt("actualMemberCount"));
            obj.setFamilyHeadName(rs.getString("familyHeadName"));
            obj.setId(rs.getString("id"));
            obj.setName(rs.getString("name"));
            obj.setAadhaarNumber(rs.getString("aadhaarNumber"));
            obj.setRelationShip(rs.getString("relationShip"));
            obj.setGender(rs.getString("gender"));
            obj.setDateOfBirth(rs.getTimestamp("dateOfBirth"));
            obj.setMemberAge(rs.getInt("memberAge"));
            obj.setBloodGroup(rs.getString("bloodGroup"));
            obj.setPhysicallyChallenged(rs.getString("physicallyChallenged"));
            obj.setPhysicallyChallengedReason(rs.getString("physicallyChallengedReason"));
            obj.setMaritalStatus(rs.getString("maritalStatus"));
            obj.setEducationQualification(rs.getString("educationQualification"));
            obj.setOccupation(rs.getString("occupation"));
            obj.setAnnualIncome(rs.getString("annualIncome"));
            obj.setMobileNumber(rs.getString("mobileNumber"));
            obj.setEmail(rs.getString("email"));
            obj.setSmartPhone(rs.getString("smartPhone"));
            obj.setCommuntiy(rs.getString("communtiy"));
            obj.setCaste(rs.getString("caste"));
            obj.setGovtInsurance(rs.getString("govtInsurance"));
            obj.setHealthInsurance(rs.getString("healthInsurance"));
            obj.setOldAgePension(rs.getString("oldAgePension"));
            obj.setWidowedPension(rs.getString("widowedPension"));
            obj.setRetirementPension(rs.getString("retirementPension"));
            obj.setSmoking(rs.getString("smoking"));
            obj.setDrinking(rs.getString("drinking"));
            obj.setTobacco(rs.getString("tobacco"));
            obj.setVacinattionDone(rs.getString("vacinattionDone"));
            obj.setFirstDose(rs.getString("firstDose"));
            obj.setSecondDose(rs.getString("secondDose"));
            obj.setPatientId(rs.getString("patientId"));
        }
        return obj;
    }
}

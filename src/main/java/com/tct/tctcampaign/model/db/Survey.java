package com.tct.tctcampaign.model.db;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Survey {
    private int formNo;
    private int projectCode;
    private String locationDetails;
    private String villageCode;
    private String panchayatNo;
    private String panchayatCode;
    private String villageName;
    private String zone;
    private String streetName;
    private String doorNo;
    private String contactPerson;
    private int numberOfFamilyMembers;
    private String statusOfHouse;
    private String typeOfHouse;
    private String toiletFacilityAtHome;
    private String ownsAnyLand;
    private String wetLandInAcres;
    private String dryLandInAcres;
    private String ownsAnyVechicles;
    private String noOfVechiclesOwned;
    private String twoWheeler;
    private String threeWheeler;
    private String fourWheeler;
    private String others;
    private String ownsAnyLiveStocks;
    private int hen;
    private int cow;
    private int pig;
    private int buffalo;
    private int goat;
    private int othersLiveStocks;
    private int livestockCount;
    private String isCompleted;
    private int actualMemberCount;
    private String familyHeadName;
    private String id;
    private String name;
    private String aadhaarNumber;
    private String relationShip;
    private String gender;
    private Date dateOfBirth;
    private int memberAge;
    private String bloodGroup;
    private String physicallyChallenged;
    private String physicallyChallengedReason;
    private String maritalStatus;
    private String educationQualification;
    private String occupation;
    private String annualIncome;
    private String mobileNumber;
    private String email;
    private String smartPhone;
    private String communtiy;
    private String caste;
    private String govtInsurance;
    private String healthInsurance;
    private String oldAgePension;
    private String widowedPension;
    private String retirementPension;
    private String smoking;
    private String drinking;
    private String tobacco;
    private String vacinattionDone;
    private String firstDose;
    private String secondDose;
    private String patientId;
}

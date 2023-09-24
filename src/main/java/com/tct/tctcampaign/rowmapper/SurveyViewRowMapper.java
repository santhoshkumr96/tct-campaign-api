package com.tct.tctcampaign.rowmapper;

import com.tct.tctcampaign.model.db.SurveyView;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class SurveyViewRowMapper implements RowMapper<SurveyView> {
    @Override
    public SurveyView mapRow(ResultSet rs, int rowNum) throws SQLException {
        SurveyView obj = new SurveyView();
        if(Objects.nonNull(rs)){
            obj.setFamilyId(rs.getString("family_id"));
            obj.setBlockName(rs.getString("block_name"));
            obj.setPanchayat(rs.getString("panchayat"));
            obj.setAreaCode(rs.getInt("area_code"));
            obj.setVillageCode(rs.getString("village_code"));
            obj.setVillageName(rs.getString("village_name"));
            obj.setStreetName(rs.getString("street_name"));
            obj.setStatusOfHouse(rs.getString("status_of_house"));
            obj.setTypeOfHouse(rs.getString("type_of_house"));
            obj.setWetLandInAcres(rs.getString("wet_land_in_acres"));
            obj.setDryLandInAcres(rs.getString("dry_land_in_acres"));
            obj.setMotorVechicles(rs.getString("motor_vechicles"));
            obj.setTwoWheeler(rs.getString("two_wheeler"));
            obj.setFourWheeler(rs.getString("four_wheeler"));
            obj.setNoOtherVechicles(rs.getString("no_other_vechicles"));
            obj.setOtherVechiclesDetails(rs.getString("other_vechicles_details"));
            obj.setLivestockDetails(rs.getString("livestock_details"));
            obj.setHen(rs.getString("hen"));
            obj.setCow(rs.getString("cow"));
            obj.setPig(rs.getString("pig"));
            obj.setBuffalo(rs.getString("buffalo"));
            obj.setGoat(rs.getString("goat"));
            obj.setNoOtherLivestock(rs.getString("no_other_livestock"));
            obj.setOtherLivestockDetails(rs.getString("other_livestock_details"));
            obj.setToiletFacilityAtHome(rs.getString("toilet_facility_at_home"));
            obj.setMemberId(rs.getLong("member_id"));
            obj.setMemberName(rs.getString("member_name"));
            obj.setAadharNumber(rs.getString("aadhar_number"));
            obj.setMobileNumber(rs.getString("mobile_number"));
            obj.setEmail(rs.getString("email"));
            obj.setSmartphone(rs.getString("smartphone"));
            obj.setGovtInsurance(rs.getString("govt_insurance"));
            obj.setPrivateInsurance(rs.getString("private_insurance"));
            obj.setOldAgePension(rs.getString("old_age_pension"));
            obj.setWidowedPension(rs.getString("widowed_pension"));
            obj.setRetiredPerson(rs.getString("retired_person"));
            obj.setVaccination(rs.getString("vaccination"));
            obj.setDoseOne(rs.getTimestamp("dose_one"));
            obj.setDoseTwo(rs.getTimestamp("dose_two"));
            obj.setBirthDate(rs.getTimestamp("birth_date"));
            obj.setSmoking(rs.getString("smoking"));
            obj.setDrinking(rs.getString("drinking"));
            obj.setTobacco(rs.getString("tobacco"));
            obj.setDiabetes(rs.getString("diabetes"));
            obj.setBp(rs.getString("bp"));
            obj.setOsteoporosis(rs.getString("osteoporosis"));
            obj.setBreastCancer(rs.getString("breast_cancer"));
            obj.setUterusCancer(rs.getString("uterus_cancer"));
            obj.setUterusCancerScan(rs.getString("uterus_cancer_scan"));
            obj.setBreastCancerScan(rs.getString("breast_cancer_scan"));
            obj.setOralCancer(rs.getString("oral_cancer"));
            obj.setObesity(rs.getString("obesity"));
            obj.setHeartDiseases(rs.getString("heart_diseases"));
            obj.setLungRelatedDiseases(rs.getString("lung_related_diseases"));
            obj.setAsthma(rs.getString("asthma"));
            obj.setJointPain(rs.getString("joint_pain"));
            obj.setOtherDiseases(rs.getString("other_diseases"));
            obj.setAnnualIncomeString(rs.getString("annual_income_string"));
            obj.setIsDeceased(rs.getString("is_deceased"));
            obj.setDeceasedDate(rs.getTimestamp("deceased_date"));
            obj.setIsOsteoporosisScan(rs.getString("is_osteoporosis_scan"));
            obj.setOsteoporosisScanOne(rs.getString("osteoporosis_scan_one"));
            obj.setOsteoporosisScanTwo(rs.getString("osteoporosis_scan_two"));
            obj.setImageLocation(rs.getString("image_location"));
            obj.setTmhId(rs.getString("tmh_id"));
            obj.setPatientId(rs.getString("patient_id"));
            obj.setPermanentSterlization(rs.getString("permanent_sterlization"));
            obj.setPermanentSterlizationDate(rs.getTimestamp("permanent_sterlization_date"));
            obj.setTmpSterlization(rs.getString("tmp_sterlization"));
            obj.setTmpSterlizationType(rs.getString("tmp_sterlization_type"));
            obj.setDiabeticEnrolmentDate(rs.getTimestamp("diabetic_enrolment_date"));
            obj.setDiabeticEnrollmentStatus(rs.getString("diabetic_enrollment_status"));
            obj.setDiabeticEnrollmentEndDate(rs.getTimestamp("diabetic_enrollment_end_date"));
            obj.setDiabeticPackage(rs.getString("diabetic_package"));
            obj.setGender(rs.getString("gender"));
            obj.setPhysicallyChallenged(rs.getString("physically_challenged"));
            obj.setHandicapType(rs.getString("handicap_type"));
            obj.setOccupation(rs.getString("occupation"));
            obj.setAge(rs.getString("age"));
            obj.setCommunity(rs.getString("community"));
            obj.setCaste(rs.getString("caste"));
            obj.setRelationship(rs.getString("relationship"));
            obj.setMartialStatus(rs.getString("martial_status"));
            obj.setBloodGroup(rs.getString("blood_group"));
            obj.setEducationQualification(rs.getString("education_qualification"));
        }
        return obj;
    }
}

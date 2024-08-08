/**
 * 
 */
package com.bskyserviceconsume.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author santanu.barad
 *
 */
public class MoSarkarRequestData {
	private String district_id;
	private String name;
	private String mobile;
	private String age;
	private Long gender;
	private String department_institution_id;
	private String registration_date;
	private String registration_no;
	private String claim_amount;
	private String hospitalInfo;
	private String purpose;
	private String claimNo;
	private Long claimId;
	private Long transactionDetailId;
	private String hospitalDistrictName;

	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("district_id", this.district_id);
		map.put("name", this.name);
		map.put("mobile", this.mobile);
		map.put("age", this.age);
		map.put("gender", this.gender);
		map.put("department_institution_id", this.department_institution_id);
		map.put("registration_date", this.registration_date);
		map.put("registration_no", this.registration_no);
		map.put("claim_amount", this.claim_amount);
		map.put("hospitalInfo", this.hospitalInfo);
		map.put("purpose", this.purpose);
		map.put("claimNo", this.claimNo);
		map.put("claimId", this.claimId);
		map.put("transactionDetailId", this.transactionDetailId);
		map.put("hospitalDistrictName", this.hospitalDistrictName);
		return map;
	}

	public String getDistrict_id() {
		return district_id;
	}

	public void setDistrict_id(String district_id) {
		this.district_id = district_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public Long getGender() {
		return gender;
	}

	public void setGender(Long gender) {
		this.gender = gender;
	}

	public String getDepartment_institution_id() {
		return department_institution_id;
	}

	public void setDepartment_institution_id(String department_institution_id) {
		this.department_institution_id = department_institution_id;
	}

	public String getRegistration_date() {
		return registration_date;
	}

	public void setRegistration_date(String registration_date) {
		this.registration_date = registration_date;
	}

	public String getRegistration_no() {
		return registration_no;
	}

	public void setRegistration_no(String registration_no) {
		this.registration_no = registration_no;
	}

	public String getClaim_amount() {
		return claim_amount;
	}

	public void setClaim_amount(String claim_amount) {
		this.claim_amount = claim_amount;
	}

	public String getHospitalInfo() {
		return hospitalInfo;
	}

	public void setHospitalInfo(String hospitalInfo) {
		this.hospitalInfo = hospitalInfo;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getClaimNo() {
		return claimNo;
	}

	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	public Long getClaimId() {
		return claimId;
	}

	public void setClaimId(Long claimId) {
		this.claimId = claimId;
	}


	public Long getTransactionDetailId() {
		return transactionDetailId;
	}

	public void setTransactionDetailId(Long transactionDetailId) {
		this.transactionDetailId = transactionDetailId;
	}

	public String getHospitalDistrictName() {
		return hospitalDistrictName;
	}

	public void setHospitalDistrictName(String hospitalDistrictName) {
		this.hospitalDistrictName = hospitalDistrictName;
	}

	@Override
	public String toString() {
		return "MoSarkarRequestData{" +
				"district_id='" + district_id + '\'' +
				", name='" + name + '\'' +
				", mobile='" + mobile + '\'' +
				", age='" + age + '\'' +
				", gender=" + gender +
				", department_institution_id='" + department_institution_id + '\'' +
				", registration_date='" + registration_date + '\'' +
				", registration_no='" + registration_no + '\'' +
				", claim_amount='" + claim_amount + '\'' +
				", hospitalInfo='" + hospitalInfo + '\'' +
				", purpose='" + purpose + '\'' +
				", claimNo='" + claimNo + '\'' +
				", claimId=" + claimId +
				", transactionDetailId=" + transactionDetailId +
				'}';
	}
}

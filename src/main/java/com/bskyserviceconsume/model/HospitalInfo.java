package com.bskyserviceconsume.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @Project : BSKY Service Consume
 * @Auther : Sambit Kumar Pradhan
 * @Created On : 04/04/2023 - 2:26 PM
 */

@Getter
@Setter
@Entity
@Table(name = "HOSPITAL_INFO")
public class HospitalInfo {
    @Id
    @Column(name = "HOSPITAL_ID", nullable = false)
    private Long hospitalId;

    @Column(name = "HOSPITAL_NAME", length = 250)
    private String hospitalName;

    @Column(name = "HOSPITAL_CODE", length = 20)
    private String hospitalCode;

    @Column(name = "STATE_ID")
    private Long stateId;

    @Column(name = "DISTRICT_ID")
    private Long districtId;

    @Column(name = "STATUS_FLAG")
    private Long statusFlag;

    @Column(name = "CPD_APPROVAL_REQUIRED")
    private Long cpdApprovalRequired;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "DISTRICT_CODE", length = 20)
    private String districtCode;

    @Column(name = "STATE_CODE", length = 20)
    private String stateCode;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "MOBILE", length = 20)
    private String mobile;

    @Column(name = "EMAIL_ID", length = 100)
    private String emailId;

    @Column(name = "HOSPITALAUTHORITYID", length = 20)
    private String hospitalauthorityid;

    @Column(name = "HOSPITALTYPE", length = 10)
    private String hospitaltype;

    @Column(name = "HOSPITALPINCODE")
    private Integer hospitalpincode;

    @Column(name = "COMPUTER", length = 10)
    private String computer;

    @Column(name = "OS", length = 10)
    private String os;

    @Column(name = "INTERNET", length = 10)
    private String internet;

    @Column(name = "EMPANELMENTDATE", length = 50)
    private String empanelmentdate;

    @Column(name = "EMPANELMENTSTATUS", length = 20)
    private String empanelmentstatus;

    @Column(name = "SERVICETAXREGISTRATION_NO", length = 50)
    private String servicetaxregistrationNo;

    @Column(name = "PANCARDNO", length = 20)
    private String pancardno;

    @Column(name = "PANCARDHOLDER", length = 50)
    private String pancardholder;

    @Column(name = "BANKNAME", length = 50)
    private String bankname;

    @Column(name = "BANKACCOUN_NO", length = 50)
    private String bankaccounNo;

    @Column(name = "IFSCCODE", length = 20)
    private String ifsccode;

    @Column(name = "PAYEENAME", length = 50)
    private String payeename;

    @Column(name = "NOOF_FULL_TIME_PHYSICIANS")
    private Integer noofFullTimePhysicians;

    @Column(name = "PHARMACY")
    private Integer pharmacy;

    @Column(name = "REMARK", length = 50)
    private String remark;

    @Column(name = "GENERAL")
    private Integer general;

    @Column(name = "DAYCARE")
    private Integer daycare;

    @Column(name = "ICU")
    private Integer icu;

    @Column(name = "SECONDARY", length = 10)
    private String secondary;

    @Column(name = "SECONDARYSINGLE_SPECIALITY", length = 10)
    private String secondarysingleSpeciality;

    @Column(name = "SECONDARYMULTI_SPECIALITY", length = 10)
    private String secondarymultiSpeciality;

    @Column(name = "TERTIARYSINGLE_SPECIALITY", length = 10)
    private String tertiarysingleSpeciality;

    @Column(name = "TERTIARYMULTI_SPECIALITY", length = 10)
    private String tertiarymultiSpeciality;

    @Column(name = "INTERNALMEDICINE", length = 10)
    private String internalmedicine;

    @Column(name = "EMERGENCYROOMORMINOR_OT", length = 10)
    private String emergencyroomorminorOt;

    @Column(name = "A24_HRAMBULANCE", length = 10)
    private String a24Hrambulance;

    @Column(name = "BURNSUNIT", length = 10)
    private String burnsunit;

    @Column(name = "TRAUMACENTRE", length = 10)
    private String traumacentre;

    @Column(name = "CATHLABFACILITY", length = 10)
    private String cathlabfacility;

    @Column(name = "NOOF_MAJOR_OTS", length = 10)
    private String noofMajorOts;

    @Column(name = "NOOF_MINOR_OTS", length = 10)
    private String noofMinorOts;

    @Column(name = "WASTEDISPOSALSYSTEM", length = 10)
    private String wastedisposalsystem;

    @Column(name = "KITCHENSERVICE", length = 10)
    private String kitchenservice;

    @Column(name = "POWERBACKUP", length = 10)
    private String powerbackup;

    @Column(name = "F62", length = 10)
    private String f62;

    @Column(name = "UPLOADDATE")
    private Date uploaddate;

    @Column(name = "UPLOADUSERID")
    private Integer uploaduserid;

    @Column(name = "HOSPITALADDRESS", length = 200)
    private String hospitaladdress;

    @Column(name = "CONTACTPERSON", length = 50)
    private String contactperson;

    @Column(name = "OPERATOR", length = 50)
    private String operator;

    @Column(name = "INSURANCECOMPANYNAME", length = 50)
    private String insurancecompanyname;

    @Column(name = "LONGITUDE", length = 50)
    private String longitude;

    @Column(name = "LATITUDE", length = 50)
    private String latitude;

    @Column(name = "BRANCHADDRESS", length = 200)
    private String branchaddress;

    @Column(name = "ASSIGNED_DC")
    private Long assignedDc;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "CREATED_ON")
    private Date createdOn;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_ON")
    private Date updatedOn;

    @Column(name = "DELETED_FLAG", nullable = false)
    private Boolean deletedFlag = false;

    @Column(name = "HOSPITAL_CATEGORYID")
    private Long hospitalCategoryid;

    @Column(name = "EXCEPTIONHOSPITAL")
    private Long exceptionhospital;

    @Column(name = "HC_VALID_FROM_DATE")
    private Date hcValidFromDate;

    @Column(name = "HC_VALID_TO_DATE")
    private Date hcValidToDate;

    @Column(name = "MOU", length = 100)
    private String mou;

    @Column(name = "MOU_START_DATE")
    private Date mouStartDate;

    @Column(name = "MOU_END_DATE")
    private Date mouEndDate;

    @Column(name = "MOU_STATUS")
    private Boolean mouStatus;

    @Column(name = "EMPANELMENTSTATUS_FLAG")
    private Boolean empanelmentstatusFlag;

    @Column(name = "BACKDATE_ADMISSION_DAYS")
    private Long backdateAdmissionDays;

    @Column(name = "BACKDATE_DISCHARGE_DAYS")
    private Long backdateDischargeDays;

    @Column(name = "MOU_DOC_UPLOAD")
    private String mouDocUpload;

    @Column(name = "IS_BLOCK_ACTIVE")
    private Boolean isBlockActive;

    @Column(name = "INTONLINESERVICEID")
    private Long intonlineserviceid;

    @Column(name = "INTPROFILEID")
    private Long intprofileid;

}
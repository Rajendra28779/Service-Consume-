package com.bskyserviceconsume.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.time.LocalDate;

/**
 * @Project : BSKY Service Consume
 * @Auther : Sambit Kumar Pradhan
 * @Created On : 28/04/2023 - 1:01 PM
 */

@Getter
@Setter
@Entity
@Table(name = "TXNCLAIM_APPLICATION")
public class TxnclaimApplication {
    @Id
    @Column(name = "CLAIMID", nullable = false)
    private Integer id;

    @Column(name = "TRANSACTIONDETAILSID", nullable = false)
    private Integer transactiondetailsid;

    @Column(name = "URN", nullable = false, length = 50)
    private String urn;

    @Column(name = "HOSPITALCODE", nullable = false, length = 50)
    private String hospitalcode;

    @Column(name = "PENDINGAT", nullable = false)
    private Integer pendingat;

    @Column(name = "CLAIMSTATUS", nullable = false)
    private Integer claimstatus;

    @Column(name = "ASSIGNEDCPD")
    private Integer assignedcpd;

    @Column(name = "ASSIGNEDSNO")
    private Integer assignedsno;

    @Column(name = "ADMINSSIONSLIP", length = 256)
    private String adminssionslip;

    @Column(name = "TREATMENTSLIP", length = 256)
    private String treatmentslip;

    @Column(name = "ADDTIONAL_DOC", length = 256)
    private String addtionalDoc;

    @Column(name = "DISCHARGESLIP", length = 256)
    private String dischargeslip;

    @Column(name = "INVESTIGATIONDOC", length = 256)
    private String investigationdoc;

    @Column(name = "PRESURGERYPHOTO", length = 256)
    private String presurgeryphoto;

    @Column(name = "POSTSURGERYPHOTO", length = 256)
    private String postsurgeryphoto;

    @Column(name = "CREATEDBY")
    private Integer createdby;

    @Column(name = "CREATEDON")
    private Instant createdon;

    @Column(name = "UPDATEDBY")
    private Integer updatedby;

    @Column(name = "UPDATEON")
    private Instant updateon;

    @Column(name = "INVESTIGATIONDOC2", length = 256)
    private String investigationdoc2;

    @Column(name = "HOSPITALBILL2", length = 256)
    private String hospitalbill2;

    @Column(name = "INVOICENO")
    private Long invoiceno;

    @Column(name = "CHIPSERIALNO")
    private String chipserialno;

    @Column(name = "PACKAGECODE", length = 20)
    private String packagecode;

    @Column(name = "REFTRANSACTIONID")
    private Long reftransactionid;

    @Column(name = "STATUSFLAG", nullable = false)
    private Boolean statusflag = false;

    @Column(name = "CPDAPPROVEDAMOUNT", length = 100)
    private String cpdapprovedamount;

    @Column(name = "SNOAPPROVEDAMOUNT", length = 100)
    private String snoapprovedamount;

    @Column(name = "USER_IP", length = 100)
    private String userIp;

    @Column(name = "REMARK_ID")
    private Long remarkId;

    @Column(name = "REMARKS", length = 500)
    private String remarks;

    @Column(name = "CPDACTIONDATE", length = 100)
    private String cpdactiondate;

    @Column(name = "ADDITIONAL_DOC1", length = 256)
    private String additionalDoc1;

    @Column(name = "ADDITIONAL_DOC2", length = 256)
    private String additionalDoc2;

    @Column(name = "REVISED_DATE")
    private Instant revisedDate;

    @Column(name = "HOSPITALBILL")
    private String hospitalbill;

    @Column(name = "CLAIM_CASE_NO", length = 20)
    private String claimCaseNo;

    @Column(name = "CLAIM_BILL_NO", length = 20)
    private String claimBillNo;

    @Column(name = "INTRA_SURGERY_PHOTO", length = 256)
    private String intraSurgeryPhoto;

    @Column(name = "SPECIMEN_REMOVAL_PHOTO", length = 256)
    private String specimenRemovalPhoto;

    @Column(name = "PATIENT_PHOTO", length = 256)
    private String patientPhoto;

    @Column(name = "CPD_ALLOTED_DATE")
    private Instant cpdAllotedDate;

    @Column(name = "CLAIM_NO", length = 30)
    private String claimNo;

    @Column(name = "CLAIM_AMOUNT", length = 100)
    private String claimAmount;

    @Column(name = "MEMBERID")
    private String memberid;

    @Column(name = "DATEOFADMISSION", length = 100)
    private String dateofadmission;

    @Column(name = "DISTRICTCODE", length = 20)
    private String districtcode;

    @Column(name = "HOSPITALAUTHORITYID", length = 20)
    private String hospitalauthorityid;

    @Column(name = "TOTALAMOUNTCLAIMED", length = 100)
    private String totalamountclaimed;

    @Column(name = "TOTALAMOUNTBLOCKED", length = 100)
    private String totalamountblocked;

    @Column(name = "NOOFDAYS", length = 100)
    private String noofdays;

    @Column(name = "DATEOFDISCHARGE", length = 100)
    private String dateofdischarge;

    @Column(name = "PATIENTNAME", length = 100)
    private String patientname;

    @Column(name = "FAMILYHEADNAME", length = 100)
    private String familyheadname;

    @Column(name = "VERIFIERNAME", length = 100)
    private String verifiername;

    @Column(name = "GENDER", length = 20)
    private String gender;

    @Column(name = "BLOCKCODE", length = 20)
    private String blockcode;

    @Column(name = "PANCHAYATCODE", length = 20)
    private String panchayatcode;

    @Column(name = "VILLAGECODE", length = 20)
    private String villagecode;

    @Column(name = "AGE", length = 20)
    private String age;

    @Column(name = "STATENAME", length = 32)
    private String statename;

    @Column(name = "DISTRICTNAME", length = 32)
    private String districtname;

    @Column(name = "BLOCKNAME", length = 32)
    private String blockname;

    @Column(name = "PANCHAYATNAME", length = 64)
    private String panchayatname;

    @Column(name = "VILLAGENAME", length = 64)
    private String villagename;

    @Column(name = "ACTUALDATEOFDISCHARGE", length = 100)
    private String actualdateofdischarge;

    @Column(name = "HOSPITALNAME")
    private String hospitalname;

    @Column(name = "PROCEDURENAME")
    private String procedurename;

    @Column(name = "PACKAGENAME")
    private String packagename;

    @Column(name = "PACKAGECATEGORYCODE", length = 20)
    private String packagecategorycode;

    @Column(name = "PACKAGEID", length = 20)
    private String packageid;

    @Column(name = "HOSPITALSTATECODE", length = 20)
    private String hospitalstatecode;

    @Column(name = "HOSPITALDISTRICTCODE", length = 20)
    private String hospitaldistrictcode;

    @Column(name = "ACTUALDATEOFADMISSION", length = 100)
    private String actualdateofadmission;

    @Column(name = "AUTHORIZEDCODE", length = 20)
    private String authorizedcode;

    @Column(name = "NABH")
    private String nabh;

    @Column(name = "PATIENTPHONENO", length = 15)
    private String patientphoneno;

    @Column(name = "IMPLANT_DATA", length = 100)
    private String implantData;

    @Column(name = "CPD_ALLOT_STATUS", length = 32)
    private String cpdAllotStatus;

    @Column(name = "INITIAL_CPD_ALLOTED_DATE", length = 100)
    private String initialCpdAllotedDate;

    @Column(name = "SYS_REJ_STATUS")
    private Long sysRejStatus;

    @Column(name = "PAYMENTFREEZESTATUS")
    private Long paymentfreezestatus;

    @Column(name = "FLOAT_NO", length = 30)
    private String floatNo;

    @Column(name = "REJECTED_STATUS")
    private Boolean rejectedStatus;

    @Column(name = "MORTALITY", length = 20)
    private String mortality;

    @Column(name = "SNA_MORTALITY", length = 20)
    private String snaMortality;

    @Column(name = "CPD_MORTALITY", length = 20)
    private String cpdMortality;

    @Column(name = "FO_REMARKS", length = 500)
    private String foRemarks;

    @Column(name = "CREATEDON1", length = 100)
    private String createdon1;

    @Column(name = "UPDATEON1", length = 100)
    private String updateon1;

    @Column(name = "CPDAPPROVEDAMOUNT1", length = 100)
    private String cpdapprovedamount1;

    @Column(name = "SNOAPPROVEDAMOUNT1", length = 100)
    private String snoapprovedamount1;

    @Column(name = "CPDACTIONDATE1", length = 100)
    private String cpdactiondate1;

    @Column(name = "REVISED_DATE1", length = 100)
    private String revisedDate1;

    @Column(name = "CPD_ALLOTED_DATE1", length = 100)
    private String cpdAllotedDate1;

    @Column(name = "CLAIM_AMOUNT1", length = 100)
    private String claimAmount1;

    @Column(name = "DATEOFADMISSION1", length = 100)
    private String dateofadmission1;

    @Column(name = "TOTALAMOUNTCLAIMED1", length = 100)
    private String totalamountclaimed1;

    @Column(name = "TOTALAMOUNTBLOCKED1", length = 100)
    private String totalamountblocked1;

    @Column(name = "NOOFDAYS1", length = 100)
    private String noofdays1;

    @Column(name = "DATEOFDISCHARGE1", length = 100)
    private String dateofdischarge1;

    @Column(name = "ACTUALDATEOFADMISSION1", length = 100)
    private String actualdateofadmission1;

    @Column(name = "INITIAL_CPD_ALLOTED_DATE1", length = 100)
    private String initialCpdAllotedDate1;

    @Column(name = "ACTUALDATEOFDISCHARGE1", length = 100)
    private String actualdateofdischarge1;

    @Column(name = "VERIFICATIONMODE")
    private Long verificationmode;

    @Column(name = "ISPATIENTOTPVERIFIED")
    private Long ispatientotpverified;

    @Column(name = "REFERRALAUTHSTATUS", length = 2)
    private String referralauthstatus;

    @Column(name = "CASENO", length = 128)
    private String caseno;

    @Column(name = "BULK_APRV_STATUS")
    private Long bulkAprvStatus;

    @Column(name = "MO_SARKAR_FLAG")
    private Boolean moSarkarFlag;

    @Column(name = "CPD_QUERY_DATE")
    private LocalDate cpdQueryDate;

    @Column(name = "SNA_QUERY_DATE")
    private LocalDate snaQueryDate;

}
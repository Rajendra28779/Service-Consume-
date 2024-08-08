package com.bskyserviceconsume.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Instant;
import java.time.LocalDate;

/**
 * @Project : BSKY Service Consume
 * @Auther : Sambit Kumar Pradhan
 * @Created On : 28/04/2023 - 01:01 PM
 */

@Getter
@Setter
@ToString
@Entity
@Table(name = "TXNTRANSACTIONDETAILS")
public class Txntransactiondetail {
    @EmbeddedId
    private TxntransactiondetailId id;

    @Column(name = "TRANSACTIONID", nullable = false)
    private Integer transactionid;

    @Column(name = "INVOICENO")
    private String invoiceno;

    @Column(name = "URN")
    private String urn;

    @Column(name = "STATECODE")
    private String statecode;

    @Column(name = "DISTRICTCODE")
    private String districtcode;

    @Column(name = "MEMBERID")
    private String memberid;

    @Column(name = "FPVERIFIERID")
    private String fpverifierid;

    @Column(name = "HOSPITALCODE")
    private String hospitalcode;

    @Column(name = "HOSPITALAUTHORITYID")
    private String hospitalauthorityid;

    @Column(name = "TRANSACTIONCODE")
    private String transactioncode;

    @Column(name = "TRANSACTIONTYPE")
    private String transactiontype;

    @Column(name = "TRANSACTIONDATE", length = 100)
    private String transactiondate;

    @Column(name = "TRANSACTIONTIME")
    private String transactiontime;

    @Column(name = "PACKAGECODE")
    private String packagecode;

    @Column(name = "TOTALAMOUNTCLAIMED", length = 100)
    private String totalamountclaimed;

    @Column(name = "TOTALAMOUNTBLOCKED", length = 100)
    private String totalamountblocked;

    @Column(name = "INSUFFCIENTFUND", length = 100)
    private String insuffcientfund;

    @Column(name = "INSUFFCIENTAMT", length = 100)
    private String insuffcientamt;

    @Column(name = "NOOFDAYS")
    private String noofdays;

    @Column(name = "DATEOFADMISSION", length = 100)
    private String dateofadmission;

    @Column(name = "DATEOFDISCHARGE", length = 100)
    private String dateofdischarge;

    @Column(name = "MORTALITY")
    private String mortality;

    @Column(name = "TRANSACTIONDESCRIPTION")
    private String transactiondescription;

    @Column(name = "AMOUNTCLAIMED", length = 100)
    private String amountclaimed;

    @Column(name = "TRAVELAMOUNTCLAIMED", length = 50)
    private String travelamountclaimed;

    @Column(name = "CURRENTTOTALAMOUNT", length = 50)
    private String currenttotalamount;

    @Column(name = "REVISED_DATE")
    private Instant revisedDate;

    @Column(name = "PATIENTNAME", length = 200)
    private String patientname;

    @Column(name = "FAMILYHEADNAME", length = 100)
    private String familyheadname;

    @Column(name = "VERIFIERNAME", length = 100)
    private String verifiername;

    @Column(name = "TRAVELAMOUNT", length = 100)
    private String travelamount;

    @Column(name = "GENDER")
    private char gender;

    @Column(name = "UPLOADSTATUS", length = 50)
    private String uploadstatus;

    @Column(name = "TRANSACTION_DATE", length = 100)
    private String transactionDate;

    @Column(name = "UNBLOCKAMOUNT", length = 100)
    private String unblockamount;

    @Column(name = "ROUND", length = 10)
    private String round;

    @Column(name = "BLOCKCODE", length = 50)
    private String blockcode;

    @Column(name = "PANCHAYATCODE", length = 50)
    private String panchayatcode;

    @Column(name = "VILLAGECODE", length = 50)
    private String villagecode;

    @Column(name = "AGE", length = 50)
    private String age;

    @Column(name = "STATENAME", length = 75)
    private String statename;

    @Column(name = "DISTRICTNAME", length = 75)
    private String districtname;

    @Column(name = "BLOCKNAME", length = 75)
    private String blockname;

    @Column(name = "PANCHAYATNAME", length = 75)
    private String panchayatname;

    @Column(name = "VILLAGENAME", length = 75)
    private String villagename;

    @Column(name = "ACTUALDATEOFDISCHARGE", length = 100)
    private String actualdateofdischarge;

    @Column(name = "REGISTRATIONNO", length = 20)
    private String registrationno;

    @Column(name = "POLICYSTARTDATE", length = 100)
    private String policystartdate;

    @Column(name = "POLICYENDDATE", length = 100)
    private String policyenddate;

    @Column(name = "HOSPITALNAME", length = 75)
    private String hospitalname;

    @Column(name = "PROCEDURENAME", length = 75)
    private String procedurename;

    @Column(name = "PACKAGENAME", length = 2084)
    private String packagename;

    @Column(name = "PACKAGECATEGORYCODE", length = 3)
    private String packagecategorycode;

    @Column(name = "PACKAGEID", length = 8)
    private String packageid;

    @Column(name = "HOSPITALSTATECODE", length = 2)
    private String hospitalstatecode;

    @Column(name = "HOSPITALDISTRICTCODE", length = 4)
    private String hospitaldistrictcode;

    @Column(name = "ACTUALDATEOFADMISSION", length = 100)
    private String actualdateofadmission;

    @Column(name = "AUTHORIZEDCODE", length = 10)
    private String authorizedcode;

    @Column(name = "MANUALTRANSACTION", length = 10)
    private String manualtransaction;

    @Column(name = "CLAIMID", length = 20)
    private String claimid;

    @Column(name = "TRIGGERFLAG", length = 50)
    private String triggerflag;

    @Column(name = "REFERRALCODE", length = 50)
    private String referralcode;

    @Column(name = "NABH", length = 10)
    private String nabh;

    @Column(name = "HOSPITALCLAIMEDAMOUNT", length = 100)
    private String hospitalclaimedamount;

    @Column(name = "CLAIMRAISESTATUS")
    private Long claimraisestatus;

    @Column(name = "DISCHARGE_DOC", length = 200)
    private String dischargeDoc;

    @Column(name = "STATUSFLAG")
    private Long statusflag;

    @Column(name = "CREATEDON", nullable = false)
    private Instant createdon;

    @Column(name = "CLAIM_ID")
    private Long claimId;

    @Column(name = "IMPLANT_DATA", length = 100)
    private String implantData;

    @Column(name = "DATEOFDISCHARGE_TEMP")
    private LocalDate dateofdischargeTemp;

    @Column(name = "CLAIM_RAISED_BY")
    private LocalDate claimRaisedBy;

    @Column(name = "DATEOFADMISSION_TEMP")
    private LocalDate dateofadmissionTemp;

    @Column(name = "PATIENTPHONENO", length = 15)
    private String patientphoneno;

    @Column(name = "SYS_REJ_STATUS")
    private Long sysRejStatus;

    @Column(name = "SEQUENCE_ID")
    private Long sequenceId;

    @Column(name = "TRANID_PK")
    private Long tranidPk;

    @Column(name = "REC_CR_DT")
    private LocalDate recCrDt;

    @Column(name = "BATCH_NO")
    private Long batchNo;

    @Column(name = "REJECTED_STATUS")
    private Integer rejectedStatus;

    @Column(name = "TRANSACTIONDATE1", length = 100)
    private String transactiondate1;

    @Column(name = "TOTALAMOUNTCLAIMED1", length = 100)
    private String totalamountclaimed1;

    @Column(name = "TOTALAMOUNTBLOCKED1", length = 100)
    private String totalamountblocked1;

    @Column(name = "INSUFFCIENTAMT1", length = 100)
    private String insuffcientamt1;

    @Column(name = "INSUFFCIENTFUND1", length = 100)
    private String insuffcientfund1;

    @Column(name = "DATEOFADMISSION1", length = 100)
    private String dateofadmission1;

    @Column(name = "DATEOFDISCHARGE1", length = 100)
    private String dateofdischarge1;

    @Column(name = "AMOUNTCLAIMED1", length = 100)
    private String amountclaimed1;

    @Column(name = "REVISED_DATE1", length = 100)
    private String revisedDate1;

    @Column(name = "TRAVELAMOUNT1", length = 100)
    private String travelamount1;

    @Column(name = "TRANSACTION_DATE1", length = 100)
    private String transactionDate1;

    @Column(name = "UNBLOCKAMOUNT1", length = 100)
    private String unblockamount1;

    @Column(name = "ACTUALDATEOFDISCHARGE1", length = 100)
    private String actualdateofdischarge1;

    @Column(name = "POLICYSTARTDATE1", length = 100)
    private String policystartdate1;

    @Column(name = "POLICYENDDATE1", length = 100)
    private String policyenddate1;

    @Column(name = "ACTUALDATEOFADMISSION1", length = 100)
    private String actualdateofadmission1;

    @Column(name = "HOSPITALCLAIMEDAMOUNT1", length = 100)
    private String hospitalclaimedamount1;

    @Column(name = "CREATEDON1", length = 100)
    private String createdon1;

    @Column(name = "INSURANCECOMPANYNAME")
    private String insurancecompanyname;

    @Column(name = "VERIFICATIONMODE")
    private Long verificationmode;

    @Column(name = "ISPATIENTOTPVERIFIED")
    private Long ispatientotpverified;

    @Column(name = "REFERRALAUTHSTATUS", length = 2)
    private String referralauthstatus;

    @Column(name = "INTRASURGERY", length = 1028)
    private String intrasurgery;

    @Column(name = "POSTSURGERY", length = 1028)
    private String postsurgery;

    @Column(name = "PRESURGERY", length = 1028)
    private String presurgery;

    @Column(name = "SPECIMENREMOVAL", length = 1028)
    private String specimenremoval;

    @Column(name = "CASENO", length = 128)
    private String caseno;

    @Column(name = "PATIENTPHOTO", length = 1028)
    private String patientphoto;

    @Column(name = "MORTALITYDOC", length = 512)
    private String mortalitydoc;

    @Column(name = "MO_SARKAR_FLAG")
    private Long moSarkarFlag;

    @Column(name = "TXNPACKAGEDETAILID")
    private Long txnpackagedetailid;
}
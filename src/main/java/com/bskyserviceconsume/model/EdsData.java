package com.bskyserviceconsume.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * @Project : BSKY Service Consume
 * @Auther : Sambit Kumar Pradhan
 * @Created On : 08/05/2023 - 2:26 PM
 */
@Getter
@Setter
@Entity
@ToString
@Table(name = "EDS_DATA")
public class EdsData {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EDS_DATA_id_gen")
    @SequenceGenerator(name = "EDS_DATA_id_gen", sequenceName = "EDS_DATA_SEQ", allocationSize = 1)
    @Column(name = "EDS_ID", nullable = false)
    private Long edsId;

    @Column(name = "ID")
    private Long id;

    @Column(name = "JOB_NO")
    private String jobNo;

    @Column(name = "CALL_TYPE")
    private String callType;

    @Column(name = "DISTRICT")
    private String district;

    @Column(name = "CALL_TAKER")
    private String callTaker;

    @Column(name = "CALLER_NO")
    private String callerNo;

    @Column(name = "CALL_DATETIME")
    private Date callDatetime;

    @Column(name = "TYPE_OF_JOB")
    private String typeOfJob;

    @Column(name = "IS_VICTIM_CALLER")
    private String isVictimCaller;

    @Column(name = "VICTIM_NAME")
    private String victimName;

    @Column(name = "FACILITY_NAME")
    private String facilityName;

    @Column(name = "VILLAGE")
    private String village;

    @Column(name = "PATIENT_AGE")
    private Long patientAge;

    @Column(name = "AGE_UNIT")
    private String ageUnit;

    @Column(name = "NATURE_OF_CALL")
    private String natureOfCall;

    @Column(name = "STATE")
    private String state;

    @Column(name = "HOSPITAL_NAME")
    private String hospitalName;

    @Column(name = "EDS_STATUS")
    private Long edsStatus;

    @Column(name = "CREATED_ON")
    private Date createdOn;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "UPDATED_ON")
    private Date updatedOn;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

    @Column(name = "STATUS_FLAG")
    private Long statusFlag = 0L;
}
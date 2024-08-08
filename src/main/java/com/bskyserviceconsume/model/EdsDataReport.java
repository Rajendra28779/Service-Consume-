package com.bskyserviceconsume.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/*
 * @Author: Sambit Kumar Pradhan
 * @Date: 28 Feb 2024
 * @Project : BSKY Service Consume
 */

@Getter
@Setter
@Entity
@ToString
@Table(name = "EDS_DATA_REPORT")
public class EdsDataReport {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EDS_DATA_REPORT_id_gen")
    @SequenceGenerator(name = "EDS_DATA_REPORT_id_gen", sequenceName = "EDS_DATA_REPORT_SEQ", allocationSize = 1)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "API_ID")
    private Integer apiId;

    @Column(name = "API_NAME")
    private String apiName;

    @Column(name = "START_TIME")
    private Date startTime;

    @Column(name = "END_TIME")
    private Date endTime;

    @Lob
    @Column(name = "INPUT_DATA")
    private String inputData;

    @Lob
    @Column(name = "BSKY_DATA")
    private String bskyData;

    @Lob
    @Column(name = "BSKY_INSERTED_DATA")
    private String bskyInsertedData;

    @Lob
    @Column(name = "BSKY_UPDATED_DATA")
    private String bskyUpdatedData;

    @Lob
    @Column(name = "BSKY_FAILED_DATA")
    private String bskyFailedData;

    @Column(name = "RECORDS_FETCH")
    private Long recordsFetch;

    @Column(name = "RECORDS_INSERTED")
    private Long recordsInserted;

    @Column(name = "RECORDS_UPDATED")
    private Long recordsUpdated;

    @Column(name = "RECORDS_FAILED")
    private Long recordsFailed;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

    @Column(name = "STATUS_FLAG")
    private Long statusFlag;
}
package com.bskyserviceconsume.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "TBL_BSKY_STATISTICS_REPORT")
public class TblBskyStatisticsReport {

    @Id
    @GeneratedValue(generator = "catInc")
    @Column(name = "REPORT_ID", nullable = false)
    @GenericGenerator(name = "catInc", strategy = "increment")
    private Long reportId;

    @Column(name = "REPORT_DATE")
    private Date reportDate;

    @Column(name = "TOTAL_EXPENSES")
    private Long totalExpenses;

    @Column(name = "TOTAL_ADMISSION")
    private Long totalAdmission;

    @Column(name = "TOTAL_OPTHALMOLOGY")
    private Long totalOpthalmology;

    @Column(name = "TOTAL_DIALYSIS")
    private Long totalDialysis;

    @Column(name = "TOTAL_COVID")
    private Long totalCovid;

    @Column(name = "TOTAL_SURGICAL")
    private Long totalSurgical;

    @Column(name = "TOTAL_CHEMOTHERAPY")
    private Long totalChemotherapy;

    @Column(name = "TOTAL_GYNAECOLOGY")
    private Long totalGynaecology;

    @Column(name = "CREATED_ON")
    private Date createdOn;

    @Column(name = "STATUS_FLAG")
    private Long statusFlag;
}
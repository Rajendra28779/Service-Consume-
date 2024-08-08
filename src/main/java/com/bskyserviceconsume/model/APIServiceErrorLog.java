package com.bskyserviceconsume.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "API_SERVICE_ERROR_LOG")
public class APIServiceErrorLog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "API_SERVICE_ERROR_LOG_id_gen")
    @SequenceGenerator(name = "API_SERVICE_ERROR_LOG_id_gen", sequenceName = "API_SERVICE_ERROR_LOG_SEQ", allocationSize = 1)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "API_ID", nullable = false)
    private Integer apiId;

    @Column(name = "API_NAME", nullable = false, length = 100)
    private String apiName;

    @Column(name = "ERROR_CODE", length = 1000)
    private String errorCode;

    @Column(name = "ERROR_MESSAGE", length = 4000)
    private String errorMessage;

    @Lob
    @Column(name = "ERROR_STACKTRACE")
    private String errorStacktrace;

    @Column(name = "CREATED_ON", nullable = false)
    private Date createdDate;

    @Column(name = "CREATED_BY", nullable = false, length = 100)
    private String createdBy;

    @Column(name = "STATUS_FLAG", nullable = false)
    private Boolean statusFlag = false;

}
/**
 * 
 */
package com.bskyserviceconsume.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

/**
 * 
 */
@Data
@Entity
@Table(name = "tbl_scst_schemetransaction_api_log")
public class SCSTTransactionApiLog {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_scst_schemetransactionapi_log_SEQ")
    @SequenceGenerator(name = "tbl_scst_schemetransactionapi_log_SEQ", sequenceName = "tbl_scst_schemetransactionapi_log_SEQ", allocationSize = 1)
    @Column(name = "apilogid")
    private Long apilogId;
	
	@Column(name = "transactionid")
    private String transactionId;

    @Column(name = "savedtransactionscount")
    private Integer saveddatacount;

    @Column(name = "errortransactionscount")
    private Integer errordatacount;

    @Lob
    @Column(name = "error_message")
    private String errormessage;

    @Column(name = "created_on")
    private Date createon;
    
    @Column(name = "created_by")
    private String createby;
    
    @Column(name = "statusflag")
    private Integer statusflag;
}

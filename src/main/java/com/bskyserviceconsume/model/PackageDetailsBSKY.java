package com.bskyserviceconsume.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Project : BSKY Service Consume
 * @Auther : Sambit Kumar Pradhan
 * @Created On : 10/04/2023 - 2:12 PM
 */

@Getter
@Setter
@Entity
@Table(name = "PACKAGEDETAILSBSKY")
public class PackageDetailsBSKY {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "SPECIALTY")
    private String specialty;

    @Column(name = "PROCEDURECODE")
    private String procedurecode;

    @Column(name = "SUB_SPECIALTY")
    private String subSpecialty;

    @Column(name = "PACKAGE_CODE")
    private String packageCode;

    @Column(name = "PACKAGE_NAME", length = 4000)
    private String packageName;

    @Column(name = "PACKAGEID")
    private String packageid;

    @Column(name = "PROCEDURE_NAME", length = 4000)
    private String procedureName;

    @Column(name = "COST")
    private String cost;

    @Column(name = "OUTSIDE_STATE_100_BED")
    private String outsideState100Bed;

    @Column(name = "NABH_E_LEVEL_PKG_COST")
    private String nabhELevelPkgCost;

    @Column(name = "NABH_PACKAGE_COST")
    private String nabhPackageCost;

    @Column(name = "OUTSIDE_NABH_E_PKG_COST")
    private String outsideNabhEPkgCost;

    @Column(name = "OUTSIDE_NABH_PACKAGE_COST")
    private String outsideNabhPackageCost;

    @Column(name = "M_DOCUMENT_PRE_AUTHORIZATION", length = 4000)
    private String mDocumentPreAuthorization;

    @Column(name = "M_DOCUMENT_CLAIM_PROCESS", length = 4000)
    private String mDocumentClaimProcess;

    @Column(name = "PACKAGE_CATEGORY_TYPE")
    private String packageCategoryType;

    @Column(name = "MAXIMUM_DAY")
    private String maximumDay;

    @Column(name = "MULTIPLE_PROCEDURES")
    private String multipleProcedures;

    @Column(name = "MENDATORY_PRE_AUTH")
    private String mendatoryPreAuth;

    @Column(name = "FIXED_VERIABLE_LENGTH_STAY")
    private String fixedVeriableLengthStay;

    @Column(name = "DAY_CARE_PROCEDURE")
    private String dayCareProcedure;

}
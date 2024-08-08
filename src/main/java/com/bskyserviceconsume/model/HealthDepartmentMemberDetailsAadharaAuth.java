package com.bskyserviceconsume.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "HEALTHDEPARTMENTMEMBERDETAILS_AADHARAAUTH")
public class HealthDepartmentMemberDetailsAadharaAuth {
    @Id
    @Column(name = "HEALTHMEMEBERSLNO", nullable = false)
    private Long healthmemeberslno;

    @Column(name = "RATIONCARDNUMBER", length = 20)
    private String rationcardnumber;

    @Column(name = "MEMBERID")
    private Long memberid;

    @Column(name = "FULLNAMEENGLISH", length = 1000)
    private String fullnameenglish;

    @Column(name = "FULLNAMEODIYA", length = 1000)
    private String fullnameodiya;

    @Column(name = "AADHARNUMBER", length = 20)
    private String aadharnumber;

    @Column(name = "GENDER", length = 20)
    private String gender;

    @Column(name = "DATEOFBIRTH")
    private Date dateofbirth;

    @Column(name = "AGE", length = 10)
    private String age;

    @Column(name = "RELATIONWITHFAMILYHEAD", length = 100)
    private String relationwithfamilyhead;

    @Column(name = "SCHEMETYPE", length = 20)
    private String schemetype;

    @Column(name = "MOBILENUMBER", length = 20)
    private String mobilenumber;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "ADDITIONDELETIONSTATUS", length = 20)
    private String additiondeletionstatus;

    @Column(name = "EXPORTDATE")
    private Date exportdate;

    @Column(name = "UPDATEDATE")
    private Date updatedate;

    @Column(name = "OLD_DATA_ID")
    private Integer oldDataId;

}
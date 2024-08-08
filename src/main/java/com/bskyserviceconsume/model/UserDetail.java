package com.bskyserviceconsume.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.time.LocalDate;

/**
 * @Project : BSKY Service Consume
 * @Auther : Sambit Kumar Pradhan
 * @Created On : 06/04/2023 - 2:30 PM
 */

@Getter
@Setter
@Entity
@Table(name = "USERDETAILS")
public class UserDetail{
    @Id
    @Column(name = "USERID", nullable = false)
    private Integer id;

    @Column(name = "USERNAME", nullable = false, length = 20)
    private String username;

    @Column(name = "PASSWORD", nullable = false, length = 500)
    private String password;

    @Column(name = "GROUPID", nullable = false)
    private Integer groupid;

    @Column(name = "CREATEDATETIME", nullable = false)
    private Date createdatetime;

    @Column(name = "PHONE")
    private Long phone;

    @Lob
    @Column(name = "EMAIL")
    private String email;

    @Column(name = "COMPANYCODE")
    private String companycode;

    @Column(name = "TPACODE")
    private String tpacode;

    @Lob
    @Column(name = "CREATEDUSERNAME")
    private String createdusername;

    @Column(name = "ISACTIVE")
    private Integer isactive;

    @Column(name = "FULL_NAME", length = 200)
    private String fullName;

    @Column(name = "STATUS_FLAG")
    private Long statusFlag;

    @Column(name = "NON_UPLOAD_BTN_FLG")
    private Long nonUploadBtnFlg;

    @Column(name = "NON_COMP_BTN_FLG")
    private Long nonCompBtnFlg;

    @Column(name = "BTN_VISIBLE_BY")
    private LocalDate btnVisibleBy;

    @Column(name = "ATTEMPTED_STATUS")
    private Boolean attemptedStatus;

    @Column(name = "TMS_LOGIN_STATUS")
    private Boolean tmsLoginStatus;

}
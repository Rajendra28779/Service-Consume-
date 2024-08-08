package com.bskyserviceconsume.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * @Project : BSKY Service Consume
 * @Auther : Sambit Kumar Pradhan
 * @Created On : 10/04/2023 - 2:06 PM
 */

@Getter
@Setter
@Entity
@Table(name = "PACKAGEHEADER")
public class PackageHeader {
    @Id
    @Column(name = "PACKAGEHEADERCODE", nullable = false, length = 8)
    private String id;

    @Column(name = "ID", nullable = false)
    private Long id1;

    @Column(name = "PACKAGEHEADER", length = 128)
    private String packageheader;

    @Column(name = "CREATEDBY", nullable = false)
    private Long createdby;

    @Column(name = "CREATEON", nullable = false)
    private LocalDate createon;

    @Column(name = "UPDATEDBY", nullable = false)
    private Long updatedby;

    @Column(name = "UPDATEDON", nullable = false)
    private LocalDate updatedon;

    @Column(name = "DELETEDFLAG", nullable = false)
    private Boolean deletedflag = false;

}
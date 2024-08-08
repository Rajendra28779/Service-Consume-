package com.bskyserviceconsume.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Project : BSKY Service Consume
 * @Auther : Sambit Kumar Pradhan
 * @Created On : 04/04/2023 - 2:26 PM
 */

@Getter
@Setter
@Entity
@ToString
@Table(name = "GROUP_TYPE")
public class GroupType {
    @Id
    @Column(name = "GROUPID", nullable = false)
    private Long id;

    @Column(name = "TYPE_ID", nullable = false)
    private Long typeId;

    @Column(name = "GROUP_TYPE_NAME", length = 50)
    private String groupTypeName;

    @Column(name = "STATUS")
    private Long status;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "CREATED_ON")
    private Date createdOn;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_ON")
    private Date updatedOn;



}
package com.bskyserviceconsume.repository;

import com.bskyserviceconsume.model.GroupType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @Project : BSKY Service Consume
 * @Auther : Sambit Kumar Pradhan
 * @Created On : 04/04/2023 - 2:26 PM
 */

public interface GroupTypeRepository extends JpaRepository<GroupType, Long> {

    @Query(value = "SELECT GT.typeId FROM GroupType GT WHERE GT.groupTypeName = 'DC'")
    Long getDCGroupTypeIdByGroupName();

    @Query(value = "SELECT GT.typeId FROM GroupType GT WHERE GT.groupTypeName = 'SWASTHYA MITRA'")
    Long getSwathyaMitraGroupTypeIdByGroupName();
}
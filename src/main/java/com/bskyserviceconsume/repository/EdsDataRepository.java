package com.bskyserviceconsume.repository;

import com.bskyserviceconsume.model.EdsData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EdsDataRepository extends JpaRepository<EdsData, Long> {
    @Query(value = "SELECT COUNT(1) FROM EDS_DATA WHERE id = ?1", nativeQuery = true)
    int getDataCountById(Long id);

    @Query(value = "FROM EdsData WHERE id = ?1")
    List<EdsData> findBSKYDataById(Long id);
}
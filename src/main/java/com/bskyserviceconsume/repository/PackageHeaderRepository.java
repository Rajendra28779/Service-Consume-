package com.bskyserviceconsume.repository;

import com.bskyserviceconsume.model.PackageHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PackageHeaderRepository extends JpaRepository<PackageHeader, String> {

    @Query(value = "SELECT\n" +
            "    PH.PACKAGEHEADER,\n" +
            "    PH.PACKAGEHEADERCODE,\n" +
            "    CASE \n" +
            "        WHEN PH.DELETEDFLAG = 0\n" +
            "            THEN 'ACTIVE'\n" +
            "        ELSE 'INACTIVE'\n" +
            "        END AS STATUS\n" +
            "FROM PACKAGEHEADER PH\n" +
            "WHERE PH.DELETEDFLAG = 0", nativeQuery = true)
    List<Object[]> getSpecialityList();
}
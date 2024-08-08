package com.bskyserviceconsume.repository;

import com.bskyserviceconsume.model.PackageDetailsBSKY;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PackageDetailsBSKYRepository extends JpaRepository<PackageDetailsBSKY, Long> {
    @Query(value = "SELECT\n" +
            "    P.SPECIALTY,\n" +
            "    P.PACKAGE_CODE,\n" +
            "    P.PACKAGE_NAME,\n" +
            "    P.PROCEDURECODE,\n" +
            "    P.PROCEDURE_NAME,\n" +
            "    'Non-NABH Package Cost' AS NONNABHPACKAGECOST,\n" +
            "    P.NABH_E_LEVEL_PKG_COST AS NABHELEVELPACKAGECOST,\n" +
            "    P.NABH_PACKAGE_COST,\n" +
            "    P.OUTSIDE_NABH_PACKAGE_COST,\n" +
            "    P.M_DOCUMENT_PRE_AUTHORIZATION,\n" +
            "    P.M_DOCUMENT_CLAIM_PROCESS,\n" +
            "    'ACTIVE' AS STATUS\n" +
            "FROM PACKAGEDETAILSBSKY P\n" +
            "INNER JOIN PACKAGEHEADER  PH ON PH.PACKAGEHEADERCODE = P.PROCEDURECODE AND PH.PACKAGEHEADER = P.SPECIALTY", nativeQuery = true)
    List<Object[]> getPackageDetailsList();
}
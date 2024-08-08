package com.bskyserviceconsume.repository;

import com.bskyserviceconsume.model.HospitalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @Project : BSKY Service Consume
 * @Auther : Sambit Kumar Pradhan
 * @Created On : 04/04/2023 - 2:26 PM
 */
public interface HospitalInfoRepository extends JpaRepository<HospitalInfo, Long> {

    @Query(value = "SELECT\n" +
            "    H.HOSPITAL_NAME,\n" +
            "    H.STATE_CODE,\n" +
            "    S.STATENAME,\n" +
            "    H.DISTRICT_CODE,\n" +
            "    D.DISTRICTNAME,\n" +
            "    H.HOSPITALTYPE,\n" +
            "    H.HOSPITALPINCODE,\n" +
            "    H.HOSPITALADDRESS,\n" +
            "    H.CONTACTPERSON,\n" +
            "    H.EMAIL_ID,\n" +
            "    H.LATITUDE,\n" +
            "    H.LONGITUDE,\n" +
            "    CASE\n" +
            "        WHEN H.STATUS_FLAG = 0\n" +
            "            THEN 'ACTIVE'\n" +
            "        ELSE 'INACTIVE'\n" +
            "    END AS STATUS_FLAG,\n" +
            "   H.HOSPITAL_CODE\n" +
            "FROM HOSPITAL_INFO H\n" +
            "INNER JOIN STATE S ON H.STATE_CODE = S.STATECODE\n" +
            "INNER JOIN DISTRICT D ON S.STATECODE = D.STATECODE AND H.DISTRICT_CODE = D.DISTRICTCODE\n" +
            "WHERE H.STATUS_FLAG = 0\n" +
            "AND ((?2 = 'I' AND TRUNC(H.CREATED_ON) = ?1)\n" +
            "OR (?2 = 'U' AND TRUNC(H.UPDATED_ON) = ?1))\n" +
            "ORDER BY H.HOSPITAL_NAME", nativeQuery = true)
    List<Object[]> getHospitalList(Date date, char flag);

    @Query(value = "SELECT\n" +
            "    H.HOSPITAL_CODE,\n" +
            "    H.HOSPITAL_NAME,\n" +
            "    H.STATE_CODE,\n" +
            "    H.DISTRICT_CODE,\n" +
            "    D.DISTRICTNAME\n" +
            "FROM HOSPITAL_INFO H\n" +
            "LEFT JOIN MOSARKARINSTITUTIONID M ON H.HOSPITAL_CODE=M.HOSPITALCODE\n" +
            "LEFT JOIN DISTRICT D ON H.DISTRICT_CODE=D.DISTRICTCODE AND H.STATE_CODE=D.STATECODE\n" +
            "    WHERE M.MOSARKARINSTITUTIONID IS NULL\n" +
            "    AND H.HOSPITAL_CODE NOT IN ('999999','99999','88888')", nativeQuery = true)
    List<Object[]> getHospitalListHavingNoInstitutionId();
}




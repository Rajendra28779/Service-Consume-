package com.bskyserviceconsume.repository;

import com.bskyserviceconsume.model.HealthDepartmentMemberDetailsAadharaAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HealthDepartmentMemberDetailsAadhaarAuthRepository extends JpaRepository<HealthDepartmentMemberDetailsAadharaAuth, Long>{
    @Query(value = "SELECT\n" +
            "    HDBDAA.GENDER,\n" +
            "    SUM(CASE WHEN HDBDAA.AGE BETWEEN 0 AND 18 THEN 1 ELSE 0 END) AS COUNT0TO18,\n" +
            "    SUM(CASE WHEN HDBDAA.AGE BETWEEN 19 AND 45 THEN 1 ELSE 0 END) AS COUNT19TO45,\n" +
            "    SUM(CASE WHEN HDBDAA.AGE BETWEEN 40 AND 60 THEN 1 ELSE 0 END) AS COUNT40TO60,\n" +
            "    SUM(CASE WHEN HDBDAA.AGE > 60 THEN 1 ELSE 0 END) AS COUNT60PLUS\n" +
            "FROM HEALTHDEPARTMENTMEMBERDETAILS_AADHARAAUTH HDBDAA\n" +
            "GROUP BY HDBDAA.GENDER;", nativeQuery = true)
    List<Object[]> getAgeAndGenderWiseCardHolders();

    @Query(value = "SELECT\n" +
            "    '0-18' AS AGE_RANGE,\n" +
            "    SUM(CASE WHEN HDBDAA.AGE BETWEEN 0 AND 18 THEN 1 ELSE 0 END) AS TOTAL_COUNT,\n" +
            "    SUM(CASE WHEN HDBDAA.AGE BETWEEN 0 AND 18 AND HDBDAA.GENDER = 'Male' THEN 1 ELSE 0 END) AS MALE_COUNT,\n" +
            "    SUM(CASE WHEN HDBDAA.AGE BETWEEN 0 AND 18 AND HDBDAA.GENDER = 'Female' THEN 1 ELSE 0 END) AS FEMALE_COUNT,\n" +
            "    SUM(CASE WHEN HDBDAA.AGE BETWEEN 0 AND 18 AND HDBDAA.GENDER NOT IN ('Male', 'Female') THEN 1 ELSE 0 END) AS OTHER_COUNT\n" +
            "FROM HEALTHDEPARTMENTMEMBERDETAILS_AADHARAAUTH HDBDAA\n" +
            "UNION ALL\n" +
            "SELECT\n" +
            "    '19-45' AS AGE_RANGE,\n" +
            "    SUM(CASE WHEN HDBDAA.AGE BETWEEN 19 AND 45 THEN 1 ELSE 0 END) AS TOTAL_COUNT,\n" +
            "    SUM(CASE WHEN HDBDAA.AGE BETWEEN 19 AND 45 AND HDBDAA.GENDER = 'Male' THEN 1 ELSE 0 END) AS MALE_COUNT,\n" +
            "    SUM(CASE WHEN HDBDAA.AGE BETWEEN 19 AND 45 AND HDBDAA.GENDER = 'Female' THEN 1 ELSE 0 END) AS FEMALE_COUNT,\n" +
            "    SUM(CASE WHEN HDBDAA.AGE BETWEEN 19 AND 45 AND HDBDAA.GENDER NOT IN ('Male', 'Female') THEN 1 ELSE 0 END) AS OTHER_COUNT\n" +
            "FROM HEALTHDEPARTMENTMEMBERDETAILS_AADHARAAUTH HDBDAA\n" +
            "UNION ALL\n" +
            "SELECT\n" +
            "    '40-60' AS AGE_RANGE,\n" +
            "    SUM(CASE WHEN HDBDAA.AGE BETWEEN 40 AND 60 THEN 1 ELSE 0 END) AS TOTAL_COUNT,\n" +
            "    SUM(CASE WHEN HDBDAA.AGE BETWEEN 40 AND 60 AND HDBDAA.GENDER = 'Male' THEN 1 ELSE 0 END) AS MALE_COUNT,\n" +
            "    SUM(CASE WHEN HDBDAA.AGE BETWEEN 40 AND 60 AND HDBDAA.GENDER = 'Female' THEN 1 ELSE 0 END) AS FEMALE_COUNT,\n" +
            "    SUM(CASE WHEN HDBDAA.AGE BETWEEN 40 AND 60 AND HDBDAA.GENDER NOT IN ('Male', 'Female') THEN 1 ELSE 0 END) AS OTHER_COUNT\n" +
            "FROM HEALTHDEPARTMENTMEMBERDETAILS_AADHARAAUTH HDBDAA\n" +
            "UNION ALL\n" +
            "SELECT\n" +
            "    '60+' AS AGE_RANGE,\n" +
            "    SUM(CASE WHEN HDBDAA.AGE > 60 THEN 1 ELSE 0 END) AS TOTAL_COUNT,\n" +
            "    SUM(CASE WHEN HDBDAA.AGE > 60 AND HDBDAA.GENDER = 'Male' THEN 1 ELSE 0 END) AS MALE_COUNT,\n" +
            "    SUM(CASE WHEN HDBDAA.AGE > 60 AND HDBDAA.GENDER = 'Female' THEN 1 ELSE 0 END) AS FEMALE_COUNT,\n" +
            "    SUM(CASE WHEN HDBDAA.AGE > 60 AND HDBDAA.GENDER NOT IN ('Male', 'Female') THEN 1 ELSE 0 END) AS OTHER_COUNT\n" +
            "FROM HEALTHDEPARTMENTMEMBERDETAILS_AADHARAAUTH HDBDAA", nativeQuery = true)
    List<Object[]> getAgeAndGenderWiseCardHolders1();

    @Query(value = "SELECT AGE_RANGE,\n" +
            "SUM(CASE WHEN UPPER(GENDER)='MALE'  THEN 1 ELSE 0 END)AS MALE_COUNT,\n" +
            "SUM(CASE WHEN UPPER(GENDER)='FEMALE'THEN 1 ELSE 0 END)AS FEMALE_COUNT,\n" +
            "SUM(CASE WHEN UPPER(GENDER)='OTHERS'THEN 1 ELSE 0 END)AS OTHER_COUNT,\n" +
            "SUM(CASE WHEN UPPER(GENDER) IN ('MALE','FEMALE','OTHERS')  THEN 1 ELSE 0 END)AS TOTAL_COUNT\n" +
            "FROM (SELECT GENDER,\n" +
            "       CASE WHEN AGE BETWEEN 0 AND 18 THEN '0_18'\n" +
            "            WHEN AGE BETWEEN 19 AND 45 THEN '19_45'\n" +
            "            WHEN AGE BETWEEN 40 AND 60 THEN '40_60'\n" +
            "            WHEN AGE > 60 THEN '60_PLUSE'\n" +
            "       END AS AGE_RANGE\n" +
            "FROM HEALTHDEPARTMENTMEMBERDETAILS_AADHARAAUTH)\n" +
            "GROUP BY AGE_RANGE ORDER BY AGE_RANGE", nativeQuery = true)
    List<Object[]> getAgeAndGenderWiseCardHolders2();

    @Query(value = "SELECT V.DISTRICT_CODE,V.DISTRICT\n" +
            ",COUNT(DISTINCT V.URN) TOTAL_CARD\n" +
            ",COUNT(DISTINCT v.URN||'#'||TO_CHAR(v.MEMBERID)) TOTAL_CARD_MEMBER\n" +
            "FROM VW_HEALTH_DEPT_MEM_DET V\n" +
            "WHERE V.STATUS=1\n" +
            "GROUP BY V.DISTRICT_CODE,V.DISTRICT", nativeQuery = true)
    List<Object[]> getDistrictWiseCardHoldersAndBeneficiaries();
}

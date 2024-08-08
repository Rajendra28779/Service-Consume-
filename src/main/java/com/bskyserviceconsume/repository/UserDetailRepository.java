package com.bskyserviceconsume.repository;

import com.bskyserviceconsume.model.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @Project : BSKY Service Consume
 * @Auther : Sambit Kumar Pradhan
 * @Created On : 04/04/2023 - 2:26 PM
 */

public interface UserDetailRepository extends JpaRepository<UserDetail, Integer> {

    @Query(value = "SELECT\n" +
            "    H.HOSPITAL_CODE,\n" +
            "    H.HOSPITAL_NAME,\n" +
            "    U.FULL_NAME,\n" +
            "    U.PHONE,\n" +
            "    U.EMAIL,\n" +
            "    CASE\n" +
            "        WHEN U.GROUPID = 6\n" +
            "            THEN 'DC'\n" +
            "        ELSE 'OTHERS'\n" +
            "        END AS TYPE,\n" +
            "    DIU.DC_SELFIE_IMAGE,\n" +
            "    CASE\n" +
            "        WHEN U.STATUS_FLAG = 0\n" +
            "            THEN 'ACTIVE'\n" +
            "        ELSE 'INACTIVE'\n" +
            "        END AS STATUS_FLAG\n" +
            "FROM HOSPITAL_INFO H\n" +
            "LEFT JOIN USERDETAILS U ON U.USERID = H.ASSIGNED_DC\n" +
            "LEFT JOIN DC_IMAGE_UPLOAD DIU on U.USERID = DIU.USER_ID\n" +
            "WHERE U.GROUPID = ?1\n" +
            "AND H.STATUS_FLAG = 0\n" +
            "AND TRUNC(U.CREATEDATETIME) = ?2\n" +
            "ORDER BY H.HOSPITAL_NAME\n", nativeQuery = true)
    List<Object[]> getDCUserList(Long dcGroupTypeId, Date createdDate);

    @Query(value = "SELECT\n" +
            "    H.HOSPITAL_CODE,\n" +
            "    H.HOSPITAL_NAME,\n" +
            "    U.FULL_NAME,\n" +
            "    U.PHONE,\n" +
            "    U.EMAIL,\n" +
            "    CASE\n" +
            "        WHEN U.GROUPID = 14\n" +
            "            THEN 'SWASTHYAMITRA'\n" +
            "        ELSE 'OTHERS'\n" +
            "        END AS TYPE,\n" +
            "    NULL AS SWASTHYAMITRA_IMAGE,\n" +
            "    CASE\n" +
            "        WHEN U.STATUS_FLAG = 0\n" +
            "            THEN 'ACTIVE'\n" +
            "        ELSE 'INACTIVE'\n" +
            "        END AS STATUS_FLAG\n" +
            "FROM USER_SWATHYA_MITRA_MAPPING USM\n" +
            "LEFT JOIN USERDETAILS U ON U.USERID = USM.USER_ID\n" +
            "LEFT JOIN HOSPITAL_INFO H ON USM.HOSPITALCODE = H.HOSPITAL_CODE\n" +
            "WHERE U.GROUPID = ?1\n" +
            "AND USM.STATUSFLAG = 0\n" +
            "  AND TRUNC(USM.CREATEDON) = ?2", nativeQuery = true)
    List<Object[]> getSwasthyMitraUserList(Long swasthyMitraGroupTypeId, Date createdDate);
}
package com.bskyserviceconsume.service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Project : BSKY Service Consume
 * @Auther : Sambit Kumar Pradhan
 * @Created On : 04/04/2023 - 11:29 AM
 */
public interface MasterAPIService {
    List<Map<String, Object>> getHospitalList(String body, HttpServletRequest httpServletRequest) throws Exception;
    List<Map<String, Object>> getUserList(String body, HttpServletRequest httpServletRequest) throws Exception;
    List<Map<String, Object>> getPackageList(HttpServletRequest httpServletRequest) throws Exception;
    List<Map< String, Object>> getSpecialityList(HttpServletRequest httpServletRequest) throws Exception;
    Map<String, Object> getCardBalance(String body, HttpServletRequest httpServletRequest) throws Exception;
    Map<String, Object> getAgeAndGenderWiseCardHolders() throws Exception;
    List<Map<String, Object>> getDistrictWiseCardHoldersAndBeneficiaries() throws Exception;
}

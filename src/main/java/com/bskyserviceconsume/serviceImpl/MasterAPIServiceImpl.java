package com.bskyserviceconsume.serviceImpl;

import com.bskyserviceconsume.model.APIServiceLog;
import com.bskyserviceconsume.repository.*;
import com.bskyserviceconsume.service.MasterAPIService;
import com.bskyserviceconsume.util.SaveAPIErrorLog;
import com.bskyserviceconsume.util.SecurityEncrypt;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Project : BSKY Service Consume
 * @Auther : Sambit Kumar Pradhan
 * @Created On : 04/04/2023 - 11:29 AM
 */
@Service
public class MasterAPIServiceImpl implements MasterAPIService {

    @Autowired
    private Logger logger;

    @Autowired
    private GroupTypeRepository groupTypeRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private APIServiceErrorLogRepository apiServiceErrorLogRepository;

    @Autowired
    private APIServiceLogRepository apiServiceLogRepository;
    @Autowired
    private HealthDepartmentMemberDetailsAadhaarAuthRepository healthDepartmentMemberDetailsAadhaarAuthRepository;

    @Override
    public List<Map<String, Object>> getHospitalList(String body, HttpServletRequest httpServletRequest) throws Exception {
        logger.info("Inside getHospitalList method of MasterAPIServiceImpl");
        List<Map<String, Object>> hospitalMapList = new ArrayList<>();
        JSONObject jsonObject;
        try {
            APIServiceLog apiServiceLog = new APIServiceLog();
            apiServiceLog.setApiName("Hospital List");
            apiServiceLog.setApiId(2);
            apiServiceLog.setCreatedBy("System");
            apiServiceLog.setCreatedOn(new Date());
            apiServiceLog.setStatusFlag(0);
            apiServiceLog.setInputData(new JSONObject()
                    .put("Authorization", httpServletRequest.getHeader("Authorization"))
                    .put("date", !new JSONObject(body).getString("date").equals("") ? new SimpleDateFormat("dd-MMM-yyyy").format(new SimpleDateFormat("dd-MM-yyyy").parse(new JSONObject(body).getString("date"))) : "")
                    .put("flag", new JSONObject(body).getString("flag")).toString()
            );
            apiServiceLog.setApiHitTime(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new Date())));

            if (httpServletRequest.getHeader("Authorization") == null || httpServletRequest.getHeader("Authorization").equals(""))
                throw new Exception("Authorization is required");
            else if (!SecurityEncrypt.checkEncryptedSecurityKey(httpServletRequest.getHeader("Authorization")))
                throw new Exception("Invalid Authorization");
            else {
                jsonObject = new JSONObject(body);

                StoredProcedureQuery storedProcedureQuery = this.entityManager.createStoredProcedureQuery("USP_MST_GET_HOSPITAL_LIST")
                        .registerStoredProcedureParameter("P_FLAG", Character.class, ParameterMode.IN)
                        .registerStoredProcedureParameter("P_DATE", Date.class, ParameterMode.IN)
                        .registerStoredProcedureParameter("P_MSG_OUT", void.class, ParameterMode.REF_CURSOR);

                storedProcedureQuery.setParameter("P_FLAG", !Objects.equals(jsonObject.getString("flag"), "") ? jsonObject.getString("flag").charAt(0) : null);
                storedProcedureQuery.setParameter("P_DATE", !Objects.equals(jsonObject.getString("date"), "") ? new SimpleDateFormat("dd-MM-yyyy").parse(jsonObject.getString("date")) : null);
                storedProcedureQuery.execute();

                List<?> hospitalList = storedProcedureQuery.getResultList();
                if (hospitalList.size() > 0) {
                    hospitalList.forEach(o -> {
                        Object[] object = (Object[]) o;
                        Map<String, Object> hospitalMap = new LinkedHashMap<>();

                        hospitalMap.put("hospitalName", object[0] != null ? object[0] : "-NA-");
                        hospitalMap.put("stateCode", object[1] != null ? object[1] : "-NA-");
                        hospitalMap.put("stateName", object[2] != null ? object[2] : "-NA-");
                        hospitalMap.put("distCode", object[3] != null ? object[3] : "-NA-");
                        hospitalMap.put("distName", object[4] != null ? object[4] : "-NA-");
                        hospitalMap.put("blockCode", object[5] != null ? object[5] : "-NA-");
                        hospitalMap.put("blockName", object[6] != null ? object[6] : "-NA-");
                        hospitalMap.put("hospitalCode", object[7] != null ? object[7] : "-NA-");
                        hospitalMap.put("type", object[8] != null ? object[8] : "-NA-");
                        hospitalMap.put("facilityType ", object[9] != null ? object[9] : "-NA-");
                        hospitalMap.put("pinCode", object[10] != null ? object[10] : "-NA-");
                        hospitalMap.put("address", object[11] != null ? object[11] : "-NA-");
                        hospitalMap.put("hospitalContactPerson", object[12] != null ? object[12] : "-NA-");
                        hospitalMap.put("hospitalContactNumber", object[13] != null ? object[13] : "-NA-");
                        hospitalMap.put("hospitalEmail", object[14] != null ? object[14] : "-NA-");
                        hospitalMap.put("latitude", object[15] != null ? object[15] : "-NA-");
                        hospitalMap.put("longitude", object[16] != null ? object[16] : "-NA-");
                        hospitalMap.put("mouStartDate", object[17] != null ? object[17] : "-NA-");
                        hospitalMap.put("mouEndDate", object[18] != null ? object[18] : "-NA-");
                        hospitalMap.put("status", object[19] != null ? object[19] : "-NA-");

                        hospitalMapList.add(hospitalMap);
                    });
                    apiServiceLog.setOutputData(new Gson().toJson(hospitalMapList));
                    apiServiceLog.setDataSize(hospitalMapList.size());
                    apiServiceLog.setApiEndTime(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new Date())));
                    apiServiceLogRepository.save(apiServiceLog);
                } else {
                    apiServiceLog.setOutputData(null);
                    apiServiceLog.setDataSize(0);
                    apiServiceLog.setApiEndTime(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new Date())));
                    apiServiceLogRepository.save(apiServiceLog);
                    throw new Exception("No Hospital List Found");
                }
            }
        } catch (Exception e) {
            logger.error("Exception occurred in getHospitalList method of MasterAPIServiceImpl", e);
            SaveAPIErrorLog saveAPIErrorLog = new SaveAPIErrorLog(apiServiceErrorLogRepository);
            saveAPIErrorLog.saveAPIErrorLog(2, "HospitalList", e);
            throw new Exception(e.getMessage());
        }
        return hospitalMapList;
    }

    @Override
    public List<Map<String, Object>> getUserList(String body, HttpServletRequest httpServletRequest) throws Exception {
        logger.info("Inside getUserList method of MasterAPIServiceImpl");
        List<Map<String, Object>> userList = null;
        JSONObject jsonObject;
        try {
            APIServiceLog apiServiceLog = new APIServiceLog();
            apiServiceLog.setApiName("User List");
            apiServiceLog.setApiId(3);
            apiServiceLog.setCreatedBy("System");
            apiServiceLog.setCreatedOn(new Date());
            apiServiceLog.setStatusFlag(0);
            apiServiceLog.setApiHitTime(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new Date())));
            apiServiceLog.setInputData(new JSONObject()
                    .put("Authorization", httpServletRequest.getHeader("Authorization"))
                    .put("date", !new JSONObject(body).getString("date").equals("") ? new SimpleDateFormat("dd-MMM-yyyy").format(new SimpleDateFormat("dd-MM-yyyy").parse(new JSONObject(body).getString("date"))) : "")
                    .toString()
            );

            if (httpServletRequest.getHeader("Authorization") == null || httpServletRequest.getHeader("Authorization").equals(""))
                throw new Exception("Authorization is required");
            else if (!SecurityEncrypt.checkEncryptedSecurityKey(httpServletRequest.getHeader("Authorization")))
                throw new Exception("Invalid Authorization");
            else {
                jsonObject = new JSONObject(body);

                Long dcGroupTypeId = groupTypeRepository.getDCGroupTypeIdByGroupName();
                StoredProcedureQuery storedProcedureQuery = this.entityManager.createStoredProcedureQuery("USP_MST_GET_USER_LIST")
                        .registerStoredProcedureParameter("P_GROUP_ID", Long.class, ParameterMode.IN)
                        .registerStoredProcedureParameter("P_DATE", Date.class, ParameterMode.IN)
                        .registerStoredProcedureParameter("P_MSG_OUT", void.class, ParameterMode.REF_CURSOR);

                storedProcedureQuery.setParameter("P_GROUP_ID", dcGroupTypeId);
                storedProcedureQuery.setParameter("P_DATE", !jsonObject.getString("date").equals("") ? new SimpleDateFormat("dd-MM-yyyy").parse(jsonObject.getString("date")) : null);
                storedProcedureQuery.execute();

                List<?> dcUserList = storedProcedureQuery.getResultList();
                if (dcUserList != null && dcUserList.size() > 0) {
                    userList = getMapList(dcUserList);

                } else
                    System.out.println("No DC User Found");

                Long swasthyMitraGroupTypeId = groupTypeRepository.getSwathyaMitraGroupTypeIdByGroupName();
                storedProcedureQuery = this.entityManager.createStoredProcedureQuery("USP_MST_GET_USER_LIST")
                        .registerStoredProcedureParameter("P_GROUP_ID", Long.class, ParameterMode.IN)
                        .registerStoredProcedureParameter("P_DATE", Date.class, ParameterMode.IN)
                        .registerStoredProcedureParameter("P_MSG_OUT", void.class, ParameterMode.REF_CURSOR);

                storedProcedureQuery.setParameter("P_GROUP_ID", swasthyMitraGroupTypeId);
                storedProcedureQuery.setParameter("P_DATE", !jsonObject.getString("date").equals("") ? new SimpleDateFormat("dd-MM-yyyy").parse(jsonObject.getString("date")) : null);
                storedProcedureQuery.execute();

                List<?> swasthyMitraUserList = storedProcedureQuery.getResultList();
                if (swasthyMitraUserList != null && swasthyMitraUserList.size() > 0) {
                    if (userList != null && userList.size() > 0)
                        userList.addAll(getMapList(swasthyMitraUserList));
                    else
                        userList = getMapList(swasthyMitraUserList);
                } else
                    System.out.println("No Swasthya Mitra User Found");

                if (userList != null && userList.size() > 0) {
                    apiServiceLog.setOutputData(new Gson().toJson(userList));
                    apiServiceLog.setDataSize(userList.size());
                    apiServiceLog.setApiEndTime(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new Date())));
                    apiServiceLogRepository.save(apiServiceLog);
                } else {
                    apiServiceLog.setOutputData(null);
                    apiServiceLog.setDataSize(0);
                    apiServiceLog.setApiEndTime(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new Date())));
                    apiServiceLogRepository.save(apiServiceLog);
                    throw new Exception("No User List Found");
                }
            }
        } catch (Exception e) {
            logger.error("Exception occurred in getUserList method of MasterAPIServiceImpl", e);
            SaveAPIErrorLog saveAPIErrorLog = new SaveAPIErrorLog(apiServiceErrorLogRepository);
            saveAPIErrorLog.saveAPIErrorLog(3, "UserList", e);
            throw new Exception(e.getMessage());
        }
        if (userList != null && userList.size() > 0)
            return userList;
        else
            throw new Exception("No User Found");
    }

    public List<Map<String, Object>> getMapList(List<?> objects) {
        logger.info("Inside getMapList method of MasterAPIServiceImpl");
        List<Map<String, Object>> userList = new ArrayList<>();
        objects.forEach(o -> {
            Object[] object = (Object[]) o;
            Map<String, Object> userMap = new LinkedHashMap<>();
            userMap.put("hospitalCode", object[0] != null ? object[0] : "-NA-");
            userMap.put("hospitalName", object[1] != null ? object[1] : "-NA-");
            userMap.put("fullName", object[2] != null ? object[2] : "-NA-");
            userMap.put("contactNo", object[3] != null ? object[3] : "-NA-");
            userMap.put("alternatePhone", object[4] != null ? object[4] : "-NA-");
            try {
                Clob clob = (Clob) object[5];
                userMap.put("email", clob != null ? clob.getSubString(1, (int) clob.length()) : "-NA-");
            } catch (SQLException e) {
                logger.error("Exception occurred in getMapList method of MasterAPIServiceImpl", e);
                throw new RuntimeException(e);
            }
            userMap.put("type", object[6] != null ? object[6] : "-NA-");
            userMap.put("image", object[7] != null ? object[7] : "-NA-");
            userMap.put("status", object[8] != null ? object[8] : "-NA-");
            userList.add(userMap);
        });
        return userList;
    }

    @Override
    public List<Map<String, Object>> getPackageList(HttpServletRequest httpServletRequest) throws Exception {
        logger.info("Inside getPackageList method of MasterAPIServiceImpl");
        List<Map<String, Object>> packageMapList = new ArrayList<>();
        try {
            APIServiceLog apiServiceLog = new APIServiceLog();
            apiServiceLog.setApiName("Package List");
            apiServiceLog.setApiId(4);
            apiServiceLog.setCreatedBy("System");
            apiServiceLog.setCreatedOn(new Date());
            apiServiceLog.setStatusFlag(0);
            apiServiceLog.setInputData(new JSONObject()
                    .put("Authorization", httpServletRequest.getHeader("Authorization")).toString()
            );
            apiServiceLog.setApiHitTime(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new Date())));

            if (httpServletRequest.getHeader("Authorization") == null || httpServletRequest.getHeader("Authorization").equals(""))
                throw new Exception("Authorization is required");
            else if (!SecurityEncrypt.checkEncryptedSecurityKey(httpServletRequest.getHeader("Authorization")))
                throw new Exception("Invalid Authorization");
            else {
                StoredProcedureQuery storedProcedureQuery = this.entityManager.createStoredProcedureQuery("USP_MST_GET_PACKAGE_LIST")
                        .registerStoredProcedureParameter("P_MSG_OUT", void.class, ParameterMode.REF_CURSOR);

                storedProcedureQuery.execute();

                List<?> packageList = storedProcedureQuery.getResultList();
                if (packageList != null && packageList.size() > 0) {
                    packageList.forEach(o -> {
                        Object[] object = (Object[]) o;
                        Map<String, Object> packageMap = new LinkedHashMap<>();
                        packageMap.put("speciality", object[0] != null ? object[0] : "-NA-");
                        packageMap.put("packageCode", object[1] != null ? object[1] : "-NA-");
                        packageMap.put("packageName", object[2] != null ? object[2] : "-NA-");
                        packageMap.put("procedureCode", object[3] != null ? object[3] : "-NA-");
                        packageMap.put("procedureName", object[4] != null ? object[4] : "-NA-");
                        packageMap.put("nonNABHPackageCost", object[5] != null ? object[5] : "-NA-");
                        packageMap.put("NABHEntryLevelPackageCost", object[6] != null ? object[6] : "-NA-");
                        packageMap.put("NABHPackageCost", object[7] != null ? object[7] : "-NA-");
                        packageMap.put("outSideStateNABHPackageCost", object[8] != null ? object[8] : "-NA-");
                        packageMap.put("mandatoryDocumentsPreAuthorization", object[9] != null ? object[9] : "-NA-");
                        packageMap.put("mandatoryDocumentsClaimProcessing", object[10] != null ? object[10] : "-NA-");
                        packageMap.put("status", object[11] != null ? object[11] : "-NA-");

                        packageMapList.add(packageMap);
                    });
                } else
                    System.out.println("No Package Found");

                if (packageMapList.size() > 0) {
                    apiServiceLog.setOutputData(new Gson().toJson(packageMapList));
                    apiServiceLog.setDataSize(packageMapList.size());
                    apiServiceLog.setApiEndTime(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new Date())));
                    apiServiceLogRepository.save(apiServiceLog);
                } else {
                    apiServiceLog.setOutputData(null);
                    apiServiceLog.setDataSize(0);
                    apiServiceLog.setApiEndTime(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new Date())));
                    apiServiceLogRepository.save(apiServiceLog);
                }
            }
        } catch (Exception e) {
            logger.error("Exception occurred in getPackageList method of MasterAPIServiceImpl", e);
            SaveAPIErrorLog saveAPIErrorLog = new SaveAPIErrorLog(apiServiceErrorLogRepository);
            saveAPIErrorLog.saveAPIErrorLog(4, "PackageList", e);
            throw new Exception(e.getMessage());
        }
        return packageMapList;
    }

    @Override
    public List<Map<String, Object>> getSpecialityList(HttpServletRequest httpServletRequest) throws Exception {
        logger.info("Inside getSpecialityList method of MasterAPIServiceImpl");
        List<Map<String, Object>> specialityMapList = new ArrayList<>();
        try {
            APIServiceLog apiServiceLog = new APIServiceLog();
            apiServiceLog.setApiName("Speciality List");
            apiServiceLog.setApiId(5);
            apiServiceLog.setCreatedBy("System");
            apiServiceLog.setCreatedOn(new Date());
            apiServiceLog.setStatusFlag(0);
            apiServiceLog.setInputData(new JSONObject()
                    .put("Authorization", httpServletRequest.getHeader("Authorization")).toString()
            );
            apiServiceLog.setApiHitTime(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new Date())));

            if (httpServletRequest.getHeader("Authorization") == null || httpServletRequest.getHeader("Authorization").equals(""))
                throw new Exception("Authorization is required");
            else if (!SecurityEncrypt.checkEncryptedSecurityKey(httpServletRequest.getHeader("Authorization")))
                throw new Exception("Invalid Authorization");
            else {
                StoredProcedureQuery storedProcedureQuery = this.entityManager.createStoredProcedureQuery("USP_MST_GET_SPECIALTY_LIST")
                        .registerStoredProcedureParameter("P_MSG_OUT", void.class, ParameterMode.REF_CURSOR);

                storedProcedureQuery.execute();

                List<?> specialityList = storedProcedureQuery.getResultList();
                System.out.println("specialityList : " + specialityList.size());
                if (specialityList.size() > 0) {
                    specialityList.forEach(o -> {
                        Object[] object = (Object[]) o;
                        Map<String, Object> specialityMap = new LinkedHashMap<>();
                        specialityMap.put("specialityName", object[0] != null ? object[0] : "-NA-");
                        specialityMap.put("specialityCode", object[1] != null ? object[1] : "-NA-");
                        specialityMap.put("status", object[2] != null ? object[2] : "-NA-");

                        specialityMapList.add(specialityMap);
                    });
                    apiServiceLog.setOutputData(new Gson().toJson(specialityMapList));
                    apiServiceLog.setDataSize(specialityMapList.size());
                    apiServiceLog.setApiEndTime(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new Date())));
                    apiServiceLogRepository.save(apiServiceLog);

                    return specialityMapList;
                } else {
                    apiServiceLog.setOutputData(null);
                    apiServiceLog.setDataSize(0);
                    apiServiceLog.setApiEndTime(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new Date())));
                    apiServiceLogRepository.save(apiServiceLog);
                    throw new Exception("No Speciality List Found!");
                }
            }
        } catch (Exception e) {
            logger.error("Exception occurred in getSpecialityList method of MasterAPIServiceImpl", e);
            SaveAPIErrorLog saveAPIErrorLog = new SaveAPIErrorLog(apiServiceErrorLogRepository);
            saveAPIErrorLog.saveAPIErrorLog(5, "SpecialityList", e);
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Map<String, Object> getCardBalance(String body, HttpServletRequest httpServletRequest) {
        //Code To Be Written Here
        return null;
    }

    @Override
    public Map<String, Object> getAgeAndGenderWiseCardHolders() {
        try {
            Map<String, Object> response = new LinkedHashMap<>();
            List<Object[]> ageAndGenderWiseCardHoldersList = healthDepartmentMemberDetailsAadhaarAuthRepository
                    .getAgeAndGenderWiseCardHolders1();


            ageAndGenderWiseCardHoldersList.forEach(o -> {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("male", o[1] != null ? o[1] : "-NA-");
                map.put("female", o[2] != null ? o[2] : "-NA-");
                map.put("other", o[3] != null ? o[3] : "-NA-");

                response.put(o[0] != null ? o[0].toString() : "-NA-", map);
            });
            return response;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<Map<String, Object>> getDistrictWiseCardHoldersAndBeneficiaries() {
        try {
            List<Map<String, Object>> responseList = new ArrayList<>();
            List<Object[]> districtWiseCardHoldersAndBeneficiariesList = healthDepartmentMemberDetailsAadhaarAuthRepository
                    .getDistrictWiseCardHoldersAndBeneficiaries();

            districtWiseCardHoldersAndBeneficiariesList.forEach(o -> {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("districtCode", o[0] != null ? o[0] : "-NA-");
                map.put("districtName", o[1] != null ? o[1] : "-NA-");
                map.put("householdsCovered", o[2] != null ? o[2] : "-NA-");
                map.put("totalCardHolders", o[3] != null ? o[3] : "-NA-");

                responseList.add(map);
            });
            return responseList;
        } catch (Exception e) {
            throw e;
        }
    }
}

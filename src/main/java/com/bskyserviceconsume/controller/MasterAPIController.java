package com.bskyserviceconsume.controller;

import com.bskyserviceconsume.service.MasterAPIService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Project : BSKY Service Consume
 * @Auther : Sambit Kumar Pradhan
 * @Created On : 07/04/2023 - 11:15 AM
 */
@RestController
@RequestMapping("/master")
public class MasterAPIController {

    @Autowired
    private Logger logger;

    @Autowired
    private MasterAPIService masterAPIService;

    /**
     * This method is used to get Hospital List
     *
     * @Auther : Sambit Kumar Pradhan
     */
    @PostMapping("/getHospitalList")
    public ResponseEntity<?> getHospitalList(@RequestBody String body, HttpServletRequest httpServletRequest) {
        logger.info("Inside getHospitalList of MasterAPIController");
        List<Map<String, Object>> hospitalList;
        Map<String , Object> response = new LinkedHashMap<>();
        try {
            hospitalList = masterAPIService.getHospitalList(body, httpServletRequest);
            if (!hospitalList.isEmpty()) {
                response.put("status", HttpStatus.OK.value());
                response.put("message", "Data Fetched Successfully");
                response.put("size", hospitalList.size() != 0 ? hospitalList.size() : 0);
                response.put("data", hospitalList);
            }
        } catch (Exception e) {
            logger.error("Exception Occurred in getHospitalList of MasterAPIController" + e.getMessage());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", e.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This method is used to get User List
     *
     * @Auther : Sambit Kumar Pradhan
     */
    @PostMapping("/getUserList")
    public ResponseEntity<?> getUserList(@RequestBody String body, HttpServletRequest httpServletRequest) {
        logger.info("Inside getUserList of MasterAPIController");
        List<Map<String, Object>> userList;
        Map<String , Object> response = new LinkedHashMap<>();
        try {
            userList = masterAPIService.getUserList(body, httpServletRequest);
            if (!userList.isEmpty()) {
                response.put("status", HttpStatus.OK.value());
                response.put("message", "Data Fetched Successfully");
                response.put("size", userList.size() != 0 ? userList.size() : 0);
                response.put("data", userList);
            }
        } catch (Exception e) {
            logger.error("Exception Occurred in getUserList of MasterAPIController" + e.getMessage());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", e.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This method is used to get Package List
     *
     * @Auther : Sambit Kumar Pradhan
     */
    @PostMapping("/getPackageDetailsList")
    public ResponseEntity<?> getPackageList(HttpServletRequest httpServletRequest) {
        logger.info("Inside getPackageList of MasterAPIController");
        List<Map<String, Object>> packageList;
        Map<String , Object> response = new LinkedHashMap<>();
        try {
            packageList = masterAPIService.getPackageList(httpServletRequest);
            if (packageList .size() > 0) {
                response.put("status", HttpStatus.OK.value());
                response.put("message", "Data Fetched Successfully");
                response.put("size", packageList.size() != 0 ? packageList.size() : 0);
                response.put("data", packageList);
            }
        } catch (Exception e) {
            logger.error("Exception Occurred in getPackageList of MasterAPIController" + e.getMessage());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", e.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This method is used to get Speciality List
     *
     * @Auther : Sambit Kumar Pradhan
     */
    @PostMapping("/getSpecialityList")
    public ResponseEntity<?> getSpecialityList(HttpServletRequest httpServletRequest) {
        logger.info("Inside getSpecialityList of MasterAPIController");
        List<Map<String, Object>> specialityList;
        Map<String , Object> response = new LinkedHashMap<>();
        try {
            specialityList = masterAPIService.getSpecialityList(httpServletRequest);
            if (!specialityList.isEmpty()) {
                response.put("status", HttpStatus.OK.value());
                response.put("message", "Data Fetched Successfully");
                response.put("size", !specialityList.isEmpty() ? specialityList.size() : 0);
                response.put("data", specialityList);
            }
        } catch (Exception e) {
            logger.error("Exception Occurred in getSpecialityList of MasterAPIController :", e);
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", e.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This method is used to get Card Balance
     *
     * @Auther : Sambit Kumar Pradhan
     */
    @PostMapping("/getCardBalance")
    public ResponseEntity< Map<String , Object>> getCardBalance(@RequestBody String body, HttpServletRequest httpServletRequest) {
        logger.info("Inside getCardBalance of MasterAPIController");
        Map<String , Object> response = new LinkedHashMap<>();
        try {
            response.put("status", HttpStatus.OK.value());
            response.put("message", "Data Fetched Successfully");
            response.put("data", masterAPIService.getCardBalance(body, httpServletRequest));
        } catch (Exception e) {
            logger.error("Exception Occurred in getCardBalance of MasterAPIController :", e);
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", e.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

/**
 * This method is used to get Age And Gender Wise Card Holders
 *
 * @Auther : Sambit Kumar Pradhan
 */
    @PostMapping(value = "/getAgeAndGenderWiseCardHolders")
    public ResponseEntity<Map<String , Object>> getAgeAndGenderWiseCardHolders() {
        logger.info("Inside getAgeAndGenderWiseCardHolders of MasterAPIController");
        Map<String , Object> response = new LinkedHashMap<>();
        try {
            Map<String, Object> data = masterAPIService.getAgeAndGenderWiseCardHolders();
            response.put("status", HttpStatus.OK.value());
            response.put("message", "Data Fetched Successfully");
            response.put("data", data);
        } catch (Exception e) {
            logger.error("Exception Occurred in getAgeAndGenderWiseCardHolders of MasterAPIController :", e);
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", e.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This method is used to get District Wise Card Holders And Beneficiaries
     *
     * @Auther : Sambit Kumar Pradhan
     */
    @PostMapping(value = "/getDistrictWiseCardHoldersAndBeneficiaries")
    public ResponseEntity<Map<String , Object>> getDistrictWiseCardHoldersAndBeneficiaries() {
        logger.info("Inside getDistrictWiseCardHoldersAndBeneficiaries of MasterAPIController");
        Map<String , Object> response = new LinkedHashMap<>();
        try {
            List<Map<String, Object>> responseList = masterAPIService.getDistrictWiseCardHoldersAndBeneficiaries();
            response.put("status", HttpStatus.OK.value());
            response.put("message", "Data Fetched Successfully");
            response.put("data", responseList);
        } catch (Exception e) {
            logger.error("Exception Occurred in getDistrictWiseCardHoldersAndBeneficiaries of MasterAPIController :", e);
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", e.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
package com.bskyserviceconsume.controller;

import com.bskyserviceconsume.service.SPDPService;
import com.bskyserviceconsume.util.CommonClassHelper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Project : BSKY Service Consume
 * @Auther : Sambit Kumar Pradhan
 * @Created On : 02/06/2023 - 3:06 PM
 */
@RestController
@RequestMapping(value = "/api")
public class SPDPController {

    @Autowired
    private Logger logger;

    @Autowired
    private SPDPService spdpService;

    /**
     * This method is used to get BSKY Transaction Data
     *
     * @Auther : Sambit Kumar Pradhan
     */
    @PostMapping(value = "/getBSKYTransactionData")
    public ResponseEntity<?> getBSKYTransactionData(HttpServletRequest httpServletRequest, @RequestBody Map<String, Object> body) {
        logger.info("Inside getBSKYTransactionData of SPDPController");
        Map<String, Object> response = new LinkedHashMap<>();
        List<Map<String, Object>> bskyDataList;
        try {
            bskyDataList = spdpService.getBskyTransactionData(httpServletRequest, body);
            if (!bskyDataList.isEmpty()) {
                response.put("status", HttpStatus.OK.value());
                response.put("message", "Data Fetched Successfully");
                response.put("size", bskyDataList.size());
                response.put("data", bskyDataList);
            }else {
                response.put("status", HttpStatus.OK.value());
                response.put("message", "No Data Found With Given Aadhaar/URN Number");
                response.put("size", bskyDataList.size());
                response.put("data", bskyDataList);
            }
        } catch (Exception e) {
            logger.error("Exception Occurred in getBSKYTransactionData of SPDPController" + e.getMessage());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    /**
     * This method is used to get BSKY Transaction Data Daily
     *
     * @Auther : Sambit Kumar Pradhan
     */
    @PostMapping(value = "/getBSKYTransactionDataDaily")
    public ResponseEntity<?> getBSKYTransactionDataDaily(HttpServletRequest httpServletRequest, @RequestBody Map<String, Object> body) {
        logger.info("Inside getBSKYTransactionDataDaily of SPDPController");
        Map<String, Object> response = new LinkedHashMap<>();
        List<Map<String, Object>> bskyDataList;
        try {
            bskyDataList = spdpService.getBskyTransactionDataDaily(httpServletRequest, body);
            if (!bskyDataList.isEmpty())
                response =  CommonClassHelper.createSuccessResponse1(
                        bskyDataList,
                        "Data Fetched Successfully");
            else
                response =  CommonClassHelper.createNoContentResponse("No Data Found");
            return ResponseEntity.ok(response);
            } catch (Exception e) {
            CommonClassHelper.createErrorResponse("Exception Occurred in getBSKYTransactionDataDaily of SPDPController" + e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    /**
     * This method is used to get BSKY Transaction Data Daily
     *
     * @Auther : Sambit Kumar Pradhan
     */
    @PostMapping(value = "getAadhaarRationStatusDetails")
    public ResponseEntity<Map<String, Object>> getAadhaarRationStatusDetails(HttpServletRequest httpServletRequest, @RequestBody Map<String, Object> body){
    	logger.info("Inside getAadharRationStatusDetails of SPDPController");
        Map<String, Object> apiDetails = new LinkedHashMap<>();
        try {
        	apiDetails = spdpService.getAadhaarRationStatusDetails(httpServletRequest, body);
		} catch (Exception e) {
			CommonClassHelper.createErrorResponse("Exception Occurred in getAadharRationStatusDetails of SPDPController" + e.getMessage());
			apiDetails.put("Response", "failed");
			apiDetails.put("Message", e.getMessage());
		}
        return ResponseEntity.ok(apiDetails);
    }
}

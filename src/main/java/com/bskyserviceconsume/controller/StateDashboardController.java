package com.bskyserviceconsume.controller;

import com.bskyserviceconsume.service.StateDashboardService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Project : BSKYServiceConsume
 * @Auther : Sambit Kumar Pradhan
 * @Created On : 10/03/2023 - 3:41 PM
 */
@RestController
@RequestMapping(value = "/api")
public class StateDashboardController {

    @Autowired
    private Logger logger;

    @Autowired
    private StateDashboardService mainService;

    /**
     * This method is used to generate Security Key
     *
     * @Auther : Sambit Kumar Pradhan
     */
    @GetMapping(value = "/generateSecurityKey")
    public ResponseEntity<?> generateSecurityKey(@RequestBody String body) {
        logger.info("Inside Generate Security Key Method of StateDashboardController");
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            response.put("status", HttpStatus.OK.value());
            response.put("secretKey", mainService.generateSecurityKey(new JSONObject(body).getString("merchantName")));
        } catch (Exception e) {
            logger.error("Exception Occurred While Generating Security Key of StateDashboardController", e.getMessage());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", "Error While Generating Security Key");
        }
        return ResponseEntity.ok(response);
    }

    /**
     * This method is used to get Transaction Data
     *
     * @Auther : Sambit Kumar Pradhan
     */
    @ResponseBody
    @GetMapping(value = "/getTransactionData")
    public ResponseEntity<?> getTransactionData(@RequestParam(value = "securityKey") String securityKey,
                                                @RequestParam(value = "fromDate", required = false) Date fromDate,
                                                @RequestParam(value = "toDate", required = false) Date toDate) {
        logger.info("Inside getTransactionData of StateDashboardController");//From Date and To Date in Format mm/dd/yyyy
        try {
            return ResponseEntity.ok(mainService.getTransactionData(securityKey, fromDate, toDate));
        } catch (Exception e) {
            logger.error("Exception Occurred While Fetching Transaction Data of StateDashboardController", e.getMessage());
            return ResponseEntity.ok(e.getMessage());
        }
    }
}

package com.bskyserviceconsume.controller;

import com.bskyserviceconsume.service.EDSSystemService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Project : BSKY Service Consume
 * @Auther : Sambit Kumar Pradhan
 * @Created On : 08/05/2023 - 12:34 PM
 */
@RestController
@RequestMapping(value = "/api")
public class EDSSystemController {

    @Autowired
    private Logger logger;

    @Autowired
    private EDSSystemService edsSystemService;

    /**
     * This method is used to get EDS System Data
     *
     * @Auther : Sambit Kumar Pradhan
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void getEDSSystemData() {
        logger.info("Inside Get EDS System Data Scheduling of EDSSystemController");
        try {
            edsSystemService.getEDSSystemData(null);
        }catch (Exception e) {
            logger.error("Error in Get EDS System Data Scheduling of EDSSystemController" + e.getMessage());
        }
    }

    /**
     * This method is used to get EDS System Data
     *
     * @Auther : Sambit Kumar Pradhan
     */
    @PostMapping(value = "/getEDSSystemData")
    public ResponseEntity<?> getEDSSystemData(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new LinkedHashMap<>();
        logger.info("Inside Get EDS System Data Method of EDSSystemController");
        try {
            response.put("statusCode", HttpStatus.OK.value());
            response.put("status", "Success");
            response.put("message", edsSystemService.getEDSSystemData(request));
        } catch (Exception e) {
            logger.error("Exception Occurred in Get EDS System Data Method of EDSSystemController" + e.getMessage());
            response.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("status", "Error");
            response.put("message", e.getMessage());
        }
        return ResponseEntity.ok().body(response);
    }
}

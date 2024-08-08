package com.bskyserviceconsume.controller;

import com.bskyserviceconsume.service.SchemeStatisticsService;
import com.bskyserviceconsume.util.ClassHelperUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Project : BSKY Service Consume
 * @Author: Sambit Kumar Pradhan
 * @Date: 19-Mar-2024 : 11:26 AM
 */

@RestController
@RequestMapping(value = "/api")
public class SchemeStatisticsController {

    private final Logger logger;

    private final SchemeStatisticsService schemeStatisticsService;

    public SchemeStatisticsController(Logger logger, SchemeStatisticsService schemeStatisticsService) {
        this.logger = logger;
        this.schemeStatisticsService = schemeStatisticsService;
    }

    @PostMapping(value = "/getSchemeStatisticsData")
    public ResponseEntity<Map<String, Object>> getSchemeStatisticsData(@RequestBody Map<String, Object> request) {
        logger.info("Inside getSchemeStatisticsData of  SchemeStatisticsController");
        Map<String, Object> response;
        try {
            response = schemeStatisticsService.getSchemeStatisticsData(request);
            if (!response.isEmpty())
                return ResponseEntity.ok(
                        ClassHelperUtils.createSuccessResponse(
                                response,
                                "Scheme Data Fetched Successfully."
                        )
                );
            else
                return ResponseEntity.ok(
                        ClassHelperUtils.createNoContentResponse("No Data Found!")
                );

        } catch (Exception e) {
            logger.error("Exception Occurred in getSchemeStatisticsData in SchemeStatisticsController : {}", e.getMessage());
            return ResponseEntity.ok(ClassHelperUtils.createErrorResponse("Something went wrong!"));
        }
    }
}

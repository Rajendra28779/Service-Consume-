package com.bskyserviceconsume.controller;


import com.bskyserviceconsume.service.MoSarkarService;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Project : BSKY Service Consume
 * @Auther : Sambit Kumar Pradhan
 * @Created On : 05/04/2023 - 11:08 AM
 */
@Controller
public class MoSarkarController {

    private final Logger logger;
    private final MoSarkarService moSarkarService;

    public MoSarkarController(Logger logger, MoSarkarService moSarkarService) {
        this.logger = logger;
        this.moSarkarService = moSarkarService;
    }

    /**
     * This method is used to get Mo Sarkar Data
     *
     * @Auther : Sambit Kumar Pradhan
     */
    @Scheduled(cron = "0 0 */3 * * *")
    public void moSarkarData() {
        logger.info("Inside MoSarkarData Scheduling of MoSarkarController");
        try {
            moSarkarService.getDataForMoSarkar();
        }catch (Exception e) {
            logger.error("Error in MoSarkarData Scheduling of MoSarkarController : {}", e.getMessage());
        }
    }

    /**
     * This method is used to get Mo Sarkar Data
     *
     * @Auther : Sambit Kumar Pradhan
     */
    @GetMapping(value = "/moSarkarData")
    public void moSarkarData1() {
        logger.info("Inside MoSarkarData of MoSarkarController");
        try {
            moSarkarService.getDataForMoSarkar();
        }catch (Exception e) {
            logger.error("Error in MoSarkarData of MoSarkarController : {}", e.getMessage());
        }
    }

    /**
     * This method is used to get Mo Sarkar Data List
     *
     * @Auther : Sambit Kumar Pradhan
     */
    @GetMapping(value = "/getMoSarkarDataList")
    public ResponseEntity<?> getMoSarkarDataList() {
        logger.info("Inside getMoSarkarDataList of MoSarkarController");
        try {
            return ResponseEntity.ok().body(moSarkarService.getMoSarkarDataList());
        }catch (Exception e) {
            logger.error("Error in getMoSarkarDataList of MoSarkarController : {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * This method is used to get Mo Sarkar Data List
     *
     * @Auther : Sambit Kumar Pradhan
     */
    @GetMapping(value = "getMoSarkarInstitutionId")
    public void getMoSarkarInstitutionId() {
        logger.info("nside getMoSarkarInstitutionId of MoSarkarController");
        try {
            moSarkarService.getMoSarkarInstitutionId();
        } catch (Exception e) {
            logger.error("Exception Occurred in getMoSarkarInstitutionId method of MoSarkarController : {}", e.getMessage());
        }
    }
}

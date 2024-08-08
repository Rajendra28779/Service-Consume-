package com.bskyserviceconsume.util;


import com.bskyserviceconsume.model.APIServiceErrorLog;
import com.bskyserviceconsume.repository.APIServiceErrorLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

/**
 * @Project : BSKY Service Consume
 * @Auther : Sambit Kumar Pradhan
 * @Created On : 08/06/2023 - 6:06 PM
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Project : BSKY Backend
 * @Auther : Sambit Kumar Pradhan
 * @Created On : 24/06/2023 - 11:51 AM
 */

@Component
public class SaveAPIErrorLog {

    private static final Logger logger = LoggerFactory.getLogger(SaveAPIErrorLog.class);

    private final APIServiceErrorLogRepository apiServiceErrorLogRepository;

    @Autowired
    public SaveAPIErrorLog(APIServiceErrorLogRepository apiServiceErrorLogRepository) {
        this.apiServiceErrorLogRepository = apiServiceErrorLogRepository;
    }

    public void saveAPIErrorLog(int apiId, String apiName, Exception e) {
        try {
            APIServiceErrorLog apiServiceErrorLog = new APIServiceErrorLog();
            apiServiceErrorLog.setApiId(apiId);
            apiServiceErrorLog.setApiName(apiName);
            apiServiceErrorLog.setErrorMessage(e.getMessage());
            apiServiceErrorLog.setErrorCode(e.getClass().getName());
            apiServiceErrorLog.setErrorStacktrace(Arrays.toString(e.getStackTrace()));
            apiServiceErrorLog.setCreatedDate(new Date());
            apiServiceErrorLog.setCreatedBy("System");

            APIServiceErrorLog saveErrorLog = apiServiceErrorLogRepository.save(apiServiceErrorLog);
            logger.info("API Error Log saved with ID: {}", saveErrorLog.getId());
        } catch (Exception ex) {
            logger.error("Failed to save API Error Log", ex);
        }
    }
}

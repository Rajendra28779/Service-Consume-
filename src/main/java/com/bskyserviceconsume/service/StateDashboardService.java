package com.bskyserviceconsume.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Project : BSKYServiceConsume
 * @Auther : Sambit Kumar Pradhan
 * @Created On : 10/03/2023 - 4:05 PM
 */
public interface StateDashboardService {
    String generateSecurityKey(String merchantName);
    List<Map<String, Object>> getTransactionData(String securityKey, Date fromDate, Date toDate) throws Exception;

}

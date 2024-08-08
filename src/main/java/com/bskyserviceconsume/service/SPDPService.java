package com.bskyserviceconsume.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @Project : BSKY Service Consume
 * @Auther : Sambit Kumar Pradhan
 * @Created On : 02/06/2023 - 3:46 PM
 */
public interface SPDPService {
    List<Map<String, Object>> getBskyTransactionData(HttpServletRequest httpServletRequest, Map<String, Object> body) throws Exception;
    List<Map<String, Object>> getBskyTransactionDataDaily(HttpServletRequest httpServletRequest, Map<String, Object> body) throws Exception;
	Map<String, Object> getAadhaarRationStatusDetails(HttpServletRequest httpServletRequest, Map<String, Object> body) throws Exception;
}

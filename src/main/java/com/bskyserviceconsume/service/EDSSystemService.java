package com.bskyserviceconsume.service;

import java.util.Map;

/**
 * @Project : BSKY Service Consume
 * @Auther : Sambit Kumar Pradhan
 * @Created On : 08/05/2023 - 12:38 PM
 */
public interface EDSSystemService {
    String getEDSSystemData(Map<String,Object> request) throws Exception;
}

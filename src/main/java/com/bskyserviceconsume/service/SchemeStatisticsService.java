package com.bskyserviceconsume.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface SchemeStatisticsService {
    Map<String, Object> getSchemeStatisticsData(Map<String, Object> request) throws Exception;
}

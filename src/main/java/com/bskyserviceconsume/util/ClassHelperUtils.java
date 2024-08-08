package com.bskyserviceconsume.util;

import org.springframework.http.HttpStatus;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Project : BSKY Card Backend
 * @Author : Sambit Kumar Pradhan
 * @Created On : 28/07/2023 - 12:24 PM
 */
public class ClassHelperUtils {

    private ClassHelperUtils() {}

    public static Map<String, Object> createSuccessResponse(Object data, String message) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        response.put("statusCode", HttpStatus.OK.value());
        response.put("message", message);
        response.put("data", data);
        return response;
    }

    public static Map<String, Object> createNoContentResponse(String message) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "failure");
        response.put("statusCode", HttpStatus.NO_CONTENT.value());
        response.put("message", message);
        return response;
    }

    public static Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "failure");
        response.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("message", message);
        return response;
    }
}

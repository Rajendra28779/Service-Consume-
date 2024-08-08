package com.bskyserviceconsume.util;

import org.springframework.http.HttpStatus;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Project : BSKY Backend
 * @Auther : Sambit Kumar Pradhan
 * @Created On : 23/06/2023 - 11:51 AM
 */
public class CommonClassHelper {

    public static List<String> getModelKeyNames(Class<?> modelClass) {
        List<String> keyNames = new ArrayList<>();

        Field[] fields = modelClass.getDeclaredFields();
        for (Field field : fields) {
            keyNames.add(field.getName());
        }
        return keyNames;
    }

    public static Map<String, Object> createSuccessResponse(Object data, String message) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        response.put("statusCode", HttpStatus.OK.value());
        response.put("message", message);
        response.put("data", data);
        return response;
    }

    public static Map<String, Object> createSuccessResponse1(Object data, String message) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        response.put("statusCode", HttpStatus.OK.value());
        response.put("message", message);
        response.put("size", data != null ? ((List<?>) data).size() : 0);
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
    
    public static Map<String, Object> convertjsonStringToMap(String jsonstring){
    	Map<String, Object> map=new HashMap<>();
	    	Gson gson = new Gson();
	        Type type = new TypeToken<Map<String, Object>>(){}.getType();
	        map = gson.fromJson(jsonstring, type);
    	return map;
    }
}

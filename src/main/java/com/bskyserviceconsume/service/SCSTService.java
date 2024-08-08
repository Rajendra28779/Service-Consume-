/**
 * 
 */
package com.bskyserviceconsume.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 */
public interface SCSTService {

	Map<String, Object> getscstAuthentication(HttpServletRequest request) throws Exception;

	Map<String ,Object> saveSchemeTransaction(HttpServletRequest request) throws Exception;

	Map<String, Object> progress(HttpServletRequest request) throws Exception;

	Map<String ,Object> transactionHistory(String transactionId, HttpServletRequest request) throws Exception;

}

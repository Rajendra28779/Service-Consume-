/**
 * 
 */
package com.bskyserviceconsume.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bskyserviceconsume.service.SCSTService;


/**
 * Rajendra Prasad Sahoo
 * 06-02-2024
 */

@RestController
@RequestMapping(value = "/scstApi")
public class SCSTController {
	
	@Autowired
	private SCSTService scstservice;

	/**
	  * @Auther : RAJENDRA PRASAD SAHOO
	  * @PURPOSE: Get Authentication
	  * @DATE   : 06-02-2024
	  */
	@PostMapping(value = "/getscstlogin")
	public Map<String, Object> getscstlogin(HttpServletRequest request) {
		Map<String, Object> details = new HashMap<>();
		try {
			details=scstservice.getscstAuthentication(request);
			details.put("status", (HttpStatus.OK).value());
			details.put("msg", "API called successful");
		}catch (Exception e) {
			e.printStackTrace();
			details.put("status", (HttpStatus.BAD_REQUEST).value());
			details.put("msg", e.getMessage());
		}
		return details;
	}
	
	/**
	  * @Auther : RAJENDRA PRASAD SAHOO
	  * @PURPOSE: Save Record in tribal Database
	  * @DATE   : 07-02-2024
	  */
	@PostMapping(value = "/saveSchemeTransaction")
	public Map<String, Object> saveSchemeTransaction(HttpServletRequest request) {
		Map<String, Object> details = new HashMap<>();
		try {
			details=scstservice.saveSchemeTransaction(request);
			details.put("status", (HttpStatus.OK).value());
			details.put("msg", "API called successful");
		}catch (Exception e) {
			e.printStackTrace();
			details.put("status", (HttpStatus.BAD_REQUEST).value());
			details.put("msg", e.getMessage());
		}
		return details;
	}
	
	/**
	  * @Auther : RAJENDRA PRASAD SAHOO
	  * @PURPOSE: Check the count the record saved
	  * @DATE   : 07-02-2024
	  */
	@PostMapping(value = "/progress")
	public Map<String, Object> progress(HttpServletRequest request) {
		Map<String, Object> details = new HashMap<>();
		try {
			details=scstservice.progress(request);
			details.put("status", (HttpStatus.OK).value());
			details.put("msg", "API called successful");
		}catch (Exception e) {
			e.printStackTrace();
			details.put("status", "failure");
			details.put("msg", e.getMessage());
		}
		return details;
	}
	
	/**
	  * @Auther : RAJENDRA PRASAD SAHOO
	  * @PURPOSE: Check the count the record saved
	  * @DATE   : 07-02-2024
	  */
	@PostMapping(value = "/transactionHistory")
	public Map<String, Object> transactionHistory(HttpServletRequest request,
			@RequestParam(value = "transactionId") String transactionId) {
		Map<String, Object> details = new HashMap<>();
		try {
			details=scstservice.transactionHistory(transactionId,request);
			details.put("status", (HttpStatus.OK).value());
			details.put("msg", "API called successful");
		}catch (Exception e) {
			e.printStackTrace();
			details.put("status", (HttpStatus.BAD_REQUEST).value());
			details.put("msg", e.getMessage());
		}
		return details;
	}
}

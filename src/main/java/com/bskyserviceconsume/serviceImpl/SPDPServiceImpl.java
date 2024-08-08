package com.bskyserviceconsume.serviceImpl;

import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bskyserviceconsume.model.APIServiceLog;
import com.bskyserviceconsume.repository.APIServiceErrorLogRepository;
import com.bskyserviceconsume.repository.APIServiceLogRepository;
import com.bskyserviceconsume.service.SPDPService;
import com.bskyserviceconsume.util.AadhaarValidation;
import com.bskyserviceconsume.util.SaveAPIErrorLog;
import com.bskyserviceconsume.util.SecurityEncrypt;
import com.google.gson.Gson;

/**
 * @Project : BSKY Service Consume
 * @Auther : Sambit Kumar Pradhan
 * @Created On : 02/06/2023 - 3:46 PM
 */
@Service
public class SPDPServiceImpl implements SPDPService {

	@Autowired
	private Logger logger;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private APIServiceLogRepository apiServiceLogRepository;

	@Autowired
	private APIServiceErrorLogRepository apiServiceErrorLogRepository;

	@Override
	public List<Map<String, Object>> getBskyTransactionData(HttpServletRequest httpServletRequest,
			Map<String, Object> body) throws Exception {
		logger.info("Inside getBskyTransactionData of SPDPServiceImpl");
		List<Map<String, Object>> bskyDataMapList;
		String value;
		int flag;
		try {
			APIServiceLog apiServiceLog = new APIServiceLog();
			apiServiceLog.setApiId(9);
			apiServiceLog.setApiName("SPDP Transaction Data");
			apiServiceLog.setCreatedOn(new Date());
			apiServiceLog.setCreatedBy("System");
			apiServiceLog.setStatusFlag(0);
			apiServiceLog.setApiHitTime(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss")
					.parse(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new Date())));

			if (httpServletRequest.getHeader("Authorization") == null
					|| httpServletRequest.getHeader("Authorization").isEmpty())
				throw new Exception("Authorization is required");

			if (!SecurityEncrypt.checkEncryptedSecurityKey(httpServletRequest.getHeader("Authorization")))
				throw new Exception("Invalid Authorization");

			if (body.isEmpty())
				throw new Exception("Request body is required");
			else {
				if (body.containsKey("rationCardNumber") && body.get("rationCardNumber") != null
						&& !body.get("rationCardNumber").toString().isEmpty()) {
					value = body.get("rationCardNumber").toString();
					flag = 2;
					apiServiceLog.setInputData(
							new JSONObject(body).put("Authorization", httpServletRequest.getHeader("Authorization"))
									.put("rationCardNumber", body.get("rationCardNumber")).toString());
				} else if (body.containsKey("aadhaarNumber") && body.get("aadhaarNumber") != null
						&& !body.get("aadhaarNumber").toString().isEmpty()) {
					if (body.get("aadhaarNumber").toString().length() == 12
							&& AadhaarValidation.validateAadhar(body.get("aadhaarNumber").toString())) {
						value = body.get("aadhaarNumber").toString();
						flag = 1;
						apiServiceLog.setInputData(
								new JSONObject(body).put("Authorization", httpServletRequest.getHeader("Authorization"))
										.put("aadhaarNumber", body.get("aadhaarNumber")).toString());
					} else
						throw new Exception("Invalid Aadhaar Number");
				} else
					throw new Exception("Either Ration Card Number or Aadhaar Number is Required");
			}
			System.out.println("Value : " + value + " Flag : " + flag);

			StoredProcedureQuery storedProcedureQuery = this.entityManager
					.createStoredProcedureQuery("USP_GET_TRANSACTION_DATA_BSKY")
					.registerStoredProcedureParameter("P_FLAG", Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter("P_VALUE", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("P_MSG_OUT", void.class, ParameterMode.REF_CURSOR);

			storedProcedureQuery.setParameter("P_FLAG", flag);
			storedProcedureQuery.setParameter("P_VALUE", value);
			storedProcedureQuery.execute();

			List<?> resultList = storedProcedureQuery.getResultList();

			bskyDataMapList = resultList.stream().map(result -> {
				Object[] objects = (Object[]) result;
				Map<String, Object> bskyDataMap = new LinkedHashMap<>();
				bskyDataMap.put("rationCardNumber", objects[0] != null ? objects[0].toString() : "-NA-");
				bskyDataMap.put("memberId", objects[1] != null ? objects[1].toString() : "-NA-");
				bskyDataMap.put("packageName", objects[2] != null ? objects[2].toString() : "-NA-");
				bskyDataMap.put("packageCost", objects[3] != null ? objects[3].toString() : "-NA-");
				bskyDataMap.put("hospitalName", objects[4] != null ? objects[4].toString() : "-NA-");
				try {
					Date transactionDate = new SimpleDateFormat("ddMMyyyy").parse(objects[5].toString());
					String formattedTransactionDate = new SimpleDateFormat("dd-MM-yyyy").format(transactionDate);
					bskyDataMap.put("dischargeDate", formattedTransactionDate);
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
				return bskyDataMap;
			}).collect(Collectors.toList());

			if (bskyDataMapList.size() > 0) {
				apiServiceLog.setOutputData(new Gson().toJson(bskyDataMapList));
				apiServiceLog.setDataSize(bskyDataMapList.size());
				apiServiceLog.setApiEndTime(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss")
						.parse(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new Date())));
				apiServiceLogRepository.save(apiServiceLog);
			} else {
				apiServiceLog.setOutputData(null);
				apiServiceLog.setDataSize(0);
				apiServiceLog.setApiEndTime(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss")
						.parse(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new Date())));
				apiServiceLogRepository.save(apiServiceLog);
				throw new Exception("No Data Found");
			}
		} catch (Exception e) {
			logger.error("Exception occurred in getBskyTransactionData method of SPDPServiceImpl", e);
			SaveAPIErrorLog saveAPIErrorLog = new SaveAPIErrorLog(apiServiceErrorLogRepository);
			saveAPIErrorLog.saveAPIErrorLog(9, "SPDPTransactionData", e);
			throw new Exception(e.getMessage());
		}
		return bskyDataMapList;
	}

	@Override
	public List<Map<String, Object>> getBskyTransactionDataDaily(HttpServletRequest httpServletRequest,
			Map<String, Object> body) throws Exception {
		List<Map<String, Object>> bskyDataMapList;
		try {
			APIServiceLog apiServiceLog = new APIServiceLog();
			apiServiceLog.setApiId(10);
			apiServiceLog.setApiName("SPDP Transaction Data Daily");
			apiServiceLog.setCreatedOn(new Date());
			apiServiceLog.setCreatedBy("System");
			apiServiceLog.setStatusFlag(0);
			apiServiceLog.setApiHitTime(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss")
					.parse(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new Date())));

			if (httpServletRequest.getHeader("Authorization") == null
					|| httpServletRequest.getHeader("Authorization").isEmpty())
				throw new Exception("Authorization is required");

			if (!SecurityEncrypt.checkEncryptedSecurityKey(httpServletRequest.getHeader("Authorization")))
				throw new Exception("Invalid Authorization");

			if (body.containsKey("date") && body.get("date") != null && !body.get("date").toString().isEmpty()) {
				apiServiceLog.setInputData(
						new JSONObject(body).put("Authorization", httpServletRequest.getHeader("Authorization"))
								.put("date", body.get("date")).toString());

				StoredProcedureQuery storedProcedureQuery = this.entityManager
						.createStoredProcedureQuery("USP_GET_TRANSACTION_DATA_BSKY_DAILY")
						.registerStoredProcedureParameter("P_DATE", Date.class, ParameterMode.IN)
						.registerStoredProcedureParameter("P_MSG_OUT", void.class, ParameterMode.REF_CURSOR);

				storedProcedureQuery.setParameter("P_DATE",
						new SimpleDateFormat("dd-MM-yyyy").parse(body.get("date").toString()));
				storedProcedureQuery.execute();

				List<?> resultList = storedProcedureQuery.getResultList();

				bskyDataMapList = resultList.stream().map(result -> {
					Object[] objects = (Object[]) result;
					Map<String, Object> bskyDataMap = new LinkedHashMap<>();
					bskyDataMap.put("rationCardNumber", objects[0] != null ? objects[0].toString() : "-NA-");
					bskyDataMap.put("aadhaarNumber", objects[1] != null ? objects[1].toString() : "-NA-");
					bskyDataMap.put("memberId", objects[2] != null ? objects[2].toString() : "-NA-");
					bskyDataMap.put("packageName", objects[3] != null ? objects[3].toString() : "-NA-");
					bskyDataMap.put("packageCost", objects[4] != null ? objects[4].toString() : "-NA-");
					bskyDataMap.put("hospitalName", objects[5] != null ? objects[5].toString() : "-NA-");
					try {
						Date transactionDate = new SimpleDateFormat("ddMMyyyy").parse(objects[6].toString());
						String formattedTransactionDate = new SimpleDateFormat("dd-MM-yyyy").format(transactionDate);
						bskyDataMap.put("dischargeDate", formattedTransactionDate);
					} catch (ParseException e) {
						throw new RuntimeException(e);
					}
					return bskyDataMap;
				}).collect(Collectors.toList());

				if (bskyDataMapList.size() > 0) {
					apiServiceLog.setOutputData(new Gson().toJson(bskyDataMapList));
					apiServiceLog.setDataSize(bskyDataMapList.size());
					apiServiceLog.setApiEndTime(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss")
							.parse(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new Date())));
					apiServiceLogRepository.save(apiServiceLog);
				} else {
					apiServiceLog.setOutputData(null);
					apiServiceLog.setDataSize(0);
					apiServiceLog.setApiEndTime(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss")
							.parse(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new Date())));
					apiServiceLogRepository.save(apiServiceLog);
					throw new Exception("No Data Found");
				}

				return bskyDataMapList;
			} else
				throw new Exception("Date is Required");
		} catch (Exception e) {
			logger.error("Exception occurred in getBskyTransactionDataDaily method of SPDPServiceImpl", e);
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public Map<String, Object> getAadhaarRationStatusDetails(HttpServletRequest httpServletRequest,
			Map<String, Object> body) throws Exception {
		Map<String, Object> mapResponse = new LinkedHashMap<>();
		ResultSet resultSet = null;

		try {
			String flag = body.get("flag").toString();

			APIServiceLog apiServiceLog = new APIServiceLog();
			apiServiceLog.setApiId(14);
			apiServiceLog.setApiName("SPDP Aadhaar & Ration Card Status");
			apiServiceLog.setCreatedOn(new Date());
			apiServiceLog.setCreatedBy("System");
			apiServiceLog.setStatusFlag(0);
			apiServiceLog.setApiHitTime(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss")
					.parse(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new Date())));

			if (httpServletRequest.getHeader("Authorization") == null
					|| httpServletRequest.getHeader("Authorization").isEmpty())
				throw new Exception("Authorization is required");

			if (!SecurityEncrypt.checkEncryptedSecurityKey(httpServletRequest.getHeader("Authorization")))
				throw new Exception("Invalid Authorization");

			if (!flag.equalsIgnoreCase("AADHAAR") && !flag.equalsIgnoreCase("RATION"))
				throw new Exception("Invalid Flag");

			if ((body.containsKey("flag") && body.get("flag") != null && !body.get("flag").toString().isEmpty())
					&& body.containsKey("value") && body.get("value") != null
					&& !body.get("value").toString().isEmpty()) {
				apiServiceLog.setInputData(
						new JSONObject(body).put("Authorization", httpServletRequest.getHeader("Authorization"))
								.put("flag", body.get("flag")).put("value", body.get("value")).toString());

				StoredProcedureQuery storedProcedureQuery = this.entityManager
						.createStoredProcedureQuery("USP_GET_AADHAAR_RATION_STATUS_DTLS")
						.registerStoredProcedureParameter("P_FLAG", Integer.class, ParameterMode.IN)
						.registerStoredProcedureParameter("P_INPUT_VALUE", String.class, ParameterMode.IN)
						.registerStoredProcedureParameter("P_IS_AVAILABLE", Integer.class, ParameterMode.OUT)
						.registerStoredProcedureParameter("P_RESPONSE", void.class, ParameterMode.REF_CURSOR);

				if (body.get("flag").toString().equalsIgnoreCase("AADHAAR"))
					storedProcedureQuery.setParameter("P_FLAG", 1);
				else if (body.get("flag").toString().equalsIgnoreCase("RATION"))
					storedProcedureQuery.setParameter("P_FLAG", 2);

				storedProcedureQuery.setParameter("P_INPUT_VALUE", body.get("value").toString());
				storedProcedureQuery.execute();

				Integer count = (Integer) storedProcedureQuery.getOutputParameterValue("P_IS_AVAILABLE");
				resultSet = (ResultSet) storedProcedureQuery.getOutputParameterValue("P_RESPONSE");

				if (count != null && count > 0) {
					mapResponse.put("Response", "success");
					int memberCount = 0;
					mapResponse.put("Status", "Y");
					while (resultSet.next()) {
						++memberCount;
						if (resultSet.getString("RELATION").toUpperCase().equals("HEAD")) {
							mapResponse.put("Value", resultSet.getString("URN"));
							mapResponse.put("Name of the HoF", resultSet.getString("FULLNAME"));
						}
					}
					mapResponse.put("Message", "Data Retrived Successfully");
					mapResponse.put("Status", "Y");
					if (body.get("flag").toString().equalsIgnoreCase("RATION"))
						mapResponse.put("Value", memberCount);
				} else {
					mapResponse.put("Response", "success");
					mapResponse.put("Message", "No Data Found");
					mapResponse.put("Status", "N");
				}

			} else
				throw new Exception("Date is Required");
		} catch (Exception e) {
			logger.error("Exception occurred in getAadhaarRationStatusDetails method of SPDPServiceImpl", e);
			throw new Exception(e.getMessage());
		}
		return mapResponse;
	}
}

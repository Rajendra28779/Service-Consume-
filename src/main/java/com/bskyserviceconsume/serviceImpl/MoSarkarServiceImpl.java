package com.bskyserviceconsume.serviceImpl;

import com.bskyserviceconsume.bean.MoSarkarRequestData;
import com.bskyserviceconsume.bean.Response;
import com.bskyserviceconsume.model.APIServiceLog;
import com.bskyserviceconsume.model.MoSarkarLog;
import com.bskyserviceconsume.repository.APIServiceErrorLogRepository;
import com.bskyserviceconsume.repository.HospitalInfoRepository;
import com.bskyserviceconsume.repository.MoSarkarLogRepository;
import com.bskyserviceconsume.service.MoSarkarService;
import com.bskyserviceconsume.util.DateFormat;
import com.bskyserviceconsume.util.SaveAPIErrorLog;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Auther santanu.barad
 * @Project : BSKY Service Consume
 * @Modified By : Sambit Kumar Pradhan
 */

@Service
public class MoSarkarServiceImpl implements MoSarkarService {

	@Autowired
	private Logger logger;

	@Value("${mosarkar.api.url}")
	private String apiUrl;

	@Value("${mosarkar.username}")
	private String username;

	@Value("${mosarkar.password}")
	private String password;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private MoSarkarLogRepository moSarkarLogRepository;

	@Autowired
	private APIServiceErrorLogRepository apiServiceErrorLogRepository;
	@Autowired
	private HospitalInfoRepository hospitalInfoRepository;

	public Map<String, Object> sendDataToMoSarkar(List<MoSarkarRequestData> listMasterData) throws JSONException, IOException, InterruptedException, ParseException {
		logger.info("Inside sendDataToMoSarkar of MoSarkarServiceImpl");
		List<MoSarkarRequestData> failedData = new ArrayList<>(), successData = new ArrayList<>();
		Map<String, Object> responseMap = new HashMap<>();
		JSONObject jsObject, jsObject1, jsObject2, jsObject3;
		JSONArray moSarkarArray = new JSONArray();
		MoSarkarLog moSarkarLog;
		String response;
		try {
			moSarkarLog = new MoSarkarLog();
			moSarkarLog.setApiId(7);
			moSarkarLog.setApiName("Mo Sarkar");
			moSarkarLog.setStartTime(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new Date())));

			for (MoSarkarRequestData moSarkarRequestData : listMasterData) {
				jsObject2 = new JSONObject();
				jsObject3 = new JSONObject();

				jsObject2.put("district_id", moSarkarRequestData.getDistrict_id());
				jsObject2.put("name", moSarkarRequestData.getName());
				jsObject2.put("mobile", moSarkarRequestData.getMobile());
				jsObject2.put("age", Long.parseLong(moSarkarRequestData.getAge()));
				jsObject2.put("gender", moSarkarRequestData.getGender());
				jsObject2.put("department_institution_id", Long.parseLong(moSarkarRequestData.getDepartment_institution_id()));
				jsObject2.put("registration_date", moSarkarRequestData.getRegistration_date());
				jsObject2.put("registration_no", moSarkarRequestData.getRegistration_no());
				jsObject2.put("claim_amount", moSarkarRequestData.getClaim_amount());
				jsObject3.put("hospital_info", moSarkarRequestData.getHospitalInfo());
				jsObject3.put("hospital_district_name", moSarkarRequestData.getHospitalDistrictName());
				jsObject3.put("purpose", moSarkarRequestData.getPurpose());
				jsObject3.put("claim_id", moSarkarRequestData.getClaimNo());
				jsObject2.put("other_info", jsObject3);
				moSarkarArray.put(jsObject2);
			}

			//Sambit
			jsObject1 = new JSONObject();

			logger.info("Before Calling HttpClient");

			HttpClient client = HttpClient.newHttpClient();
			jsObject1.put("method", "OutboundDataSubmit");
			jsObject1.put("dept_code", "HFW@12");
			jsObject1.put("service_code", "HFW@12@BSKY");
			jsObject1.put("data", moSarkarArray);

			logger.info("Json Object : " + jsObject1);
			moSarkarLog.setInputData(jsObject1.toString());
			moSarkarLog.setDataSize(moSarkarArray.length());

			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(apiUrl))
					.header("Authorization", getBasicAuthenticationHeader(username, password))
					.header("Content-Type", "application/json")
					.header("Cookie", "PHPSESSID=9mcb9lq3t9nbcv047i2cb1et91")
					.POST(HttpRequest.BodyPublishers.ofString(String.valueOf(jsObject1))).build();

			logger.info("Request : " + request.toString());

			response = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
			moSarkarLog.setOutputData(response);

			logger.info("After Calling HttpClient Response  Received : " + response);

			jsObject = new JSONObject(response);
			if (jsObject.has("result")) {
				JSONObject statusCode1 = jsObject.getJSONObject("result");
				if (statusCode1.getLong("status") == 205 || statusCode1.getLong("status") == 409 || statusCode1.getLong("status") == 401){
					failedData.addAll(listMasterData);
					responseMap.put("actionValue", 0);
					responseMap.put("list", failedData);
				}
			}else if (jsObject.has("response_code")) {
				long resCode = jsObject.getLong("response_code");
				if (resCode == 405) {
					JSONArray invalidDataArr = jsObject.getJSONArray("invalidvalidDataArr");
					for (int i = 0; i < invalidDataArr.length(); i++) {
						String invoiceNo = invalidDataArr.getString(i);
						listMasterData.stream()
								.filter(moSarkarData -> moSarkarData.getRegistration_no().equals(invoiceNo))
								.forEach(failedData::add);
					}
				}else if (resCode == 200 && jsObject.has("invalidvalidDataArr")) {
					JSONArray invalidDataArr = jsObject.getJSONArray("invalidvalidDataArr");
					if (invalidDataArr.length() > 0) {
						for (int i = 0; i < invalidDataArr.length(); i++) {
							String invoiceNo = invalidDataArr.getString(i);
							listMasterData.stream()
									.filter(moSarkarData -> moSarkarData.getRegistration_no().equals(invoiceNo))
									.forEach(failedData::add);
						}
					}
				} else if (resCode != 200 && jsObject.has("invalidvalidDataArr")) {
					JSONArray invalidDataArr = jsObject.getJSONArray("invalidvalidDataArr");
					for (int i = 0; i < invalidDataArr.length(); i++) {
						String invoiceNo = invalidDataArr.getString(i);
						listMasterData.stream()
								.filter(moSarkarData -> moSarkarData.getRegistration_no().equals(invoiceNo))
								.forEach(failedData::add);
					}
				}
				logger.info("Mo Sarkar Failed Data : " + failedData.size());

				successData = listMasterData.stream()
						.filter(moSarkarData -> !failedData.contains(moSarkarData)).collect(Collectors.toList());

				List<Map<String, Object>> successDataList = successData.stream()
						.map(MoSarkarRequestData::toMap).collect(Collectors.toList());

				List<Map<String, Object>> failedDataList = failedData.stream()
						.map(MoSarkarRequestData::toMap).collect(Collectors.toList());

				moSarkarLog.setSuccessData(new Gson().toJson(successDataList));
				moSarkarLog.setFailedData(new Gson().toJson(failedDataList));
				moSarkarLog.setFailedDataSize(failedDataList.size());
				moSarkarLog.setSuccessDataSize(successDataList.size());
				moSarkarLog.setCreatedOn(new Date());
				moSarkarLog.setCreatedBy("System");
				moSarkarLog.setEndTime(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new Date())));
				moSarkarLog.setStatusFlag(0);

				moSarkarLogRepository.save(moSarkarLog);

				responseMap.put("actionValue", 1);
				responseMap.put("list", failedData);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Exception in sendDataToMoSarkar() : " + e.getMessage());
			failedData.addAll(listMasterData);
			responseMap.put("actionValue", 0);
			responseMap.put("list", failedData);
			saveFailedMoSarkarData(responseMap);
			throw e;
		}
		logger.info("Response Map : " + responseMap);
		return responseMap;
	}

	private static String getBasicAuthenticationHeader(String username, String password) {
		String valueToEncode = username + ":" + password;
		return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
	}

	@Override
	@Transactional
	public Response getDataForMoSarkar() throws JSONException, IOException, InterruptedException, ParseException {
		List<MoSarkarRequestData> listMasterData = new ArrayList<>();
		Map<String, Object> responseMoSarkarData = null;
		Response response = null;
		try {
			StoredProcedureQuery storedProcedureQuery = this.entityManager
					.createStoredProcedureQuery("USP_CLAIM_MOSARKAR_DATA")
					.registerStoredProcedureParameter("P_MSG_OUT", void.class, ParameterMode.REF_CURSOR);

			logger.info("Before Calling Procedure USP_CLAIM_MOSARKAR_DATA");
			storedProcedureQuery.execute();

			List<?> moSarkarObjectsList = storedProcedureQuery.getResultList();

			if (moSarkarObjectsList != null && moSarkarObjectsList.size() > 0) {
				moSarkarObjectsList.forEach(moSarkarObject -> {
					Object[] moSarkarObjectArr = (Object[]) moSarkarObject;
					MoSarkarRequestData moSarkarRequestData = new MoSarkarRequestData();
					moSarkarRequestData.setDistrict_id(moSarkarObjectArr[1] != null ? moSarkarObjectArr[1].toString() : "NA");
					moSarkarRequestData.setName(moSarkarObjectArr[4] != null ? moSarkarObjectArr[4].toString() : "NA");
					moSarkarRequestData.setMobile(moSarkarObjectArr[5] != null ? moSarkarObjectArr[5].toString() : "NA");
					moSarkarRequestData.setAge(moSarkarObjectArr[6] != null ? moSarkarObjectArr[6].toString() : "NA");
					moSarkarRequestData.setGender(moSarkarObjectArr[7] != null ? Long.parseLong(moSarkarObjectArr[7].toString()) : 0);
					moSarkarRequestData.setDepartment_institution_id(moSarkarObjectArr[2] != null ? moSarkarObjectArr[2].toString() : "NA");
					moSarkarRequestData.setRegistration_date(moSarkarObjectArr[10] != null ? DateFormat.FormatToDateString(moSarkarObjectArr[10].toString()) : "NA");
					moSarkarRequestData.setRegistration_no(moSarkarObjectArr[9] != null ? moSarkarObjectArr[9].toString() : "NA");
					moSarkarRequestData.setClaim_amount(moSarkarObjectArr[8] != null ? moSarkarObjectArr[8].toString() : "NA");
					moSarkarRequestData.setHospitalInfo(moSarkarObjectArr[3] != null ? moSarkarObjectArr[3].toString() : "NA");
					moSarkarRequestData.setPurpose(moSarkarObjectArr[11] != null ? moSarkarObjectArr[11].toString() : "NA");
					moSarkarRequestData.setClaimNo(moSarkarObjectArr[12] != null ? moSarkarObjectArr[12].toString() : "NA");
					moSarkarRequestData.setClaimId(moSarkarObjectArr[13] != null ? Long.parseLong(moSarkarObjectArr[13].toString()) : 0);
					moSarkarRequestData.setTransactionDetailId(moSarkarObjectArr[15] != null ? Long.parseLong(moSarkarObjectArr[15].toString()) : 0);
					moSarkarRequestData.setHospitalDistrictName(moSarkarObjectArr[17] != null ? moSarkarObjectArr[17].toString() : "NA");
					listMasterData.add(moSarkarRequestData);
				});
			}
			logger.info("After Calling Procedure USP_CLAIM_MOSARKAR_DATA, Size of List : " + listMasterData.size());

			responseMoSarkarData = sendDataToMoSarkar(listMasterData);
			response = saveFailedMoSarkarData(responseMoSarkarData);
		} catch (Exception e) {
			logger.info("Exception in getDataForMoSarkar() : " + e.getMessage());
			SaveAPIErrorLog saveAPIErrorLog = new SaveAPIErrorLog(apiServiceErrorLogRepository);
			saveAPIErrorLog.saveAPIErrorLog(7, "MoSarkar", e);
			e.printStackTrace();
			throw e;
		}
		System.out.println("Response : " + response);
		return response;
	}

	public int saveFailedMoSarkarData(String transactionDetailsIdList, int actionValue) {
		String returnValue;
		try {
			StoredProcedureQuery storedProcedureQuery = this.entityManager
					.createStoredProcedureQuery("USP_CLAIM_MOSARKAR_DATA_FAILED")
					.registerStoredProcedureParameter("P_TRANSACTIONDETAILSID_LIST", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("P_ACTION_VALUE", Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter("P_MSG_OUT", String.class, ParameterMode.OUT);

			storedProcedureQuery.setParameter("P_TRANSACTIONDETAILSID_LIST", transactionDetailsIdList);
			storedProcedureQuery.setParameter("P_ACTION_VALUE", actionValue);

			storedProcedureQuery.execute();
			returnValue = (String) storedProcedureQuery.getOutputParameterValue("P_MSG_OUT");
		} catch (Exception e) {
			logger.info("Exception in saveFailedMoSarkarData() : " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		return Integer.parseInt(returnValue);
	}

	public Response saveFailedMoSarkarData(Map<String, Object> responseMap) {
		Response response = new Response();
		int success = 0, actionValue;
		try {
			if (responseMap != null && !responseMap.isEmpty()) {
				actionValue = (int) responseMap.get("actionValue");
				List<MoSarkarRequestData> resMoSarkarData = (List<MoSarkarRequestData>) responseMap.get("list");
				if (!resMoSarkarData.isEmpty()) {
					int count = 0;
					StringBuilder stringBuffer = new StringBuilder();
					for (MoSarkarRequestData element : resMoSarkarData) {
						stringBuffer.append(element.getTransactionDetailId().toString()).append(",");
						count++;
						if (count == 100) {
							success = saveFailedMoSarkarData(stringBuffer.substring(0, stringBuffer.length() - 1), actionValue);
							stringBuffer = new StringBuilder();
							count = 0;
						}
					}
					if (count > 0)
						success = saveFailedMoSarkarData(stringBuffer.substring(0, stringBuffer.length() - 1), actionValue);
					if (success == 1) {
						response.setStatus("Success");
						response.setMessage("Data Updated Successfully");
					} else {
						response.setStatus("Failed");
						response.setMessage("Something Went Wrong");
					}
				}
			}
			logger.info("Process Completed");
		} catch (Exception e) {
			logger.info("Exception in saveFailedMoSarkarData() : " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		return response;
	}

	@Override
	public List<MoSarkarRequestData> getMoSarkarDataList() {
		List<MoSarkarRequestData> listMasterData = new ArrayList<>();
		try {
			StoredProcedureQuery storedProcedureQuery = this.entityManager
					.createStoredProcedureQuery("USP_CLAIM_MOSARKAR_DATA")
					.registerStoredProcedureParameter("P_MSG_OUT", void.class, ParameterMode.REF_CURSOR);

			logger.info("Before Calling Procedure USP_CLAIM_MOSARKAR_DATA");
			storedProcedureQuery.execute();

			List<?> moSarkarObjectsList = storedProcedureQuery.getResultList();

			if (moSarkarObjectsList != null && moSarkarObjectsList.size() > 0) {
				moSarkarObjectsList.forEach(moSarkarObject -> {
					Object[] moSarkarObjectArr = (Object[]) moSarkarObject;
					MoSarkarRequestData moSarkarRequestData = new MoSarkarRequestData();
					moSarkarRequestData.setDistrict_id(moSarkarObjectArr[1] != null ? moSarkarObjectArr[1].toString() : "NA");
					moSarkarRequestData.setName(moSarkarObjectArr[4] != null ? moSarkarObjectArr[4].toString() : "NA");
					moSarkarRequestData.setMobile(moSarkarObjectArr[5] != null ? moSarkarObjectArr[5].toString() : "NA");
					moSarkarRequestData.setAge(moSarkarObjectArr[6] != null ? moSarkarObjectArr[6].toString() : "NA");
					moSarkarRequestData.setGender(moSarkarObjectArr[7] != null ? Long.parseLong(moSarkarObjectArr[7].toString()) : 0);
					moSarkarRequestData.setDepartment_institution_id(moSarkarObjectArr[2] != null ? moSarkarObjectArr[2].toString() : "NA");
					moSarkarRequestData.setRegistration_date(moSarkarObjectArr[10] != null ? DateFormat.FormatToDateString(moSarkarObjectArr[10].toString()) : "NA");
					moSarkarRequestData.setRegistration_no(moSarkarObjectArr[9] != null ? moSarkarObjectArr[9].toString() : "NA");
					moSarkarRequestData.setClaim_amount(moSarkarObjectArr[8] != null ? moSarkarObjectArr[8].toString() : "NA");
					moSarkarRequestData.setHospitalInfo(moSarkarObjectArr[3] != null ? moSarkarObjectArr[3].toString() : "NA");
					moSarkarRequestData.setPurpose(moSarkarObjectArr[11] != null ? moSarkarObjectArr[11].toString() : "NA");
					moSarkarRequestData.setClaimNo(moSarkarObjectArr[12] != null ? moSarkarObjectArr[12].toString() : "NA");
					moSarkarRequestData.setClaimId(moSarkarObjectArr[13] != null ? Long.parseLong(moSarkarObjectArr[13].toString()) : 0);
					moSarkarRequestData.setTransactionDetailId(moSarkarObjectArr[15] != null ? Long.parseLong(moSarkarObjectArr[15].toString()) : 0);
					moSarkarRequestData.setHospitalDistrictName(moSarkarObjectArr[17] != null ? moSarkarObjectArr[17].toString() : "NA");
					listMasterData.add(moSarkarRequestData);
				});
			}
			logger.info("After Calling Procedure USP_CLAIM_MOSARKAR_DATA, Size of List : " + listMasterData.size());
		} catch (Exception e) {
			logger.info("Exception in getDataForMoSarkar() : " + e.getMessage());
			SaveAPIErrorLog saveAPIErrorLog = new SaveAPIErrorLog(apiServiceErrorLogRepository);
			saveAPIErrorLog.saveAPIErrorLog(7, "MoSarkar", e);
			e.printStackTrace();
			throw e;
		}
		return listMasterData;
	}

	@Override
	public void getMoSarkarInstitutionId() throws ParseException {
		try {
			APIServiceLog apiServiceLog = new APIServiceLog();
			apiServiceLog.setApiId(13);
			apiServiceLog.setApiName("Mo Sarkar Institution Id");
			apiServiceLog.setCreatedOn(new Date());
			apiServiceLog.setCreatedBy("System");
			apiServiceLog.setStatusFlag(0);
			apiServiceLog.setApiHitTime(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new Date())));

			List<Map<String, Object>> requestMapList = new ArrayList<>();

			List<Object[]> getHospitalListHavingNoInstitutionId = hospitalInfoRepository.getHospitalListHavingNoInstitutionId();
			if (!getHospitalListHavingNoInstitutionId.isEmpty()) {
				getHospitalListHavingNoInstitutionId.forEach(hospitalArray -> {
					Map<String, Object> requestMap = new LinkedHashMap<>();
					requestMap.put("hospitalCode", hospitalArray[0] != null ? hospitalArray[0].toString() : "-NA-");
					requestMap.put("hospitalName", hospitalArray[1] != null ? hospitalArray[1].toString() : "-NA-");
					requestMap.put("stateCode", hospitalArray[2] != null ? hospitalArray[2].toString() : "-NA-");
					requestMap.put("districtCode", hospitalArray[3] != null ? hospitalArray[3].toString() : "-NA-");
					requestMap.put("districtName", hospitalArray[4] != null ? hospitalArray[4].toString() : "-NA-");

					requestMapList.add(requestMap);
				});
			}

			if (!requestMapList.isEmpty()) {
				apiServiceLog.setInputData(new Gson().toJson(requestMapList));
//				Login For Sending to Mo Sarkar Server after they Provide
			}
		} catch (Exception e) {
			logger.error("Exception Occurred in getMoSarkarInstitutionId method of MoSarkarServiceImpl :" + e);
			throw e;
		}
	}
}

package com.bskyserviceconsume.serviceImpl;

import com.bskyserviceconsume.model.EdsData;
import com.bskyserviceconsume.model.EdsDataLog;
import com.bskyserviceconsume.model.EdsDataReport;
import com.bskyserviceconsume.repository.APIServiceErrorLogRepository;
import com.bskyserviceconsume.repository.EDSDataReportRepository;
import com.bskyserviceconsume.repository.EdsDataLogRepository;
import com.bskyserviceconsume.repository.EdsDataRepository;
import com.bskyserviceconsume.service.EDSSystemService;
import com.bskyserviceconsume.util.PreviousDate;
import com.bskyserviceconsume.util.SaveAPIErrorLog;
import com.google.gson.*;
import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Project : BSKY Service Consume
 * @Auther : Sambit Kumar Pradhan
 * @Created On : 08/05/2023 - 12:39 PM
 */
@Service
public class EDSSystemServiceImpl implements EDSSystemService {

    private final Logger logger;

    @Value("${eds.api.url}")
    private String edsSystemURL;

    @Value("${eds.action.key.UAT}")
    private String apiAccessKey;

    private final EdsDataRepository edsDataRepository;

    private final EDSDataReportRepository edsDataReportRepository;
    private final EdsDataLogRepository edsDataLogRepository;

    private final APIServiceErrorLogRepository apiServiceErrorLogRepository;

    public EDSSystemServiceImpl(Logger logger, EdsDataRepository edsDataRepository, EDSDataReportRepository edsDataReportRepository,
                                APIServiceErrorLogRepository apiServiceErrorLogRepository, EdsDataLogRepository edsDataLogRepository) {
        this.logger = logger;
        this.edsDataRepository = edsDataRepository;
        this.edsDataLogRepository = edsDataLogRepository;
        this.edsDataReportRepository = edsDataReportRepository;
        this.apiServiceErrorLogRepository = apiServiceErrorLogRepository;
    }

    @Override
    public String getEDSSystemData(Map<String, Object> request) throws Exception {
        logger.info("Inside Get EDS System Data Method of EDSSystemServiceImpl");

        try {
            EdsDataReport edsDataReport = new EdsDataReport();
            edsDataReport.setApiId(8);
            edsDataReport.setApiName("EDS / 104 Service");
            edsDataReport.setStartTime(new Date());
            edsDataReport.setCreatedDate(new Date());
            edsDataReport.setCreatedBy("System");

            String fromDate;
            String toDate;
            String apiURL = edsSystemURL;

            if (request == null) {
                fromDate = new SimpleDateFormat("yyyy-MM-dd").format(PreviousDate.getPreviousDate()) + " 00:00:00";
                toDate = new SimpleDateFormat("yyyy-MM-dd").format(PreviousDate.getPreviousDate()) + " 23:59:59";
            } else {
                fromDate = (String) request.get("fromDate");
                toDate = (String) request.get("toDate");
            }

            logger.info("From Date: {} To Date: {}", fromDate, toDate);

            JsonObject requestBody = new JsonObject();
            requestBody.addProperty("apiAccessKey", apiAccessKey);
            requestBody.addProperty("FromDate", fromDate);
            requestBody.addProperty("ToDate", toDate);

            logger.info("Request Body: {}", requestBody);
            edsDataReport.setInputData(requestBody.toString());

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(apiURL))
                    .header("Content-Type", "application/JSON")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String response = httpResponse.body();
            logger.info("Response Body: {}", response);

            JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
            logger.info("Response Body as JSON Object: {}", jsonObject);

            JsonArray bskyDataArray = jsonObject.getAsJsonArray("BSKYData");
            edsDataReport.setBskyData(bskyDataArray.toString());
            edsDataReport.setRecordsFetch((long) bskyDataArray.size());

            AtomicInteger edsInsertCount = new AtomicInteger();
            AtomicInteger edsUpdateCount = new AtomicInteger();
            AtomicInteger failedCount = new AtomicInteger();

            List<EdsData> edsDataListToSave = new ArrayList<>();
            List<EdsData> edsIdListsInserted = new ArrayList<>();
            List<EdsData> edsIdListsUpdated = new ArrayList<>();
            List<EdsData> edsIdListsFailed = new ArrayList<>();
            List<EdsDataLog> edsDataLogList = new ArrayList<>();

            bskyDataArray.forEach(bskyDataElement -> {
                EdsData edsData = extractEdsData(bskyDataElement);
                List<EdsData> edsDataList = edsDataRepository.findBSKYDataById(edsData.getId());

                if (edsDataList.isEmpty()) {
                    edsIdListsInserted.add(edsData);
                    edsData.setCreatedOn(new Date());
                    edsData.setCreatedBy("System");
                    edsData.setStatusFlag(0L);
                    edsInsertCount.incrementAndGet();
                } else if (edsDataList.size() == 1) {
                    EdsData existingEdsData = edsDataList.get(0);

                    EdsDataLog edsDataLog = new EdsDataLog();
                    BeanUtils.copyProperties(existingEdsData, edsDataLog);
                    edsDataLog.setEdsId(existingEdsData.getEdsId());
                    edsDataLog.setEds(existingEdsData);
                    edsDataLogList.add(edsDataLog);

                    edsIdListsUpdated.add(edsData);
                    edsData.setEdsId(existingEdsData.getEdsId());
                    edsData.setCreatedOn(existingEdsData.getCreatedOn());
                    edsData.setCreatedBy(existingEdsData.getCreatedBy());
                    edsData.setUpdatedOn(new Date());
                    edsData.setUpdatedBy("System");
                    edsUpdateCount.incrementAndGet();
                } else {
                    edsIdListsFailed.add(edsData);
                    failedCount.incrementAndGet();
                }
                edsDataListToSave.add(edsData);
            });

            edsDataReport.setBskyInsertedData(new Gson().toJson(edsIdListsInserted));
            edsDataReport.setBskyUpdatedData(new Gson().toJson(edsIdListsUpdated));
            edsDataReport.setBskyFailedData(new Gson().toJson(edsIdListsFailed));
            edsDataReport.setRecordsInserted((long) edsInsertCount.get());
            edsDataReport.setRecordsUpdated((long) edsUpdateCount.get());
            edsDataReport.setRecordsFailed((long) failedCount.get());
            edsDataReport.setEndTime(new Date());
            edsDataReport.setStatusFlag(0L);

            logger.info("EDS Data Report: {}", edsDataReport);

            edsDataRepository.saveAll(edsDataListToSave);
            edsDataLogRepository.saveAll(edsDataLogList);
            edsDataReportRepository.save(edsDataReport);

            return "EDS Data Saved Successfully";
        } catch (Exception e) {
            logger.error("Exception Occurred in Get EDS System Data Method of EDSSystemServiceImpl : {}", e.getMessage());
            SaveAPIErrorLog saveAPIErrorLog = new SaveAPIErrorLog(apiServiceErrorLogRepository);
            saveAPIErrorLog.saveAPIErrorLog(8, "EDSSystemData", e);
            throw new Exception(e);
        }
    }

    private EdsData extractEdsData(JsonElement bskyData) {
        JsonObject bskyDataObject = bskyData.getAsJsonObject();
        EdsData edsData = new EdsData();
        edsData.setId(bskyDataObject.get("Id").getAsLong());
        edsData.setJobNo(bskyDataObject.get("JobNo").getAsString());
        edsData.setCallType(bskyDataObject.get("CallType").getAsString());
        edsData.setDistrict(bskyDataObject.get("District").getAsString());
        edsData.setCallTaker(bskyDataObject.get("CallTaker").getAsString());
        edsData.setCallerNo(bskyDataObject.get("CallerNo").getAsString());
        try {
            edsData.setCallDatetime(
                    bskyDataObject.has("CallDateTime")
                            ? new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(bskyDataObject.get("CallDateTime").getAsString())
                            : null
            );
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        edsData.setTypeOfJob(bskyDataObject.get("TypeofJob").getAsString());
        edsData.setIsVictimCaller(bskyDataObject.get("IsVictimCaller").getAsString());
        edsData.setVictimName(bskyDataObject.get("VictimName").getAsString());
        edsData.setFacilityName(bskyDataObject.get("FacilityName").getAsString());
        edsData.setVillage(bskyDataObject.get("Village").getAsString());
        edsData.setPatientAge(bskyDataObject.get("PatientAge").getAsLong());
        edsData.setAgeUnit(bskyDataObject.get("AgeUnit").getAsString());
        edsData.setNatureOfCall(bskyDataObject.get("NatureOfCall").getAsString());
        edsData.setState(bskyDataObject.get("State").getAsString());
        edsData.setHospitalName(bskyDataObject.get("Hospital Name").getAsString());
        edsData.setEdsStatus(
                bskyDataObject.get("CallType").getAsString().equalsIgnoreCase("BSKY Inbound-Enquiry")
                        ? 1L
                        : bskyDataObject.get("CallType").getAsString().equalsIgnoreCase("BSKY_COMPLAIN")
                        ? 2L
                        : 3L
        );
        return edsData;
    }
}
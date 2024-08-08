package com.bskyserviceconsume.serviceImpl;

import com.bskyserviceconsume.model.APIServiceLog;
import com.bskyserviceconsume.repository.APIServiceErrorLogRepository;
import com.bskyserviceconsume.repository.APIServiceLogRepository;
import com.bskyserviceconsume.service.StateDashboardService;
import com.bskyserviceconsume.util.SaveAPIErrorLog;
import com.bskyserviceconsume.util.SecurityEncrypt;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Project : BSKYServiceConsume
 * @Auther : Sambit Kumar Pradhan
 * @Created On : 10/03/2023 - 4:05 PM
 */
@Service
public class StateDashboardServiceImpl implements StateDashboardService {

    @Autowired
    private Logger logger;

    @Value("${BSKY.private.merchantName}")
    private String username;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private APIServiceLogRepository apiServiceLogRepository;

    @Autowired
    private APIServiceErrorLogRepository apiServiceErrorLogRepository;

    @Override
    public String generateSecurityKey(String merchantName) {
        logger.info("Inside generateSecurityKey method of StateDashboardServiceImpl.");
        if (merchantName != null && !merchantName.isEmpty())
            return SecurityEncrypt.encryptSecurityKey(merchantName.trim());
        else
            return SecurityEncrypt.encryptSecurityKey(username);
    }

    @Override
    public List<Map<String, Object>> getTransactionData(String securityKey, Date fromDate, Date toDate) throws Exception {
        logger.info("Inside getTransactionData method of StateDashboardServiceImpl.");
        List<Map<String, Object>> responseList = new ArrayList<>();
        StoredProcedureQuery storedProcedureQuery;
        try {
            APIServiceLog apiServiceLog = new APIServiceLog();
            apiServiceLog.setApiId(1);
            apiServiceLog.setStatusFlag(0);
            apiServiceLog.setCreatedBy("System");
            apiServiceLog.setCreatedOn(new Date());
            apiServiceLog.setApiName("State Dashboard Transaction Data");
            apiServiceLog.setInputData(new JSONObject()
                    .put("Authorization", securityKey)
                    .put("fromDate", new SimpleDateFormat("dd-MMM-yyyy").format(fromDate))
                    .put("toDate", new SimpleDateFormat("dd-MMM-yyyy").format(toDate)).toString()
            );
            apiServiceLog.setApiHitTime(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new Date())));

            if (SecurityEncrypt.checkEncryptedSecurityKey(securityKey)) {
                storedProcedureQuery = this.entityManager.createStoredProcedureQuery("USP_GET_TRANSACTION_DATA")
                        .registerStoredProcedureParameter("P_FROM_DATE", Date.class, ParameterMode.IN)
                        .registerStoredProcedureParameter("P_TO_DATE", Date.class, ParameterMode.IN)
                        .registerStoredProcedureParameter("P_MSG_OUT", void.class, ParameterMode.REF_CURSOR);

                storedProcedureQuery.setParameter("P_FROM_DATE", new SimpleDateFormat("dd-MMM-yyyy").parse(new SimpleDateFormat("dd-MMM-yyyy").format(fromDate)));
                storedProcedureQuery.setParameter("P_TO_DATE", new SimpleDateFormat("dd-MMM-yyyy").parse(new SimpleDateFormat("dd-MMM-yyyy").format(toDate)));
                storedProcedureQuery.execute();

//                System.out.println("From Date : " + new SimpleDateFormat("dd-MMM-yyyy").parse(new SimpleDateFormat("dd-MMM-yyyy").format(fromDate)));

                List<?> resultList = storedProcedureQuery.getResultList();
                if (resultList != null && !resultList.isEmpty()) {
                    for (Object o : resultList) {
                        Map<String, Object> response = new LinkedHashMap<>();
                        response.put("TransactionID", ((Object[]) o)[0] == null ? "-NA-" : ((Object[]) o)[0]);
                        response.put("TerminalID", ((Object[]) o)[1] == null ? "-NA-" : ((Object[]) o)[1].toString());
                        response.put("BatchNo", ((Object[]) o)[2] == null ? "-NA-" : ((Object[]) o)[2].toString());
                        response.put("InvoiceNo", ((Object[]) o)[3] == null ? "-NA-" : ((Object[]) o)[3].toString());
                        response.put("URN", ((Object[]) o)[4] == null ? "-NA-" : ((Object[]) o)[4].toString());
                        response.put("ChipSerialNO", ((Object[]) o)[5] == null ? "-NA-" : ((Object[]) o)[5].toString());
                        response.put("CardType", ((Object[]) o)[6] == null ? "-NA-" : ((Object[]) o)[6].toString());
                        response.put("FPOverrideCode", ((Object[]) o)[7] == null ? "-NA-" : ((Object[]) o)[7].toString());
                        response.put("PatientPhoneNo", ((Object[]) o)[8] == null ? "-NA-" : ((Object[]) o)[8].toString());
                        response.put("StateCode", ((Object[]) o)[9] == null ? "-NA-" : ((Object[]) o)[9].toString());
                        response.put("DistrictCode", ((Object[]) o)[10] == null ? "-NA-" : ((Object[]) o)[10].toString());
                        response.put("MemberID", ((Object[]) o)[11] == null ? "-NA-" : ((Object[]) o)[11].toString());
                        response.put("FPVerifierID", ((Object[]) o)[12] == null ? "-NA-" : ((Object[]) o)[12].toString());
                        response.put("HospitalCode", ((Object[]) o)[13] == null ? "-NA-" : ((Object[]) o)[13].toString());
                        response.put("HospitalAuthorityID", ((Object[]) o)[14] == null ? "-NA-" : ((Object[]) o)[14].toString());
                        response.put("TransactionCode", ((Object[]) o)[15] == null ? "-NA-" : ((Object[]) o)[15].toString());
                        response.put("TransactionType", ((Object[]) o)[16] == null ? "-NA-" : ((Object[]) o)[16].toString());
                        response.put("TransactionDate", ((Object[]) o)[17] == null ? "-NA-" : ((Object[]) o)[17].toString());
                        response.put("TransactionTiMe", ((Object[]) o)[18] == null ? "-NA-" : ((Object[]) o)[18].toString());
                        response.put("PackageCode", ((Object[]) o)[19] == null ? "-NA-" : ((Object[]) o)[19].toString());
                        response.put("TotalAmountClaimed", ((Object[]) o)[20] == null ? "-NA-" : ((Object[]) o)[20].toString());
                        response.put("TotalAmountBlocked", ((Object[]) o)[21] == null ? "-NA-" : ((Object[]) o)[21].toString());
                        response.put("InsufficientFund", ((Object[]) o)[22] == null ? "-NA-" : ((Object[]) o)[22].toString());
                        response.put("InsufficientAmt", ((Object[]) o)[23] == null ? "-NA-" : ((Object[]) o)[23].toString());
                        response.put("noOfDays", ((Object[]) o)[24] == null ? "-NA-" : ((Object[]) o)[24].toString());
                        response.put("DateOFAdmission", ((Object[]) o)[25] == null ? "-NA-" : ((Object[]) o)[25].toString());
                        response.put("DateOfDischarge", ((Object[]) o)[26] == null ? "-NA-" : ((Object[]) o)[26].toString());
                        response.put("Mortality", ((Object[]) o)[27] == null ? "-NA-" : ((Object[]) o)[27].toString());
                        response.put("TransactionDescription", ((Object[]) o)[28] == null ? "-NA-" : ((Object[]) o)[28].toString());
                        response.put("AmountClaimed", ((Object[]) o)[29] == null ? "-NA-" : ((Object[]) o)[29].toString());
                        response.put("TravelAmountClaimed", ((Object[]) o)[30] == null ? "-NA-" : ((Object[]) o)[30].toString());
                        response.put("CurrentTotalAmount", ((Object[]) o)[31] == null ? "-NA-" : ((Object[]) o)[31].toString());
                        response.put("datetime", ((Object[]) o)[32] == null ? "-NA-" : new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                                .format(((Object[]) o)[32]));
                        response.put("id", ((Object[]) o)[33] == null ? "-NA-" : ((Object[]) o)[33]);
                        response.put("PatientName", ((Object[]) o)[34] == null ? "-NA-" : ((Object[]) o)[34].toString());
                        response.put("FamilyHeadName", ((Object[]) o)[35] == null ? "-NA-" : ((Object[]) o)[35].toString());
                        response.put("VerifierName", ((Object[]) o)[36] == null ? "-NA-" : ((Object[]) o)[36].toString());
                        response.put("Version", ((Object[]) o)[37] == null ? "-NA-" : ((Object[]) o)[37].toString());
                        response.put("ImplantCode", ((Object[]) o)[38] == null ? "-NA-" : ((Object[]) o)[38].toString());
                        response.put("Gender", ((Object[]) o)[39] == null ? "-NA-" : ((Object[]) o)[39].toString());
                        response.put("UploadStatus", ((Object[]) o)[40] == null ? "-NA-" : ((Object[]) o)[40].toString());
//                        System.out.println(response);
                        try {
                            response.put("Transaction_Date", ((Object[]) o)[41] == null ? "-NA-" :  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(
                                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(((Object[]) o)[41].toString())));
                        } catch (ParseException e) {
                            throw new Exception(e.getMessage());
                        }
                        response.put("UnblockAmount", ((Object[]) o)[42] == null ? "-NA-" : Long.parseLong(((Object[]) o)[42].toString()));
                        response.put("Round", ((Object[]) o)[43] == null ? "-NA-" : ((Object[]) o)[43].toString());
                        response.put("BlockCode", ((Object[]) o)[44] == null ? "-NA-" : ((Object[]) o)[44].toString());
                        response.put("PanchayatCode", ((Object[]) o)[45] == null ? "-NA-" : ((Object[]) o)[45].toString());
                        response.put("VillageCode", ((Object[]) o)[46] == null ? "-NA-" : ((Object[]) o)[46].toString());
                        response.put("Age", ((Object[]) o)[47] == null ? "-NA-" : ((Object[]) o)[47].toString());
                        response.put("StateName", ((Object[]) o)[48] == null ? "-NA-" : ((Object[]) o)[48].toString());
                        response.put("DistrictName", ((Object[]) o)[49] == null ? "-NA-" : ((Object[]) o)[49].toString());
                        response.put("BlockName", ((Object[]) o)[50] == null ? "-NA-" : ((Object[]) o)[50].toString());
                        response.put("PanchayatName", ((Object[]) o)[51] == null ? "-NA-" : ((Object[]) o)[51].toString());
                        response.put("VillageName", ((Object[]) o)[52] == null ? "-NA-" : ((Object[]) o)[52].toString());
                        response.put("RegistrationNo", ((Object[]) o)[53] == null ? "-NA-" : ((Object[]) o)[53].toString());
                        response.put("SchemeName", ((Object[]) o)[54] == null ? "-NA-" : ((Object[]) o)[54].toString());
                        response.put("PolicyStartDate", ((Object[]) o)[55] == null ? "-NA-" : ((Object[]) o)[55].toString());
                        response.put("PolicyEndDate", ((Object[]) o)[56] == null ? "-NA-" : ((Object[]) o)[56].toString());
                        response.put("HospitalName", ((Object[]) o)[57] == null ? "-NA-" : ((Object[]) o)[57].toString());
                        response.put("ProcedureName", ((Object[]) o)[58] == null ? "-NA-" : ((Object[]) o)[58].toString());
                        response.put("PackageName", ((Object[]) o)[59] == null ? "-NA-" : ((Object[]) o)[59].toString());
                        response.put("PackageCategoryCode", ((Object[]) o)[60] == null ? "-NA-" : ((Object[]) o)[60].toString());
                        response.put("PackageID", ((Object[]) o)[61] == null ? "-NA-" : ((Object[]) o)[61].toString());
                        response.put("HospitalStateCode", ((Object[]) o)[62] == null ? "-NA-" : ((Object[]) o)[62].toString());
                        response.put("HospitalDistrictCode", ((Object[]) o)[63] == null ? "-NA-" : ((Object[]) o)[63].toString());
                        response.put("ActualDateofAdmission", ((Object[]) o)[64] == null ? "-NA-" : ((Object[]) o)[64].toString());
                        response.put("ActualDateofDischarge", ((Object[]) o)[65] == null ? "-NA-" : ((Object[]) o)[65].toString());
                        response.put("AuthorizedCode", ((Object[]) o)[66] == null ? "-NA-" : ((Object[]) o)[66].toString());
                        response.put("ClaimID", ((Object[]) o)[67] == null ? "-NA-" : ((Object[]) o)[67].toString());
                        response.put("TriggerFlag", ((Object[]) o)[68] == null ? "-NA-" : ((Object[]) o)[68].toString());
                        response.put("ImplantData", ((Object[]) o)[69] == null ? "-NA-" : ((Object[]) o)[69].toString());
                        response.put("ReferralCode", ((Object[]) o)[70] == null ? "-NA-" : ((Object[]) o)[70].toString());
                        response.put("PackageType", ((Object[]) o)[71] == null ? "-NA-" : ((Object[]) o)[71].toString());
                        response.put("HospitalDistrict", ((Object[]) o)[72] == null ? "-NA-" : ((Object[]) o)[72].toString());
                        response.put("PDFPath", ((Object[]) o)[73] == null ? "-NA-" : ((Object[]) o)[73].toString());
                        response.put("NeedMoreDocs", ((Object[]) o)[74] == null ? "-NA-" : ((Object[]) o)[74].toString());
                        response.put("MoreDocsDescription", ((Object[]) o)[75] == null ? "-NA-" : ((Object[]) o)[75].toString());
                        response.put("PDFPathWeb", ((Object[]) o)[76] == null ? "-NA-" : ((Object[]) o)[76].toString());
                        response.put("CreatedOn", ((Object[]) o)[77] == null ? "-NA-" : new SimpleDateFormat("ddMMyyyy").format(
                                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(((Object[]) o)[77].toString())));
                        responseList.add(response);
                    }

                    apiServiceLog.setOutputData(new Gson().toJson(responseList));
                    apiServiceLog.setDataSize(responseList.size());
                    apiServiceLog.setApiEndTime(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new Date())));
                    apiServiceLogRepository.save(apiServiceLog);
                } else{
                    apiServiceLog.setOutputData(null);
                    apiServiceLog.setDataSize(0);
                    apiServiceLog.setApiEndTime(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new Date())));
                    apiServiceLogRepository.save(apiServiceLog);
                    throw new Exception("No Data Found For Given Date Range!");
                }
            } else
                throw new Exception("Invalid Security Code!");
        } catch (Exception e) {
            logger.error("Exception Occurred in getClaimDetails method of StateDashboardServiceImpl : ", e);
            SaveAPIErrorLog saveAPIErrorLog = new SaveAPIErrorLog(apiServiceErrorLogRepository);
            saveAPIErrorLog.saveAPIErrorLog(1, "StateDashboardTransactionData", e);
            throw e;
        }
        return responseList;
    }
}

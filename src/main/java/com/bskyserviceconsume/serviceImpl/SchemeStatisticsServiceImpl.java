package com.bskyserviceconsume.serviceImpl;

import com.bskyserviceconsume.model.APIServiceLog;
import com.bskyserviceconsume.model.TblBskyStatisticsReport;
import com.bskyserviceconsume.repository.APIServiceErrorLogRepository;
import com.bskyserviceconsume.repository.APIServiceLogRepository;
import com.bskyserviceconsume.repository.TblBskyStatisticsReportRepository;
import com.bskyserviceconsume.service.SchemeStatisticsService;
import com.bskyserviceconsume.util.SaveAPIErrorLog;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Project : BSKY Service Consume
 * @Author: Sambit Kumar Pradhan
 * @Date: 19-Mar-2024 : 11:37 AM
 */
@Service
public class SchemeStatisticsServiceImpl implements SchemeStatisticsService {

    private final TblBskyStatisticsReportRepository tblBskyStatisticsReportRepository;
    private final APIServiceLogRepository aPIServiceLogRepository;
    private final APIServiceErrorLogRepository aPIServiceErrorLogRepository;

    public SchemeStatisticsServiceImpl(TblBskyStatisticsReportRepository tblBskyStatisticsReportRepository,
                                       APIServiceLogRepository aPIServiceLogRepository,
                                       APIServiceErrorLogRepository aPIServiceErrorLogRepository) {
        this.tblBskyStatisticsReportRepository = tblBskyStatisticsReportRepository;
        this.aPIServiceLogRepository = aPIServiceLogRepository;
        this.aPIServiceErrorLogRepository = aPIServiceErrorLogRepository;
    }

    @Override
    public Map<String, Object> getSchemeStatisticsData(Map<String, Object> request) throws Exception {
        Date date = new SimpleDateFormat("dd-MM-yyyy").parse(request.get("date").toString());
        Map<String, Object> response = new LinkedHashMap<>();

        try {
            APIServiceLog apiServiceLog = new APIServiceLog();
            apiServiceLog.setApiId(17);
            apiServiceLog.setApiName("Scheme Statistics Report");
            apiServiceLog.setCreatedOn(new Date());
            apiServiceLog.setCreatedBy("System");
            apiServiceLog.setStatusFlag(0);
            apiServiceLog.setApiHitTime(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new Date())));


            TblBskyStatisticsReport tblBskyStatisticsReport = tblBskyStatisticsReportRepository.getTblBskyStatisticsReportByCreatedOn(date);
            if (tblBskyStatisticsReport != null) {

                response.put("reportDate", new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(tblBskyStatisticsReport.getReportDate()));
                response.put("totalExpenses", tblBskyStatisticsReport.getTotalExpenses());
                response.put("totalAdmission", tblBskyStatisticsReport.getTotalAdmission());
                response.put("totalOpthalmology", tblBskyStatisticsReport.getTotalOpthalmology());
                response.put("totalDialysis", tblBskyStatisticsReport.getTotalDialysis());
                response.put("totalCovid", tblBskyStatisticsReport.getTotalCovid());
                response.put("totalSurgical", tblBskyStatisticsReport.getTotalSurgical());
                response.put("totalChemotherapy", tblBskyStatisticsReport.getTotalChemotherapy());
                response.put("totalGynaecology", tblBskyStatisticsReport.getTotalGynaecology());
            }
            apiServiceLog.setInputData(new Gson().toJson(date));
            apiServiceLog.setOutputData(new Gson().toJson(response));
            apiServiceLog.setApiEndTime(new Date());
            apiServiceLog.setStatusFlag(0);
            apiServiceLog.setDataSize(1);
            aPIServiceLogRepository.save(apiServiceLog);

            return response;
        } catch (Exception e) {
            SaveAPIErrorLog saveAPIErrorLog = new SaveAPIErrorLog(aPIServiceErrorLogRepository);
            saveAPIErrorLog.saveAPIErrorLog(17, "Scheme Statistics Report", e);
            e.printStackTrace();
            throw e;
        }
    }
}

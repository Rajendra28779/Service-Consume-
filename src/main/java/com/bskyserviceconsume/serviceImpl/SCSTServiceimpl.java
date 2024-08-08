
package com.bskyserviceconsume.serviceImpl;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bskyserviceconsume.bean.ResponseBean;
import com.bskyserviceconsume.model.APIServiceLog;
import com.bskyserviceconsume.model.SCSTTransactionApiLog;
import com.bskyserviceconsume.repository.APIServiceLogRepository;
import com.bskyserviceconsume.repository.SCSTTransactionApiLogRepository;
import com.bskyserviceconsume.service.SCSTService;
import com.bskyserviceconsume.util.CommonClassHelper;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * rajendra Prasad Sahoo
 * Dt : 06-02-2024
 * SCST API Integration
 */
@Service
public class SCSTServiceimpl implements SCSTService {
	
	@Value("${scst.api.url}")
	private String scstapiUrl;

	@Value("${scst.username}")
	private String scstusername;

	@Value("${scst.password}")
	private String scstpassword;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired 
	private APIServiceLogRepository apiServiceLogRepository;
	
	@Autowired
	private SCSTTransactionApiLogRepository scsttanslogrepository;

	/**
	  * @Auther : RAJENDRA PRASAD SAHOO
	  * @PURPOSE: Get Authentication
	  * @Date   : 06-02-2024
	  */
	@Override
	public Map<String, Object> getscstAuthentication(HttpServletRequest request) throws Exception {	
		Map<String, Object> map=new HashMap<>();       
	        try {
	        	JSONObject json = new JSONObject();
				json.put("username", scstusername);
				json.put("password", scstpassword);
				
				APIServiceLog apiServiceLog = new APIServiceLog();
	        	apiServiceLog.setApiName("getscstAuthentication");
	            apiServiceLog.setApiId(15);
	            apiServiceLog.setCreatedBy("System");
	            apiServiceLog.setCreatedOn(Calendar.getInstance().getTime());
	            apiServiceLog.setStatusFlag(0);	 
	            apiServiceLog.setApiHitTime(Calendar.getInstance().getTime());	
				apiServiceLog.setInputData(json.toString());
				
				String url=scstapiUrl+"authenticate";
				ResponseBean responseBody=callscstpostapi(json.toString(),"",url);				
				JSONObject jsonObject = new JSONObject(responseBody.getData());
				
				apiServiceLog.setApiEndTime(Calendar.getInstance().getTime());
				apiServiceLog.setOutputData(new Gson().toJson(jsonObject));
		        apiServiceLog.setDataSize(0);
				
				String token="";
	        	if(jsonObject.getString("status").equals("success")) {
					token="Bearer "+jsonObject.getString("token");
					HttpSession session = request.getSession();
			        session.setAttribute("token", token);
				}
		        map = CommonClassHelper.convertjsonStringToMap(responseBody.getData());		        
		        apiServiceLogRepository.save(apiServiceLog);		
		        
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(e);
			}	       
	        return map;
	    }
	

	/**
	  * @Auther : RAJENDRA PRASAD SAHOO
	  * @PURPOSE: Save Record in tribal Database
	  * @Date   : 07-02-2024
	  */
	@Override
	public Map<String, Object> saveSchemeTransaction(HttpServletRequest httprequest) throws Exception {
		Map<String, Object> map=new HashMap<>();
		ResultSet rs=null;
		Integer batchamount=500;
		Integer pagein=1;
		Integer pageout=500;
		try{   
			Boolean whilestatus=true;
			while(whilestatus) {
					List<Map<String, Object>> dataList = new ArrayList<>();
					
				StoredProcedureQuery storedProcedureQuery = this.entityManager
						.createStoredProcedureQuery("USP_GET_BSKYSTDATA_API")
						.registerStoredProcedureParameter("P_PAGE_IN", Integer.class, ParameterMode.IN)
						.registerStoredProcedureParameter("P_PAGE_END", Integer.class, ParameterMode.IN)
						.registerStoredProcedureParameter("P_MSG_OUT", void.class, ParameterMode.REF_CURSOR);
				
				storedProcedureQuery.setParameter("P_PAGE_IN", pagein);
				storedProcedureQuery.setParameter("P_PAGE_END", pageout);
				storedProcedureQuery.execute();
				rs = (ResultSet) storedProcedureQuery.getOutputParameterValue("P_MSG_OUT");
				
				while(rs.next()){
					Map<String, Object> data1 = new HashMap<>();
			        data1.put("schemeCode", rs.getString(1));
			        data1.put("aadhaarReferenceNumber", rs.getString(2));
			        data1.put("uniqueBeneficiaryId", rs.getString(3));
			        data1.put("financialYear", rs.getString(4));
			        data1.put("transactionType", rs.getString(5));
			        data1.put("transactionAmount", rs.getLong(6));
			        data1.put("inKindBenefitDetail", rs.getString(7));
			        data1.put("transactionDate", rs.getString(8));
				        Map<String, Object> schemeData1 = new HashMap<>();
				        schemeData1.put("socialCategory", rs.getString(9));
				        schemeData1.put("rationCardNo", rs.getString(10)); 
				        schemeData1.put("primaryMobileNumber", rs.getString(11));
				        schemeData1.put("educationalQualification", rs.getString(12));
				        schemeData1.put("annualIncome", rs.getString(13));
				    data1.put("schemeData",schemeData1);
			        data1.put("packageCode", rs.getString(16));
			        data1.put("subPackageCode", rs.getString(17));
			        data1.put("actualDateofAdmission", rs.getString(18));
			        data1.put("actualDateofDischarge", rs.getString(20));
			        data1.put("dateofDischarge", rs.getString(21));
			        data1.put("codeofInstitution", rs.getString(22));
			        data1.put("beneficiaryName", rs.getString(23));
			        data1.put("gender", rs.getString(24));
			        dataList.add(data1);
				}	
				if(dataList.size()>0) {
					    JSONArray jsonArray = new JSONArray(dataList);
				        JSONObject json = new JSONObject();
				        json.put("data", jsonArray);
				        
				    String token=getToken(httprequest);
				    
			        APIServiceLog apiServiceLog = new APIServiceLog();
		        	apiServiceLog.setApiName("getscstAuthentication");
		            apiServiceLog.setApiId(15);
		            apiServiceLog.setCreatedBy("System");
		            apiServiceLog.setCreatedOn(Calendar.getInstance().getTime());
		            apiServiceLog.setStatusFlag(0);	 
		            apiServiceLog.setApiHitTime(Calendar.getInstance().getTime());	
					apiServiceLog.setInputData(jsonArray.toString());				
			        	        
			        String url=scstapiUrl+"saveSchemeTransaction";
			        ResponseBean response=callscstpostapi(json.toString(),token,url);
			        if(response.getStatus()==HttpStatus.FORBIDDEN.value()) {
			        	getscstAuthentication(httprequest);
			        	HttpSession session = httprequest.getSession();
			        	token=(String) session.getAttribute("token");
			        	response=callscstpostapi(json.toString(),token,url);
			        }			        
			        apiServiceLog.setApiEndTime(Calendar.getInstance().getTime());
					apiServiceLog.setOutputData(new Gson().toJson(response.getData()));
			        apiServiceLog.setDataSize(0);
			        
			        map = CommonClassHelper.convertjsonStringToMap(response.getData());			        
			        
			        SCSTTransactionApiLog scsttanslog=new SCSTTransactionApiLog();
			        scsttanslog.setTransactionId(map.get("transactionId").toString());
			        Double d=(Double) map.get("savedTransactionsCount");
			        scsttanslog.setSaveddatacount(d.intValue());
			        d=(Double) map.get("errorTransactionsCount");
			        scsttanslog.setErrordatacount(d.intValue());
			        scsttanslog.setErrormessage(map.get("errors").toString());
			        scsttanslog.setCreateby("System");
			        scsttanslog.setCreateon(Calendar.getInstance().getTime());
			        scsttanslog.setStatusflag(0);
			        scsttanslogrepository.save(scsttanslog);	
			        apiServiceLogRepository.save(apiServiceLog);
				}
		       if(dataList.size()<batchamount) {
		    	   whilestatus=false;
		       }
			}
		}catch (Exception e) {
			throw new Exception(e);
		}
		return map;
	}

	/**
	  * @Auther : RAJENDRA PRASAD SAHOO
	  * @PURPOSE: Check the count the record saved
	  * @Date   : 07-02-2024
	  */
	@Override
	public Map<String, Object> progress(HttpServletRequest httprequest) throws Exception {
		Map<String, Object> map=new HashMap<>();
		ResponseBean responseBody=new ResponseBean();
		try {
			String token=getToken(httprequest);
	        
	        APIServiceLog apiServiceLog = new APIServiceLog();
        	apiServiceLog.setApiName("getscstAuthentication");
            apiServiceLog.setApiId(15);
            apiServiceLog.setCreatedBy("System");
            apiServiceLog.setCreatedOn(Calendar.getInstance().getTime());
            apiServiceLog.setStatusFlag(0);	 
            apiServiceLog.setApiHitTime(Calendar.getInstance().getTime());	
			apiServiceLog.setInputData(null);
			
			String url=scstapiUrl+"progress";
			responseBody = callscstgetapi(url,token);
	        if(responseBody.getStatus()==HttpStatus.FORBIDDEN.value()) {
	        	getscstAuthentication(httprequest);
	        	HttpSession session = httprequest.getSession();
	        	token=(String) session.getAttribute("token");
	        	responseBody = callscstgetapi(url,token);
	        }
	        
	        apiServiceLog.setApiEndTime(Calendar.getInstance().getTime());
			apiServiceLog.setOutputData(new Gson().toJson(responseBody.getData()));
	        apiServiceLog.setDataSize(0);
	        
	        map = CommonClassHelper.convertjsonStringToMap(responseBody.getData());
	        apiServiceLogRepository.save(apiServiceLog);
	        
		}catch (Exception e) {
			throw new Exception(e);
		}
		return map;
	}

	/**
	  * @Auther : RAJENDRA PRASAD SAHOO
	  * @PURPOSE: Check the Histroy of saved record
	  * @Date   : 07-02-2024
	  */
	@Override
	public Map<String, Object> transactionHistory(String transactionId, HttpServletRequest httprequest) throws Exception {
		Map<String, Object> map=new HashMap<>();
		try {
			String token=getToken(httprequest);
			
			APIServiceLog apiServiceLog = new APIServiceLog();
        	apiServiceLog.setApiName("getscstAuthentication");
            apiServiceLog.setApiId(15);
            apiServiceLog.setCreatedBy("System");
            apiServiceLog.setCreatedOn(Calendar.getInstance().getTime());
            apiServiceLog.setStatusFlag(0);	
            apiServiceLog.setApiHitTime(Calendar.getInstance().getTime());	
			apiServiceLog.setInputData(null);
            
	        String url=scstapiUrl+"transactionHistory/"+transactionId;
	        ResponseBean response=callscstgetapi(url,token);
	        if(response.getStatus()==HttpStatus.FORBIDDEN.value()) {
	        	getscstAuthentication(httprequest);
	        	HttpSession session = httprequest.getSession();
	        	token=(String) session.getAttribute("token");
	        	response=callscstgetapi(url,token);
	        }
	        
	        apiServiceLog.setApiEndTime(Calendar.getInstance().getTime());
			apiServiceLog.setOutputData(new Gson().toJson(response.getData()));
	        apiServiceLog.setDataSize(0);
	        
	        map = CommonClassHelper.convertjsonStringToMap(response.getData());
	        apiServiceLogRepository.save(apiServiceLog);
	        
		}catch (Exception e) {
			throw new Exception(e);
		}
		return map;
	}
	
	/**
	  * @Auther : RAJENDRA PRASAD SAHOO
	  * @PURPOSE: Common method for call SCST API (Post Method) By parse url and token and Request body
	  * @Date   : 08-02-2024
	  */
	private ResponseBean callscstpostapi(String jsonobject,String token,String url) {
		ResponseBean responseBody=new ResponseBean();
		 OkHttpClient client = new OkHttpClient();	        
		 MediaType mediaType = MediaType.parse("application/json");
	    
	        RequestBody body = RequestBody.create(jsonobject, mediaType);
	        Request request = new Request.Builder()
	                .url(url)
	                .post(body)
	                .addHeader("Content-Type", "application/json")
	                .addHeader("Authorization", token)
	                .build();

	        try (Response response = client.newCall(request).execute()) {
	        	responseBody.setStatus(response.code());
	        	responseBody.setData(response.body().string());
	        } catch (IOException e) {
	            e.printStackTrace();
	        } 
	        return responseBody;
	}
	
	/**
	  * @Auther : RAJENDRA PRASAD SAHOO
	  * @PURPOSE: Common method for call SCST API (Get Method) By parse url and token
	  * @Date   : 08-02-2024
	  */	
	private ResponseBean callscstgetapi(String url,String token) {
		ResponseBean responseBody=new ResponseBean();
		 OkHttpClient client = new OkHttpClient();
		 
		   /**  URL with query parameters
		     *  HttpUrl.Builder urlBuilder = HttpUrl.parse("URL").newBuilder();
		     *  urlBuilder.addQueryParameter("username", username); //add @RequestParam
		     *  String url = urlBuilder.build().toString();
			 */
		 
	        Request request = new Request.Builder()
	                .url(url)
	                .get()
	                .addHeader("Content-Type", "application/json")
	                .addHeader("Authorization", token)
	                .build();

	        try (Response response = client.newCall(request).execute()) {
	        	responseBody.setStatus(response.code());
	        	responseBody.setData(response.body().string());
	        } catch (IOException e) {
	            e.printStackTrace();
	        } 
	        return responseBody;
	}	
	
	/**
	  * @Auther : RAJENDRA PRASAD SAHOO
	  * @PURPOSE: Common method for get token from session (HttpSession session) 
	  * @Date   : 08-02-2024
	  */
	private String getToken(HttpServletRequest httprequest) throws Exception {
		HttpSession session = httprequest.getSession();
        String token=(String) session.getAttribute("token");
        if(token==null) {
        	getscstAuthentication(httprequest);
        	token=(String) session.getAttribute("token");
        }
        return token;
	}

}

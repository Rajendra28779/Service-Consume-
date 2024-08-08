package com.bskyserviceconsume.service;


import com.bskyserviceconsume.bean.MoSarkarRequestData;
import com.bskyserviceconsume.bean.Response;

import java.text.ParseException;
import java.util.List;

/**
 * @author santanu.barad
 *
 */
public interface MoSarkarService {
	Response getDataForMoSarkar() throws Exception;
	List<MoSarkarRequestData> getMoSarkarDataList() throws Exception;
	void getMoSarkarInstitutionId() throws ParseException;

}

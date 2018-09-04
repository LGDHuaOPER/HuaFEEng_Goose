package com.eoulu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.dao.TrainingRecordsDao;
import com.eoulu.entity.TrainingRecords;
import com.eoulu.service.TrainingRecordsService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TrainingRecordsServiceImpl implements TrainingRecordsService{

	@Override
	public List<Map<String, Object>> getDataByPage(Page page, String column, String content) {
		
		return new TrainingRecordsDao().getDataByPage(page, column, content);
	}

	@Override
	public int getCounts(String column, String content) {
	
		return new TrainingRecordsDao().getCounts(column, content);
	}

	@Override
	public boolean insert(TrainingRecords records) {

		return new TrainingRecordsDao().insert(records);
	}

	@Override
	public boolean update(TrainingRecords records) {

		return new TrainingRecordsDao().update(records);
	}



	@Override
	public List<Map<String, Object>> getTrainingDetails(int recordID) {
		
		return new TrainingRecordsDao().getTrainingDetails(recordID);
	}

	@Override
	public boolean saveTrainingDetails(String detailsJson) {
		JSONArray array = null;
		if(!detailsJson.equals("")){
			array = JSONArray.fromObject(detailsJson);
		}
		JSONObject object = null;
		Map<String, Object> updateMap = null;
		List<Map<String, Object>> list = new ArrayList<>();
		for(int i = 0;i < array.size();i ++){
			object = array.getJSONObject(i);
			updateMap = new HashMap<>();
			updateMap.put("RecordID", Integer.parseInt(object.get("RecordID").toString()));
			updateMap.put("Number", Integer.parseInt((String)object.get("Number").toString()));
			updateMap.put("SerialNumber",(String)object.get("SerialNumber"));
			updateMap.put("Attend", (String)object.get("Attend"));
			updateMap.put("TrainingTime", (String)object.get("TrainingTime"));
			updateMap.put("Pass", (String)object.get("Pass"));
			updateMap.put("Feedback", (String)object.get("Feedback"));
			
			
			list.add(updateMap);
		}
		
		return new TrainingRecordsDao().saveTrainingDetails(list);
		
	}
	

}

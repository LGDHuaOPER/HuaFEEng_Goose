package com.eoulu.service;

import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.TrainingRecords;

public interface TrainingRecordsService {
	public List<Map<String,Object>> getDataByPage(Page page,String column,String content);
	
	public int getCounts(String column,String content);
	
	public boolean insert(TrainingRecords records);
	
	public boolean update(TrainingRecords records);
	
	public boolean saveTrainingDetails(String detailsJson);

	
	public List<Map<String, Object>> getTrainingDetails(int recordID);

}

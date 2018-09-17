package com.eoulu.service;

import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.Reimburse;

public interface ReimburseService {
	
	public List<Map<String, Object>> getAllData(Page page,String startTime,String endTime);
	
	public int getCounts(String startTime,String endTime);
	
	public List<Map<String, Object>> getOnlyData(Page page,String startTime,String endTime,String email);
	
	public int getOnlyCounts(String startTime,String endTime,String email);
	
	public String addReimburse(Reimburse reimburse,String detailJson,String travelJson);
	
	public boolean updateReimburse(Reimburse reimburse,String detailJson,String travelJson);
	
	public String getUserName(String email);
	
	
	//获取申请表信息
	public Map<String, Object> getApplication(int RequestID);
	
	//审批
	public String review(int ID,String state,String name,String reason,String filingDate);
	
	public void sendNoticeMail();
	
	public String exportFile(String ids,String year,String month);
	
	//查询通知列表
	public List<Map<String, Object>> getList(Page page);
	
	public int getListCount();
	
	public boolean saveAttachment(Reimburse reimburse,String folder,String deleteFile);
	
}

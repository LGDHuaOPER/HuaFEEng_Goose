package com.eoulu.service;

import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.Examination;

public interface ExaminationService {
	public List<Map<String, Object>> getDataByPage(Page page,String classify);

	public int getAccount(String classify);

	public List<Map<String, Object>> getExaminer();

	public boolean addExaminer(String examiner);
	
	//public boolean deleteExaminer(String[] ID);
	
	public boolean insertExamination(Examination examination);
	
	public boolean updateExamination(Examination examination);
	
	public String sendAssessmentNotice(Examination examination, String[] examiners,String[] examinerID,String recipient);
	
	public String saveAssessmentNotice(Examination examination, String[] examiners,String[] examinerID);

	public List<Map<String, Object>> getAssessmentNotice(Page page,String column1,String content1);
	
	public int getAssessmentNoticeCount(String column1,String content1);
	
	public String saveScore(String scoreJson);
	
	//public String saveConfirm(String confirmJson);
	
	public List<Map<String, Object>> getStatisticsByStaff(String classify);
	
	public List<Map<String, Object>> getStatisticsByTime(String basis,String startTime,String endTime);
	
	public List<Map<String,Object>> getDepartmentStatistics(String Number);
	
	//动态加载下拉框
	
	public List<Map<String, Object>> getSubject();
	
	public List<Map<String, Object>> getNumber(String Subject);
	
	
	//根据部门、科目查询
	public List<Map<String, Object>> queryDetails(Page page,String department,String number);
	
	public int queryCounts(String department,String number);
		
	


}


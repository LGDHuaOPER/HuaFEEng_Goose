package com.eoulu.service;

import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.NonStandard;

public interface NonStandardService {
	
	public int insert(NonStandard nonStandard);
	
	public boolean update(NonStandard nonStandard);
	
	public List<Map<String, Object>> getDataByPage(Page page,String type,String column1,String content1,String column2,String content2);
	
	public int getCounts(String type,String column1,String content1,String column2,String content2);
	
	public String sendMail(NonStandard nonStandard);
	
	public List<NonStandard> getRemindProject();
	
	public boolean deleteProject(int ID);
	
	public List<Map<String, Object>> getProjectName(String key);
	

}

package com.eoulu.service;

import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.Task;

public interface TaskService {
	public List<Map<String, Object>> getDataByPage(Page page,String type,String column1,String content1,String column2,String content2);
	public boolean insert(Task task);
	public boolean update(Task task);
	public int getCounts(String type,String column1,String content1,String column2,String content2);
	public String sendMail(Task task);
	public List<Map<String,Object>> getStatistics(String year,String month,String type);

}

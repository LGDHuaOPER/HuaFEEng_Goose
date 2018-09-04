package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

public interface StatisticsService {

	
	/**
	 * ��ȡ���۴���ָ��ʱ�������ǩ���ĺ�ͬ��������������
	 * */
	public List<Map<String,Object>> getStatisticsBySales(String startTime,String endTime);
	
	
	
	/**
	 * ��ȡ���۴���ָ��ʱ�������ǩ���ĺ�ͬ��������������
	 * */
	public List<Map<String, Object>> getStatisticsByArea(String startTime, String endTime,boolean monthTarget);
	
	/**
	 * ��ȡָ��ʱ����ڵĸ������Ŀ��ֵ�����ֵ
	 * */
	public List<Map<String, Object>> getTargetValueByArea(String startTime, String endTime);
	
	
	/**
	 * ��ȡָ��ʱ����ڵĸ������Ŀ��ֵ�����ֵ��ƴ�ӳ��ַ�������[[Ŀ��ֵ],[���ֵ]],���ձ���--�Ϸ�--���Ϸ�����
	 * */
	public String getAreaDataToString(String startTime, String endTime);
	
	
	/**
	 * ��ȡ���۴����ҵ����������������
	 * */
	public String getSalesDataToString(String startTime, String endTime);
	
	/**
	 * ��ȡָ��ʱ���Ŀ��ֵ
	 * */
	public float getTargetValue(String startTime, String endTime);
	
	/**
	 * ��ȡָ��ʱ�����ֵ
	 * */
	public float getCompleteValue(String startTime, String endTime);

	
	public List<Map<String,Object>> getStatisticsByAreaPerMonth(String year);
	
	public List<Map<String, Object>> getStatisticsByArea(String startTime, String endTime,String startYear,String endYear);
	
	public void exportExcel(List<Map<String,Object>> data,String name,HttpServletResponse response);
	
	public List<Map<String,Object>> getExcelData(String year);
}

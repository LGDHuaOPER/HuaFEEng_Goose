/**
 * 
 */package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;
import com.eoulu.entity.InventoryInfo;
import com.eoulu.entity.Logistics;
import com.eoulu.entity.OrderInfo;

/** @author  ���� : zhangkai
* @date ����ʱ�䣺2017��6��12�� ����10:36:12 
* @version 1.0  
* @since  
* @return  
*/
/**
* @author zhangkai
*
*/
public interface InventoryInfoService {
	
	
	public boolean addInventoryInfo(HttpServletRequest request);

	/*
	 * �����޲����Ĳ�ѯ�������з�ҳ
	 * */
	public List<Map<String,Object>> getInventoryByCommon(Page page);
	
	
	/*
	 * ��ȡ���е��豸����
	 * */
	public int getAllEquipmentCounts();
	
	
	/*
	 * ��������ѯ�������豸��Ϣ��ͳ�Ƴ��������
	 * */
	public List<Map<String,Object>> resultToStatisticsInventory(String conditon,Map<String,Object> parameter, List<Map<String, Object>> ls);

	/*
	 * �޸Ĳ�Ʒ���ڳ�����
	 * */
	public boolean modifyInitQuantity(int equipmentID,int quantity);
	
	
	/*
	 * ��ȡ������Ϣ
	 * */
	public List<Map<String,Object>> getInInventoryInfo(int equipmentID,int type);
	/*
	 * ��ȡ�����Ϣ
	 * */
	public List<Map<String,Object>> getInInventoryInfo1(int equipmentID,int type,String Model);
	
	/*
	 * ��ӿ����Ϣ
	 * */
	public boolean insertInInventory(InventoryInfo inventoryInfo);
	
	
	
	/*
	 * �޸Ŀ����Ϣ
	 * */
	public boolean modifyInInventory(InventoryInfo inventoryInfo);
	
	
	/*
	 * ɾ�������Ϣ
	 * */
	public boolean deleteInInventory(InventoryInfo inventoryInfo);
	
	
	/*
	 * �����Ϣ��һ��ѯ
	 * */
	public List<Map<String,Object>> QueryInventoryInfoByOne(String conditon,Map<String,Object> parameter,Page page);
	
	
	/*
	 * ��һ��ѯ������
	 * */
	public int QueryInventoryInfoByOneCounts(String condition,Map<String,Object> parameter);
	
	
	
	/*
	 * ��������ID�õ���ͬ�ţ�PO�ţ��豸ID
	 * */
	public Map<String,Object> getInventoryByOrderID(int orderInfoID);
	
	
	/*
	 * ��������ID�Զ����������Ϣ
	 * */
	public int importInInventoryInfo(int orderInfoID,Logistics logistics,OrderInfo orderInfo);
	
	
	/*
	 * ��������ID�Զ����������Ϣ
	 * */
	public int importOutInventoryInfo(int orderInfoID,Logistics logistics,OrderInfo orderInfo);


	public Map<String, List<Map<String, Object>>> getinventoryInfoByEquipment(List<Map<String, Object>> equipmentList,String startTime,String endTime);
	
	public String getModel(int id);

}

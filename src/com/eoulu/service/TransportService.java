/**
 * 
 */package com.eoulu.service;

import java.util.List;
import java.util.Map;

/** @author  ���� : zhangkai
* @date ����ʱ�䣺2017��5��31�� ����6:19:51 
* @version 1.0  
* @since  
* @return  
*/
/**
* @author zhangkai
*
*/
public interface TransportService {

	/**
	 * �޸ĺ�ͬ״̬
	 * */
	public boolean modifyContractStatus(int orderID,int status);
	
	/**
	 * ��ȡ��ͬ����
	 * */
	public String getOrderType(int orderID);
	
	
	/**
	 * ����IDͳ��USD��RMB���
	 * */
	public List<Map<String,Object>> getPOAmountsByID(int orderID);
	
	/**
	 * �޸����״̬1
	 * */
	public boolean modifyReview1Status(int review1,int userID,int orderInfoID);
	
	
	/**
	 * �޸����״̬2
	 * */
	public boolean modifyReview2Status(int review2,int userID,int orderInfoID);
	
	/**
	 * ������ͬ����������Ϣ����ͬ�Ŷ�ӦPO���룩
	 * 
	 * @param content   ��Ҫ����������
	 * 
	 * @return ���ɵ��ļ���ַ
	 * */
	public String ExportOrderAndLogisticsInfo(List<Map<String,Object>> content);
}

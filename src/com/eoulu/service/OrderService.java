/**
 * 
 */
package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;
import com.eoulu.entity.BillCharge;
import com.eoulu.entity.CompleteOrder;
import com.eoulu.entity.Consignee;
import com.eoulu.entity.Logistics;
import com.eoulu.entity.Order;
import com.eoulu.entity.OrderInfo;
import com.eoulu.entity.Quotes;

/**
 * @author zhangkai
 *
 */
public interface OrderService {


	/**
	 * ���뱨����Ϣ��������ͬ��ϸ��Ϣ
	 * */
	public int orderAdd(CompleteOrder completeOrder);
	
	public int updateIsSend(Order order);


	/**
	 * ��request�еĲ���ת��ΪCompleteOrder����
	 * 
	 * */
	public CompleteOrder reqToObject(HttpServletRequest request);


	/**
	 * 
	 * �޸ĺ�ͬ��ϸ
	 *
	 * */
	public int modifyQuotes(Quotes quotes, int id);



	/**
	 * ���붩����ϸ
	 * 
	 * */
	public int insertOrderInfo(HttpServletRequest request);



	/**
	 * ��ȡ���ж���������
	 * 
	 * */
	public int getAllCounts(int type);
	
	
	/**
	 * ��ȡ���ж������Ұ���ǩ�����ڽ�������
	 * 
	 * */
	public List<Map<String, Object>> getAllOrder(Page page,int type);
	
	public List<Map<String, Object>> getAllOrderByActualDelivery(Page page,String condition,String column,int type);


	/**
	 * ���ݶ��������ѯ����
	 * 
	 * */
	public int getCountByClassify(String classify1,Object parameter1,String classify2,Object parameter2,int type);
	
	public int getCountByClassify(String classify1, Object parameter1, String classify2, Object parameter2,String condition,int type);
	/**
	 * ���ݶ�ʱ���������ѯ����
	 * 
	 * */
	public int getCountByTimeClassify(String classify1,Map<String,Object> map1,String classify2,Map<String,Object> map2,int type);
	
	public int getCountByTimeClassify(String classify1, Map<String, Object> map1, String classify2,Map<String, Object> map2,String condition,int type);
	/**
	 * ���ݵ���������ѯ����
	 * 
	 * */
	public int getCountByClassify(String classify,Object parameter,int type);
	
	public int getCountByClassify(String classify, Object parameter,String condition,int PageType);
	/**
	 * ���ݵ�������ʱ���ѯ����
	 * 
	 * */
	public int getCountByClassifyTime(String classify,String start_time1, String end_time1,String condition,int PageType);
	
	public int getCountByClassifyTime(String classify, String start_time1, String end_time1,int type);
	
	/**
	 * ���ݵ���������ѯ�ӷ�ҳ
	 * 
	 * */
	public List<Map<String,Object>> getOrderByPageInOne(String classify,Object parameter,Page page,int type);
	
	public List<Map<String, Object>> getOrderByPageInOne(String classify, Object parameter, Page page,String condition,int PageType);
	/**
	 * ���ݵ���������ѯ�ӷ�ҳPO_SO����
	 * 
	 * */
	public int getOrderByPageInOneByPoSoCount(String classify,Object parameter,String condition,int PageType);
	
	public int getOrderByPageInOneByPoSoCount(String classify,Object parameter,int PageType);
	
	/**
	 * ���ݵ���������ѯ�ӷ�ҳPO_SO
	 * 
	 * */
	public List<Map<String,Object>> getOrderByPageInOneByPoSo(String classify,Object parameter,Page page,int PageType);
	
	public List<Map<String,Object>> getOrderByPageInOneByPoSo(String classify,Object parameter,Page page,String condition,int PageType);
	/**
	 * ���ݵ���ʱ��������ѯ�ӷ�ҳ
	 * 
	 * */
	public List<Map<String,Object>> getOrderByTime(String classify,String start_time1,String end_time1,Page page,int type);
	
	public List<Map<String, Object>> getOrderByTime(String classify, String start_time1, String end_time1,Page page,String condition,int PageType);
	
	/**
	 * ���ݶ��������ѯ�ӷ�ҳ
	 * 
	 * */
	public List<Map<String,Object>> getOrderByPageInTwo(String classify1,Object parameter1,String classify2,Object parameter2,Page page, int type);
	
	public List<Map<String, Object>> getOrderByPageInTwo(String classify1, Object parameter1, String classify2,
			Object parameter2, Page page,String condition,int type);
	/**
	 * ���ݶ������ʱ���ѯ�ӷ�ҳ
	 * 
	 * */
	public List<Map<String,Object>> getOrderByPageInTwoTime(String classify1,Map<String,Object> map1,String classify2,Map<String,Object> map2,Page page,int type);
	
	public List<Map<String, Object>> getOrderByPageInTwoTime(String classify1, Map<String, Object> map1,
			String classify2, Map<String, Object> map2, Page page,String condition,int type);
	/**
	 * ģ����ѯorder������������ID��
	 * 
	 * */
	public Object[] getIDByBlur(String classify, String param);
	
	
	/**
	 * 
	 * ��ȡ������ϸ
	 * */
	
	public List<Map<String, Object>> getOrderInfoById(int orderID);
	
	
	/**
	 * �޸Ķ�����Ϣ
	 * 
	 * */
	public boolean modifyOrder(CompleteOrder completeOrder);
	
	/**
	 * ��ȡ��Ӧ����ϸ
	 * */
	public List<Map<String,Object>> getLogisticsByID(int id);
	
	
	/**
	 * �鿴��ͬ��ϸ
	 * */
	public List<Map<String,Object>> getQuotesByID(String id);
	
	
	
	/**
	 * �����ͬ����
	 * */
	public boolean OrderInfoAndLogisticsAdd(OrderInfo orderInfo,Logistics logistics);
	
	
	/**
	 * ɾ����ͬ����
	 * */
	public boolean deleteOrderInfo(String orderInfoId);
	
	
	/**
	 * 
	 * ��ȡ��ͬ����
	 * 
	 * */
	public List<Map<String,Object>> getOrderInfo(String orderID);
	
	/**
	 * �޸ĺ�ͬ������Ϣ
	 * */
	public boolean modifyOrderInfo(String id,String counts);
	
	
	/**
	 * ɾ����ͬ��Ϣ
	 * */
	public boolean deleteOrder(String id);
	
	/**
	 * �����û�������Ƿ񸶿��ֵ  ������һ��ID�����ݿ����û���û������ֵ����ô�ͽ����Ƿ񸶿���ӵ����ݿ⣬�ڷ���ID
	 * */
	public int getPaymentStatusID(String status);
	
	
	/**
	 * �����Զ�����
	 * */
	public boolean autoInputOrder(HttpServletRequest request);
	
	/**
	 * ȫ���Ķ���ת��Ϊ�����ݿ��ʽ��ͬ�ĸ�ʽ
	 * */
	public CompleteOrder[] orderToObject(Map<String,Object> map);
	
	
	/**
	 * ����excel�ļ�������path������
	 * */
	public List<Map<String,Object>> exportOrder(String queryType,Object[] classify,Object[] parameter,String path,String condition);
	/**
	 * ����excel�ļ�������path������
	 * */
	public Boolean exportOrderIncludePO(String queryType,Object[] classify,Object[] parameter,String path,String condition);
	
	public Boolean exportOrderStock(String queryType, Object[] classify, Object[] parameter, String path);
	
	public Boolean exportOrderStock(String queryType, Object[] classify, Object[] parameter, String path,String condition);
	
	/**
	 * ���յ�������ѯ����
	 * */
	public List<Map<String,Object>> getOrderByOne(String classify1,Object parameter1,String condition,int type);
	
	/**
	 * ���ն�������ѯ����
	 * */
	public List<Map<String,Object>> getOrderByTwo(String classify1,Object parameter1,String classify2,Object parameter2,String condition,int type);
	
	/**
	 * ������������ѯ����
	 * */
	public List<Map<String,Object>> getOrderByNone(int type);
	
	/**
	 * �޸Ķ�����ʵ�ʻ��ں�Ԥ�ƻ���
	 * */
	public boolean modifyLogisticsTime(Order order);
	
	
	/**
	 * ����ID��ȡ������Ϣ�����ص��Ƕ���ѯ�Ķ�����Ϣ
	 * */
	public List<Map<String,Object>> getOrderByID(String id);
	
	
	/**
	 * ������������Ļ�ȡ����������ID��û�еĲ������ݿ�
	 * */
	public int getPaymentTermsID(String condition);
	
	public Boolean exportOrderIncludePO(String queryType, Object[] classify, Object[] parameter, String path);
	
	/*
	 *����ѯ�����Ľ��������һ���ֶ���ʾ����ͬ�����Ƿ��Ѿ���ȫ��˹� ��
	 * */
	public List<Map<String,Object>> queryResultAddReview(List<Map<String,Object>> queryList);
	
	public int getAllCountsIfActualDelivery(String condition,String column,int type);
	
	public int getShippedCounts(int type,String startTime,String endTime);
	
	public List<Map<String,Object>> getShippedDetail(Page page,int type,String startTime,String endTime);
	
	
	public List<Map<String, Object>> getTransportDetail(Page page, String condition,int type);
	
	public boolean ConsigneeAdd(Consignee consignee);
	
	public List<Map<String, Object>> exportOrder(String queryType, Object[] classify, Object[] parameter, String path);
	
	public List<Map<String, Object>> getOrderByTwo(String classify1, Object parameter1, String classify2,
			Object parameter2,int type);

	public List<Map<String, Object>> getOrderByOne(String classify, Object parameter,int type);
	
	public List<Map<String,Object>> MatchQuotation(HttpServletRequest request);
	
	public boolean addPurchaseInfo(HttpServletRequest request);
	
	public List<Map<String, Object>> getSupplierBank(String supplier);
	
	public List<Map<String, Object>> queryPurchaseInfo(int ID,String supplier);
	
	public boolean setPurchaseMail(int ID);
	
	public String saveBillCharge(BillCharge charge);
	
	public List<Map<String, Object>> getBillCharge(int ID);
	
	public String sendInvoiceEmail(HttpServletRequest req);
	
	public boolean exportOrderExcel(String path,int type);

}

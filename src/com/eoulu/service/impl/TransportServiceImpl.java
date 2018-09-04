package com.eoulu.service.impl;

import java.util.List;
import java.util.Map;

import com.eoulu.dao.OrderDao;
import com.eoulu.service.TransportService;
import com.eoulu.util.DBUtil;

/** @author  ���� : zhangkai
 * @date ����ʱ�䣺2017��5��31�� ����6:28:41 
 * @version 1.0  
 * @since  
 * @return  
 */
public class TransportServiceImpl implements TransportService {

	/**
	 * �޸ĺ�ͬ״̬
	 * */
	@Override
	public boolean modifyContractStatus(int orderID, int status) {
		boolean flag = false;
		String sql = "update t_order set t_order.Status=? where t_order.ID=?";

		flag = new OrderDao().executeUpdate(sql, new Object[]{status,orderID})>=1?true:false;

		return flag;
	}

	/* (non-Javadoc)
	 * @see com.eoulu.service.TransportService#getOrderType(int)
	 */
	@Override
	public String getOrderType(int orderID) {
		String sql = "select * from t_quotes where OrderID=?";
		DBUtil db= new DBUtil();
		Object[] parameter = new Object[]{orderID};

		List<Map<String,Object>> ls = db.QueryToList(sql, parameter);

		String type = "RMB";

		if(ls.size()>0){
			int rmb,usd = 0;
			try{rmb = Integer.parseInt(ls.get(1).get("RMBQuotes").toString());}catch(Exception e){rmb = 0;}
			try{usd = Integer.parseInt(ls.get(1).get("USDQuotes").toString());}catch(Exception e){rmb = 0;}

			type = rmb>usd?"RMB":"USD";

		}

		return "{\"Type\":\""+type+"\"}";
	}

	/**
	 * ����PO��ͳ�ƽ��
	 * */
	@Override
	public List<Map<String, Object>> getPOAmountsByID(int orderID) {

		String sql = "select t_order_info.Date ActualDate,t_logistics.ActualPaymentTime,t_logistics.EstimatedPaymentTime,t_logistics.FactoryShipment,t_supplier.Name Supplier,t_logistics.SONO,t_logistics.PONO,sum(t_logistics.POAmount) USD,sum(t_logistics.RMBPOAmount) RMB from t_logistics left join t_supplier on t_logistics.Supplier=t_supplier.ID left join t_order_info on t_logistics.OrderInfoID=t_order_info.ID where t_logistics.OrderID=? group by t_logistics.PONO";
		DBUtil db = new DBUtil();
		Object[] parameter = new Object[]{orderID};

		List<Map<String,Object>> ls = db.QueryToList(sql, parameter);


		//////��ʽ������   ��ʼ
		int length = ls.size();
		if(length > 1){
			for(int i=1; i<length; i++){
				String RMB = ls.get(i).get("RMB").toString();
				String USD = ls.get(i).get("USD").toString();
					try{
					Float.parseFloat(RMB);	
					}catch(Exception e){
						ls.get(i).put("RMB", "0");	
					}
					
					try{
						Float.parseFloat(USD);	
						}catch(Exception e){
							ls.get(i).put("USD", "0");	
						}
			}
			
			
		}


		//////��ʽ������  ����



		return ls;
	}

//	@Test
//	public void test(){
//
//		System.out.println(getPOAmountsByID(2062));
//
//	}

	/* 
	 * �޸����״̬1
	 */
	@Override
	public boolean modifyReview1Status(int review1, int userID, int orderInfoID) {
		
		String sql = "update t_order_info set t_order_info.Review1=?,t_order_info.ReviewName1=?  where t_order_info.ID=?";
		Object[] parameter = new Object[]{review1,userID,orderInfoID};
		
		int result = new DBUtil().executeUpdate(sql, parameter);
		
		return result>=1?true:false;
	}

	/** 
	 * �޸����״̬1
	 * @param review2  ���״̬2
	 * 
	 * @param userID  ����˵�ID
	 * 
	 * @param
	 */
	@Override
	public boolean modifyReview2Status(int review2, int userID, int orderInfoID) {
		String sql = "update t_order_info set t_order_info.Review2=?,t_order_info.ReviewName2=?  where t_order_info.ID=?";
		Object[] parameter = new Object[]{review2,userID,orderInfoID};
		
		int result = new DBUtil().executeUpdate(sql, parameter);
		
		return result>=1?true:false;
	}

	/* 
	 * ������ͬ+������Ϣ���ļ�
	 */
	@Override
	public String ExportOrderAndLogisticsInfo(List<Map<String, Object>> content) {
		
		return null;
	}

}

package com.eoulu.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.entity.Equipment;
import com.eoulu.entity.InventoryInfo;
import com.eoulu.entity.Supplier;
import com.eoulu.util.DBUtil;

/** @author  ���� : zhangkai
* @date ����ʱ�䣺2017��6��12�� ����10:33:06 
* @version 1.0  
* @since  
* @return  
*/
public class InventoryInfoDao {
	
	/**
	 * 库存页面的添加
	 * @param request
	 * @return
	 */
	public static boolean InsertInventoryInfo(HttpServletRequest request){
		String SupplierName = request.getParameter("Name");
		//System.out.println(request.getParameter("Name"));
		String Model = request.getParameter("Model");
		String Remarks = request.getParameter("Remarks");
		//System.out.println(request.getParameter("InitialQuantity"));
		int InitialQuantity = Integer.parseInt(request.getParameter("InitialQuantity"));
		String OperatingTime = request.getParameter("OperatingTime");
		//System.out.println("OperatingTime"+OperatingTime);
		String OperatingTime2 = request.getParameter("OperatingTime2");
		//System.out.println("OperatingTime2"+OperatingTime2);
		int Quantity = Integer.parseInt(request.getParameter("Quantity"));
		int Quantity2 = Integer.parseInt(request.getParameter("Quantity2"));
		String ContractNo1 = request.getParameter("ContractNo1");
		//System.out.println(ContractNo1);
		String ContractNo2 = request.getParameter("ContractNo2");
		String OperationCode = request.getParameter("OperationCode");
		String OperationCode2 = request.getParameter("OperationCode2");
		int Types1 = Integer.parseInt(request.getParameter("Types1"));
		int Types2 = Integer.parseInt(request.getParameter("Types2"));
		float RMBPOAmount = Float.parseFloat(request.getParameter("RMBPOAmount"));
		float POAmount = Float.parseFloat(request.getParameter("POAmount"));
		float RMBQuotes = Float.parseFloat(request.getParameter("RMBQuotes"));
		float USDQuotes = Float.parseFloat(request.getParameter("USDQuotes"));
		
		List<Object> ls = new ArrayList<>();
		boolean flag = true;
		DBUtil db = new DBUtil();
	
		try {
			//供应商
			String sql1 = "insert into t_supplier (name) values (?)";
			Object[] parameter1 = new Object[]{SupplierName};
			Object Supplier =  db.insertGetId(sql1, parameter1); 
			//System.out.println("Supplier"+Supplier);
			//设备
			String sql2 = "insert into t_commodity_info (Model,CommodityName,InitialQuantity,SupplierID) values (?,?,?,?)";
			Object[] parameter2 = new Object[]{Model,Remarks,InitialQuantity,Supplier};
			Object EquipmentID = db.insertGetId(sql2, parameter2);
			//System.out.println("EquipmentID"+EquipmentID);
			ls.add(EquipmentID);
			if(Types1 == 1){//入库
		    	//订单
				String orderSql = "insert into t_order (ContractNo,isSend) values (?,?)";
		    	Object[] orderParameter = new Object[]{ContractNo1,"否"};
		    	Object OrderID1 =  db.insertGetId(orderSql, orderParameter);
		    	//System.out.println("OrderID1"+OrderID1);
		    	ls.add(OrderID1);
		    	//订单信息
		    	String orderInfoSql = "insert into t_order_info (OrderID,EquipmentModel) values (?,?)";
		    	Object[] orderInfoParameter = new Object[]{OrderID1,EquipmentID};
		    	Object OrderInfoID = db.insertGetId(orderInfoSql, orderInfoParameter);
		    	//System.out.println("OrderInfoID"+OrderInfoID);
		    	ls.add(OrderInfoID);
		    	//物流
		    	String logisticsSql = "insert into t_logistics (OrderID,OrderInfoID,Supplier,RMBPOAmount,POAmount) values (?,?,?,?,?)";
		    	Object[] logisticsParameter = new Object[]{OrderID1,OrderInfoID,Supplier,RMBPOAmount,POAmount};
		    	int logistics = db.executeUpdateNotClose(logisticsSql, logisticsParameter);
		    	//System.out.println("logistics"+logistics);
		        ls.add(logistics);
		    	 //库存信息
			    String sql3 = "insert into t_inventory_info (EquipmentID,OperatingTime,Quantity,ContractNo,OperationCode,Types,SourceSign,OrderInfoID) values (?,?,?,?,?,?,?,?)";
				Object[] parameter3 =  new Object[]{EquipmentID,OperatingTime,Quantity,ContractNo1,OperationCode,Types1,2,OrderInfoID};
				 int inventory = db.executeUpdateNotClose(sql3, parameter3);
				// System.out.println("inventory"+inventory);
				ls.add(inventory);
		    }
		    if(Types2 == 2){//出库
		    	//订单
		    	String orderSql = "insert into t_order (ContractNo,isSend) values (?,?)";
		    	Object[] orderParameter = new Object[]{ContractNo2,"否"};
		    	Object OrderID2 =  db.insertGetId(orderSql, orderParameter);
		    	//System.out.println("OrderID2"+OrderID2);
		    	ls.add(OrderID2);
		    	String quotesSql = "insert into t_quotes (OrderID,RMBQuotes,USDQuotes) values (?,?,?)";
		    	Object[] quotesParameter = new Object[]{OrderID2,RMBQuotes,USDQuotes};
		    	int quotes = db.executeUpdateNotClose(quotesSql, quotesParameter);
		    	//System.out.println("quotes"+quotes);
		    	ls.add(quotes);
		    	//订单信息
		    	String orderInfoSql = "insert into t_order_info (OrderID,EquipmentModel) values (?,?)";
		    	Object[] orderInfoParameter = new Object[]{OrderID2,EquipmentID};
		    	Object OrderInfoID2 = db.insertGetId(orderInfoSql, orderInfoParameter);
		    	//System.out.println("OrderInfoID2"+OrderInfoID2);
		    	ls.add(OrderInfoID2);
		    	 //库存信息
			    String sql3 = "insert into t_inventory_info (EquipmentID,OperatingTime,Quantity,ContractNo,OperationCode,Types,SourceSign,OrderInfoID) values (?,?,?,?,?,?,?,?)";
				Object[] parameter3 =  new Object[]{EquipmentID,OperatingTime2,Quantity2,ContractNo2,
						OperationCode2,Types2,2,OrderInfoID2};
				int inventory2 =  db.executeUpdateNotClose(sql3, parameter3);
				//System.out.println("inventory2"+inventory2);
				ls.add(inventory2);
		    }
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db.closed();
		}
		if(ls.contains(null)||ls.contains("0")||ls.contains("")){
			flag = false;
		}
	
		return flag;
		
	}


	
	
	
	/**
	 * ִ�в�ѯ��sql���
	 * */
	public static List<Map<String,Object>> QueryToList(String sql,Object[] parameter){
		
		DBUtil db = new DBUtil();
		List<Map<String,Object>> ls = db.QueryToList(sql, parameter);
		
		return ls;
		
	}
	
	
	/**
	 * ִ����ɾ�ĵ�sql���
	 * */
	public int executeUpdate(String sql,Object[] parameter){
		DBUtil db = new DBUtil();
		int result = db.executeUpdate(sql, parameter);
		
		return result;
		
	}
	
	
	/**
	 * ��������Ϣ
	 * */
	public int insertInventoryInfo(InventoryInfo inventoryInfo){
		
		String sql = "insert into t_inventory_info (EquipmentID,Types,Quantity,ContractNo,OperationCode,OperatingTime,SourceSign,OrderInfoID) values (?,?,?,?,?,?,?,?)";
		Object[] parameter = new Object[]{inventoryInfo.getEquipmentID(),inventoryInfo.getTypes(),inventoryInfo.getQuantity()
				,inventoryInfo.getContractNo(),inventoryInfo.getOperationCode(),inventoryInfo.getOperatingTime(),inventoryInfo.getSourceSign(),inventoryInfo.getOrderInfoID()};
		
		DBUtil db = new DBUtil();
		int result = db.executeUpdate(sql, parameter);
		
		return result;
		
	}
	
	
	/**
	 * �޸Ŀ����Ϣ
	 * */
	public int modifyInventoryInfo(InventoryInfo inventoryInfo){
		String sql = "update t_inventory_info set EquipmentID=?,Types=?,Quantity=?,ContractNo=?,OperationCode=?,OperatingTime=? where ID=?";  
		Object[] parameter = new Object[]{inventoryInfo.getEquipmentID(),inventoryInfo.getTypes(),inventoryInfo.getQuantity()
				,inventoryInfo.getContractNo(),inventoryInfo.getOperationCode(),inventoryInfo.getOperatingTime(),inventoryInfo.getID()};
		
		
		DBUtil db = new DBUtil();
		int result = db.executeUpdate(sql, parameter);
		
		return result;
	}
	
	public String getModel(int id){
		DBUtil db = new DBUtil();
		String sql = "select Model from t_commodity_info where ID=?";
		Object[] param = new Object[]{id};
		List<Map<String,Object>> ls = db.QueryToList(sql, param);
		String model = "";
		if(ls.size()>1){
			model = ls.get(1).get("Model").toString();
		}
		return model;
	}
	
	
}

package com.eoulu.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.map.HashedMap;
import org.junit.Test;

import com.eoulu.commonality.Page;
import com.eoulu.dao.InventoryInfoDao;
import com.eoulu.entity.InventoryInfo;
import com.eoulu.entity.Logistics;
import com.eoulu.entity.OrderInfo;
import com.eoulu.service.InventoryInfoService;
import com.eoulu.util.DBUtil;

/** @author  ���� : zhangkai
 * @date ����ʱ�䣺2017��6��12�� ����10:36:31 
 * @version 1.0  
 * @since  
 * @return  
 */
public class InventoryInfoServiceImpl implements InventoryInfoService {

	/* 
	 * �޲����Ĳ�ѯ��ҳ��Ϣ
	 */
	@Override
	public List<Map<String, Object>> getInventoryByCommon(Page page) {
		String OperatingTime = getNowDate();
		/*String sql="SELECT t_equipment.ID EquipmentID,t_equipment.Model,t_equipment.Remarks,t_supplier.`Name` Supplier,t_equipment.InitialQuantity,t_equipment.InitialQuantity-a.outCounts+b.inCounts InventoryQuantity from t_equipment  "
				+ "LEFT JOIN(select t_inventory_info.EquipmentID ID, sum(t_inventory_info.Quantity) outCounts from t_equipment left join "
				+ "t_inventory_info on t_equipment.ID=t_inventory_info.EquipmentID WHERE t_inventory_info.Types=? and t_inventory_info.OperatingTime<=? GROUP BY t_inventory_info.EquipmentID)a on t_equipment.ID=a.ID "
				+ "LEFT JOIN(select t_inventory_info.EquipmentID ID, sum(t_inventory_info.Quantity) inCounts from t_equipment left join "
				+ "t_inventory_info on t_equipment.ID=t_inventory_info.EquipmentID WHERE t_inventory_info.Types=? and t_inventory_info.OperatingTime<=? GROUP BY t_inventory_info.EquipmentID)b on t_equipment.ID=b.ID "
				+ "LEFT JOIN t_supplier on t_equipment.Supplier=t_supplier.ID  "
				+ "order by InventoryQuantity desc limit ?,?";*/
		String sql="select t_commodity_info.ID EquipmentID,t_commodity_info.Model,t_commodity_info.CommodityName Remarks,"
				+ "t_supplier.`Name` Supplier,"
				+ "t_commodity_info.InitialQuantity,t_commodity_info.InitialQuantity-(case when a.outCounts is null then 0 else a.outCounts end)+(case when b.inCounts is null then 0 else b.inCounts end) InventoryQuantity  "
				+ "from t_order_info left join t_commodity_info on t_order_info.EquipmentModel=t_commodity_info.ID "
				+ "LEFT JOIN (select t_inventory_info.EquipmentID ID, sum(t_inventory_info.Quantity) outCounts from t_commodity_info "
				+ "left join t_inventory_info on t_commodity_info.ID=t_inventory_info.EquipmentID WHERE t_inventory_info.Types=?"
				+ " and t_inventory_info.OperatingTime<= ? and t_inventory_info.OperatingTime>=? GROUP BY t_inventory_info.EquipmentID)a "
				+ "on t_commodity_info.ID=a.ID LEFT JOIN "
				+ "(select t_inventory_info.EquipmentID ID, sum(t_inventory_info.Quantity) inCounts "
				+ "from t_commodity_info left join t_inventory_info on t_commodity_info.ID=t_inventory_info.EquipmentID "
				+ "WHERE t_inventory_info.Types= ? and t_inventory_info.OperatingTime<= ? and t_inventory_info.OperatingTime>=? "
				+ "GROUP BY t_inventory_info.EquipmentID)b on t_commodity_info.ID=b.ID LEFT JOIN "
				+ "t_supplier on t_commodity_info.SupplierID=t_supplier.ID  "
				+ "GROUP BY t_order_info.EquipmentModel order by InventoryQuantity desc  limit ?, ?";
		Object[] parameter = new Object[]{2,OperatingTime,"2017-07-01",1,OperatingTime,"2017-07-01",(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		InventoryInfoDao inventoryInfoDao = new InventoryInfoDao();
		List<Map<String,Object>> ls = inventoryInfoDao.QueryToList(sql, parameter);

		//ls = resultToStatisticsInventory(ls);
		return ls;
	}

	/* 
	 * ��ȡ�����豸������
	 */
	@Override
	public int getAllEquipmentCounts() {
		/*String sql = "select count(ID) counts from t_equipment where t_equipment.ID in (select t_order_info.EquipmentModel from t_order_info group by t_order_info.EquipmentModel)";
		String sql = "select count(ID) counts from t_equipment where t_equipment.ID in (select t_order_info.EquipmentModel from t_order_info group by t_order_info.EquipmentModel)";*/
		String sql = "select count(a.id) counts from(select t_order_info.EquipmentModel id from t_order_info left join t_commodity_info on t_order_info.EquipmentModel=t_commodity_info.ID group by t_order_info.EquipmentModel)a";


		List<Map<String,Object>> ls = new InventoryInfoDao().QueryToList(sql, new Object[0]);


		int result = 0;

		if(ls.size()>1)
			result = Integer.parseInt(ls.get(1).get("counts").toString());

		return result;
	}

	/* 
	 * ���ݲ�ѯ�����Ĳ�Ʒ��Ϣ(List<Map<String,Object>>)��ȥ������ͳ�Ƴ���������
	 */
	@Override
	public List<Map<String, Object>> resultToStatisticsInventory(String conditon,Map<String,Object> parameter, List<Map<String, Object>> ls) {


		List<Map<String,Object>> midLs = ls;
		int length = ls.size();
	//	String sql = "select sum(Quantity) counts from t_inventory_info where EquipmentID=? and Types=? and OperatingTime<=?";
		String sql="select (case when sum(Quantity) is null then 0 else sum(Quantity) end)-(case when tb.outcounts is null then 0 else tb.outcounts end ) counts from t_inventory_info,(select sum(Quantity) outcounts from t_inventory_info where EquipmentID= ? and Types= ? and OperatingTime<= ? and OperatingTime>= ?  )tb where EquipmentID=? and Types= ? and OperatingTime<= ? and OperatingTime>= ?  ";
		//���㵱ǰ����֮ǰ�Ŀ������ͳ��      ��ʼ
		String OperatingTime = null;
		if(conditon.equals("库存")){
			OperatingTime = parameter.get("1").toString(); 
		}else{
			OperatingTime = getNowDate();
		}
		for(int i=1; i<length; i++){
			int equipmentID = Integer.parseInt(ls.get(i).get("EquipmentID").toString());
			int initialQuantity = Integer.parseInt(ls.get(i).get("InitialQuantity").toString());
			

			Object[] parameter1 = new Object[]{equipmentID,2,OperatingTime,"2017-07-01",equipmentID,1,OperatingTime,"2017-07-01"};
			//Object[] parameter2 = new Object[]{equipmentID,2,OperatingTime};

			InventoryInfoDao inventoryInfoDao = new InventoryInfoDao();
			List<Map<String,Object>> ls1 = inventoryInfoDao.QueryToList(sql, parameter1);
			//List<Map<String,Object>> ls2 = inventoryInfoDao.QueryToList(sql, parameter2);

			//�ܵ��������-�ܵĳ�������
			int inCounts = Integer.parseInt(ls1.get(1).get("counts").toString().equals("--")?"0":ls1.get(1).get("counts").toString());
			//�ܵĳ�������
		//	int outCounts = Integer.parseInt(ls2.get(1).get("counts").toString().equals("--")?"0":ls2.get(1).get("counts").toString());

			int inventoryQuantity = initialQuantity+inCounts;


			ls.get(i).put("InventoryQuantity", inventoryQuantity);
		}
		//���㵱ǰ����֮ǰ�Ŀ������ͳ��      ����

		return ls;
	}

	/*
	 * ��ȡ��ǰʱ����ַ��� yyyy:mm:dd
	 * */
	public static String getNowDate(){

		Calendar cale = Calendar.getInstance();
		int year = cale.get(Calendar.YEAR);
		int month = cale.get(Calendar.MONTH)+1;
		int day = cale.get(Calendar.DAY_OF_MONTH);
		String time = year+"-"+(month>9?month:"0"+month)+"-"+(day>9?day:"0"+day);

		return time;
	}

	/*
	 * �޸��ڳ�����
	 * */
	@Override
	public boolean modifyInitQuantity(int equipmentID,int quantity){
		boolean flag = false;
		InventoryInfoDao inventoryInfoDao = new InventoryInfoDao();
		String sql = "update t_commodity set InitialQuantity=? where ID=?";
		Object[] parameter = new Object[]{quantity,equipmentID};
		int result = inventoryInfoDao.executeUpdate(sql, parameter);
		
		flag = result>0?true:false;

		return flag;
	}


	/* 
	 * ��ȡ������Ϣ
	 */
	@Override
	public List<Map<String, Object>> getInInventoryInfo(int equipmentID,int type) {
		String date = "2017-07-01";
		String date2 = getNowDate();
		String sql = "select * from t_inventory_info where EquipmentID=? and Types=? and OperatingTime<=? and OperatingTime>='"+date+"'";
		Object[] parameter = new Object[]{equipmentID,type,date2};


		List<Map<String,Object>> ls = new InventoryInfoDao().QueryToList(sql, parameter);
		for(int i=1;i<ls.size();i++){
			String ContractNo=(String) ls.get(i).get("ContractNo");
			String sql1="select t_quotes.RMBQuotes,t_quotes.USDQuotes from t_quotes "
					+ "where OrderID=( select ID from t_order where ContractNo = ? )";
			Object[] parameter1 = new Object[]{ContractNo};
			List<Map<String,Object>> ls1 = new InventoryInfoDao().QueryToList(sql1, parameter1);
			if(ls1.size()>=2){
				String USDQuotes = ls1.get(1).get("USDQuotes").toString().equals("--")?"0":ls1.get(1).get("USDQuotes").toString();
				String RMBQuotes = ls1.get(1).get("RMBQuotes").toString().equals("--")?"0":ls1.get(1).get("RMBQuotes").toString();
				ls.get(i).put("USDQuotes", USDQuotes);
				ls.get(i).put("RMBQuotes", RMBQuotes);
			}else{
				ls.get(i).put("USDQuotes", 0);
				ls.get(i).put("RMBQuotes", 0);
			}
		}
		return ls;
	}
	/* 
	 * ��ȡ�����Ϣ
	 */
	@Override
	public List<Map<String, Object>> getInInventoryInfo1(int equipmentID,int type,String Model) {
		String date = "2017-07-01";
		String date2 = getNowDate();
		String sql = "select * from t_inventory_info where EquipmentID=? and Types=? and OperatingTime<=? and OperatingTime>='"+date+"'";
		Object[] parameter = new Object[]{equipmentID,type,date2};

		List<Map<String,Object>> ls = new InventoryInfoDao().QueryToList(sql, parameter);
		//设置PO金额
		for(int i=1;i<ls.size();i++){
			String PONO=(String) ls.get(i).get("ContractNo");
			String sql1="select t_order_info.ID, t_logistics.POAmount,t_logistics.RMBPOAmount from t_logistics  "
					+ "left join t_order_info on t_order_info.ID=t_logistics.OrderInfoID "
					+ "left join t_commodity_info on t_commodity_info.ID=t_order_info.EquipmentModel  "
					+ "where t_logistics.PONO=? and  t_commodity_info.Model=?";
			Object[] parameter1 = new Object[]{PONO,Model};
			List<Map<String,Object>> ls1 = new InventoryInfoDao().QueryToList(sql1, parameter1);
		
			if(ls1.size()>=2){
				String POAmount = ls1.get(1).get("POAmount").toString().equals("--")?"0":ls1.get(1).get("POAmount").toString();
				String RMBPOAmount = ls1.get(1).get("RMBPOAmount").toString().equals("--")?"0":ls1.get(1).get("RMBPOAmount").toString();
				ls.get(i).put("POAmount", POAmount);
				ls.get(i).put("RMBPOAmount", RMBPOAmount);
			}else{
				ls.get(i).put("POAmount", 0);
				ls.get(i).put("RMBPOAmount", 0);
			}
			
		}
		return ls;
	}
	

	/* 
	 * ��������Ϣ
	 */
	@Override
	public boolean insertInInventory(InventoryInfo inventoryInfo) {

		int result = new InventoryInfoDao().insertInventoryInfo(inventoryInfo);

		return result>=1?true:false;
	}

	/* 
	 * �޸Ŀ����Ϣ
	 */
	@Override
	public boolean modifyInInventory(InventoryInfo inventoryInfo) {
		int result = new InventoryInfoDao().modifyInventoryInfo(inventoryInfo);

		return result>=1?true:false;
	}

	/* 
	 * ��һ��ѯ�����Ϣ
	 */
	@Override
	public List<Map<String, Object>> QueryInventoryInfoByOne(String conditon,Map<String,Object> parameter,Page page) {

		List<Map<String,Object>> ls = null;
		//查出所有的设备信息的sql
		String sql = productSqlByQueryOne(conditon);
		//参数拼接
		Object[] para = productParameterByQueryOne(conditon,parameter,page);

		DBUtil db = new DBUtil();
		//查询出数据
		ls = db.QueryToList(sql, para);
		//统计库存结果
		ls = resultToStatisticsInventory(conditon,parameter,ls);
		return ls;
	}
	
	
	/*
	 * ��һ��ѯ������
	 * */
	public int QueryInventoryInfoByOneCounts(String condition,Map<String,Object> parameter){
		String sql = "select count(t_commodity_info.ID) counts from t_commodity_info ";

		String remainingSql = "";
		switch(condition){
		
		case "型号":remainingSql = " where t_commodity_info.Model like ?";break;
		case "供应商":remainingSql = " where t_commodity_info.SupplierID  in (select t_supplier.ID from t_supplier where t_supplier.`Name` like ?)";break;
		case "入库时间":remainingSql = " where t_commodity_info.ID in (select t_inventory_info.EquipmentID from t_inventory_info where t_inventory_info.Types=1 and t_inventory_info.OperatingTime between ? and ?) ";break;
		case "PO号":remainingSql = " where t_commodity_info.ID in (select t_inventory_info.EquipmentID from t_inventory_info where t_inventory_info.Types=1 and t_inventory_info.ContractNo like ?)";break;
		case "入库编码":remainingSql = " where t_commodity_info.ID in (select t_inventory_info.EquipmentID from t_inventory_info where t_inventory_info.Types=1 and t_inventory_info.OperationCode like ?)";break;
		case "出库时间":remainingSql = " where t_commodity_info.ID in (select t_inventory_info.EquipmentID from t_inventory_info where t_inventory_info.Types=2 and t_inventory_info.OperatingTime between ? and ?) ";break;
		case "合同号":remainingSql = " where t_commodity_info.ID in (select t_inventory_info.EquipmentID from t_inventory_info where t_inventory_info.Types=2 and t_inventory_info.ContractNo like ?)";break;
		case "出库编码":remainingSql = " where t_commodity_info.ID in (select t_inventory_info.EquipmentID from t_inventory_info where t_inventory_info.Types=2 and t_inventory_info.OperationCode like ?)";break;
		case "库存":remainingSql = " where 1=1";break;
		}
		sql += remainingSql+"  and t_commodity_info.ID in (select t_order_info.EquipmentModel from t_order_info group by t_order_info.EquipmentModel)";
							// and t_equipment.ID in (select t_order_info.EquipmentModel from t_order_info group by t_order_info.EquipmentModel)";

		Object[] para = null;


		switch(condition){
		case "型号":para = new Object[]{"%"+parameter.get("1").toString()+"%"};break;
		case "供应商":para = new Object[]{"%"+parameter.get("1")+"%"};break;
		case "入库时间":para = new Object[]{parameter.get("1")==""?"1970-01-01":parameter.get("1"),parameter.get("2")==""?"2050-01-01":parameter.get("2")};break;
		case "PO号":para = new Object[]{"%"+parameter.get("1")+"%"};break;
		case "入库编码":para = new Object[]{"%"+parameter.get("1")+"%"};break;
		case "出库时间":para = new Object[]{parameter.get("1")==""?"1970-01-01":parameter.get("1"),parameter.get("2")==""?"2050-01-01":parameter.get("2")};break;
		case "合同号":para = new Object[]{"%"+parameter.get("1")+"%"};break;
		case "出库编码":para = new Object[]{"%"+parameter.get("1")+"%"};break;
		//case "库存":para = new Object[]{"%"+parameter.get("1")+"%"};break;
		}
		
		
		List<Map<String,Object>> ls = new DBUtil().QueryToList(sql, para);
		
		int result = 0;
		if(ls.size()>1)
			result = Integer.parseInt(ls.get(1).get("counts").toString());
		
		
		return result;
		
		
	}
	
	

	/*
	 * ��һ��ѯ�����ݲ�ѯ��������sql���
	 * */
	public String productSqlByQueryOne(String condition){

		String sql = "select t_commodity_info.ID EquipmentID,t_commodity_info.Model,t_commodity_info.CommodityName Remarks,t_supplier.`Name` Supplier,t_commodity_info.InitialQuantity from t_commodity_info LEFT JOIN t_supplier on t_commodity_info.SupplierID=t_supplier.ID ";

		String remainingSql = "";
		switch(condition){
		case "型号":remainingSql = " where t_commodity_info.Model like ?";break;
		case "供应商":remainingSql = " where t_commodity_info.SupplierID  in (select t_supplier.ID from t_supplier where t_supplier.`Name` like ?)";break;
		case "入库时间":remainingSql = " where t_commodity_info.ID in (select t_inventory_info.EquipmentID from t_inventory_info where t_inventory_info.Types=1 and t_inventory_info.OperatingTime between ? and ?) ";break;
		case "PO号":remainingSql = " where t_commodity_info.ID in (select t_inventory_info.EquipmentID from t_inventory_info where t_inventory_info.Types=1 and t_inventory_info.ContractNo like ?)";break;
		case "入库编码":remainingSql = " where t_commodity_info.ID in (select t_inventory_info.EquipmentID from t_inventory_info where t_inventory_info.Types=1 and t_inventory_info.OperationCode like ?)";break;
		case "出库时间":remainingSql = " where t_commodity_info.ID in (select t_inventory_info.EquipmentID from t_inventory_info where t_inventory_info.Types=2 and t_inventory_info.OperatingTime between ? and ?) ";break;
		case "合同号":remainingSql = " where t_commodity_info.ID in (select t_inventory_info.EquipmentID from t_inventory_info where t_inventory_info.Types=2 and t_inventory_info.ContractNo like ?)";break;
		case "出库编码":remainingSql = " where t_commodity_info.ID in (select t_inventory_info.EquipmentID from t_inventory_info where t_inventory_info.Types=2 and t_inventory_info.OperationCode like ?)";break;
		case "出库":remainingSql = " where 1=1";break;
		}
		sql += remainingSql+" and t_commodity_info.ID in (select t_order_info.EquipmentModel from t_order_info group by t_order_info.EquipmentModel)";
		sql += "limit ?,?";
		return sql;

	}


	/*
	 * ��һ��ѯ�����ݲ�ѯ�������ɲ���
	 * */
	public Object[] productParameterByQueryOne(String condition,Map<String,Object> parameter,Page page){

		Object[] para = null;


		switch(condition){
		case "型号":para = new Object[]{"%"+parameter.get("1").toString()+"%",(page.getCurrentPage()-1)*page.getRows(),page.getRows()};break;
		case "供应商":para = new Object[]{"%"+parameter.get("1")+"%",(page.getCurrentPage()-1)*page.getRows(),page.getRows()};break;
		case "入库时间":para = new Object[]{parameter.get("1")==""?"1970-01-01":parameter.get("1"),parameter.get("2")==""?"2050-01-01":parameter.get("2"),(page.getCurrentPage()-1)*page.getRows(),page.getRows()};break;
		case "PO号":para = new Object[]{"%"+parameter.get("1")+"%",(page.getCurrentPage()-1)*page.getRows(),page.getRows()};break;
		case "入库编码":para = new Object[]{"%"+parameter.get("1")+"%",(page.getCurrentPage()-1)*page.getRows(),page.getRows()};break;
		case "出库时间":para = new Object[]{parameter.get("1")==""?"1970-01-01":parameter.get("1"),parameter.get("2")==""?"2050-01-01":parameter.get("2"),(page.getCurrentPage()-1)*page.getRows(),page.getRows()};break;
		case "合同号":para = new Object[]{"%"+parameter.get("1")+"%",(page.getCurrentPage()-1)*page.getRows(),page.getRows()};break;
		case "出库编码":para = new Object[]{"%"+parameter.get("1")+"%",(page.getCurrentPage()-1)*page.getRows(),page.getRows()};break;
		case "库存":para = new Object[]{"库存",(page.getCurrentPage()-1)*page.getRows(),page.getRows()};break;
		}


		return para;

	}

	/*
	 * ��������ID�õ���ͬ�ţ�PO�ţ��豸ID
	 * */
	@Override
	public Map<String, Object> getInventoryByOrderID(int orderInfoID) {
		Map<String,Object> map = new HashMap<String,Object>();

		//��ȡ��ͬ��
		String getContractNo = "select t_order.ContractNo from t_order where t_order.ID=(select t_order_info.OrderID from t_order_info where t_order_info.ID=?)";
		Object[] getContractNoPara = new Object[]{orderInfoID};

		String getPO = "select t_logistics.PONO from t_logistics where t_logistics.OrderInfoID=?";
		Object[] getPOPara = new Object[]{orderInfoID};




		List<Map<String,Object>> ls1 = new DBUtil().QueryToList(getContractNo, getContractNoPara);
		List<Map<String,Object>> ls2 = new DBUtil().QueryToList(getPO, getPOPara);


		String getEquipment = "select t_order_info.EquipmentModel,SUM(Number) Number from t_order_info where t_order_info.ID in (select t_logistics.OrderInfoID from t_logistics where t_logistics.PONO=?) and t_order_info.EquipmentModel=(select t_order_info.EquipmentModel from t_order_info where t_order_info.ID=?) GROUP BY t_order_info.EquipmentModel";
		Object[] getEquipmentPara = new Object[]{ls2.get(1).get("PONO"),orderInfoID};
		List<Map<String,Object>> ls3 = new DBUtil().QueryToList(getEquipment, getEquipmentPara);
		System.out.println(ls2.get(1).get("PONO")+"-----"+orderInfoID);
System.out.println("test:"+ls3);
		map.put("合同号", ls1.get(1).get("ContractNo"));
		map.put("PO号", ls2.get(1).get("PONO"));
		map.put("设备ID", ls3.get(1).get("EquipmentModel"));
		map.put("数量", ls3.get(1).get("Number"));


		return map;
	}



	/*
	 * ��������ID�Զ���������Ϣ
	 * */
	@Override
	public int importInInventoryInfo(int orderInfoID,Logistics logistics,OrderInfo orderInfo) {

		Map<String,Object> map = getInventoryByOrderID(orderInfoID);

		inInventory(map,logistics,orderInfo);

		return 0;
	}


	//	public int importOutventoryInfo(int orderInfoID,Logistics logistics,OrderInfo orderInfo) {
	//
	//		Map<String,Object> map = getInventoryByOrderID(orderInfoID);
	//
	//		outInventory(map,logistics,orderInfo);
	//
	//		return 0;
	//	}


	/*
	 * �����Ϣ����
	 * */
	public void inInventory(Map<String,Object> map,Logistics logistics,OrderInfo orderInfo){
		String checkSql = "select * from t_inventory_info where Types=1 and ContractNo=? and EquipmentID=?";
		Object[] parameter = new Object[]{map.get("PO号"),map.get("设备ID")};

		List<Map<String,Object>> checkLS = new DBUtil().QueryToList(checkSql, parameter);

		InventoryInfo inventoryInfo = new InventoryInfo();

		try{inventoryInfo.setEquipmentID(Integer.parseInt(map.get("设备ID").toString()));}catch(Exception e){}
		try{inventoryInfo.setTypes(1);}catch(Exception e){}
		try{inventoryInfo.setQuantity(Integer.parseInt(map.get("数量").toString()));}catch(Exception e){}
		try{inventoryInfo.setContractNo(map.get("PO号").toString());}catch(Exception e){}
		try{inventoryInfo.setOperationCode("");}catch(Exception e){}
		try{inventoryInfo.setOperatingTime(logistics.getFactoryShipment());}catch(Exception e){}
		try{inventoryInfo.setSourceSign(1);}catch(Exception e){}
		System.out.println("size:"+checkLS.size());
		if(checkLS.size()>1){
			System.out.println("1:"+checkLS.get(1));
			System.out.println("0:"+checkLS.get(0));
			try{inventoryInfo.setID(Integer.parseInt(checkLS.get(1).get("ID").toString()));}catch(Exception e){}
			modifyInInventory(inventoryInfo);

		}else{
			//System.out.println("0:"+checkLS.get(0));
			insertInInventory(inventoryInfo);
		}
	}


	/*
	 * ����OrderID����������Ϣ
	 * */
	@Override
	public int importOutInventoryInfo(int orderInfoID, Logistics logistics, OrderInfo orderInfo) {
		Map<String,Object> map = getInventoryByOrderID(orderInfoID);

		outInventory(map,logistics,orderInfo,orderInfoID);

		return 0;

	}



	/*
	 * ������Ϣ����
	 * */
	public void outInventory(Map<String,Object> map,Logistics logistics,OrderInfo orderInfo,int orderInfoID){
		String checkSql = "select * from t_inventory_info where Types=2 and ContractNo=? and EquipmentID=?";
		Object[] parameter = new Object[]{map.get("合同号"),map.get("设备ID")};
		List<Map<String,Object>> checkLS = new DBUtil().QueryToList(checkSql, parameter);
		
		String getQunatly = "select Number from t_order_info where t_order_info.ID=?";
		Object[] pa = new Object[]{orderInfoID};
		
		int quantity =Integer.parseInt(new DBUtil().QueryToList(getQunatly, pa).get(1).get("Number").toString());
		

		InventoryInfo inventoryInfo = new InventoryInfo();

		try{inventoryInfo.setEquipmentID(Integer.parseInt(map.get("设备ID").toString()));}catch(Exception e){}
		try{inventoryInfo.setTypes(2);}catch(Exception e){}
		try{inventoryInfo.setQuantity(quantity);}catch(Exception e){}
		try{inventoryInfo.setContractNo(map.get("合同号").toString());}catch(Exception e){}
		try{inventoryInfo.setOperationCode("");}catch(Exception e){}
		try{inventoryInfo.setOperatingTime(orderInfo.getDate());}catch(Exception e){}
		try{inventoryInfo.setSourceSign(1);}catch(Exception e){}
		try{inventoryInfo.setOrderInfoID(orderInfoID);}catch(Exception e){}


		if(checkLS.size()>1){

			try{inventoryInfo.setID(Integer.parseInt(checkLS.get(1).get("ID").toString()));}catch(Exception e){}
			


			modifyInInventory(inventoryInfo);


		}else{
			insertInInventory(inventoryInfo);
		}


	}



	/* 
	 * ɾ�������Ϣ
	 */
	@Override
	public boolean deleteInInventory(InventoryInfo inventoryInfo) {
		boolean flag = false;
		
		String sql = "delete from t_inventory_info where ID=? and SourceSign=2";
		Object[] parameter = new Object[]{inventoryInfo.getID()};
		
		
		int result = new DBUtil().executeUpdate(sql, parameter);
		
		flag = result>0?true:false;
		
		return flag;
	}
	
	
	@Test
	public void test(){
		Page page = new Page();
		page.setCurrentPage(1);
		page.setRows(10);

		Map<String,Object> map = new HashedMap<String,Object>();
		map.put("1", "0");

	}

	@Override
	public Map<String, List<Map<String, Object>>> getinventoryInfoByEquipment(List<Map<String, Object>> equipmentList,String startTime,String endTime) {
		String sql = "select t_inventory_info.Types,t_inventory_info.Quantity,t_inventory_info.OperationCode ,t_inventory_info.ContractNo,"
				+ "t_inventory_info.OperatingTime,t_quotes.RMBQuotes,t_quotes.USDQuotes "
				+ "from t_inventory_info LEFT JOIN t_quotes on  t_quotes.OrderID = ( select ID from t_order where t_order.ContractNo =t_inventory_info.ContractNo )"
				+ " where t_inventory_info.EquipmentID=? and t_inventory_info.OperatingTime>=? and t_inventory_info.OperatingTime<=? ;";
		Map<String, List<Map<String, Object>>> inventoryInfoMap = new HashMap<String, List<Map<String, Object>>>();
		DBUtil db = new DBUtil();
		for(int i =0;i<equipmentList.size();i++){
			//计算第一个库存
			Object[] parameter1 = new Object[]{equipmentList.get(i).get("ID"),"2017-07-01",startTime};
			ResultSet rs1 = db.Query(sql, parameter1);
			try {
				int n = 0;
				while(rs1.next()){
						if(rs1.getObject(1).toString().equals("1"))
						{
							n+=Integer.parseInt(rs1.getObject(2).toString());
						}else{
							n-=Integer.parseInt(rs1.getObject(2).toString());
						}
					}
				String InitialQuantity = equipmentList.get(i).get("InitialQuantity").toString()==null?"0":equipmentList.get(i).get("InitialQuantity").toString();
				equipmentList.get(i).put("Inventory1",Integer.parseInt(InitialQuantity)+n);
				} catch (SQLException e) {
					e.printStackTrace();
				}

			//计算第二个库存
			Object[] parameter = new Object[]{equipmentList.get(i).get("ID"),startTime,endTime};
			ResultSet rs = db.Query(sql, parameter);
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			try {
				int n = 0;
				while(rs.next()){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("Types", rs.getObject(1));
					map.put("Quantity", rs.getObject(2));
					if(rs.getObject(1).toString().equals("1"))
					{
						//在入库的地方改
						String sql1 = "select t_order_info.ID, t_logistics.POAmount,"
								+ "t_logistics.RMBPOAmount from t_logistics "
								+ "left join t_order_info on t_order_info.ID=t_logistics.OrderInfoID "
								+ "left join t_commodity_info on t_commodity_info.ID=t_order_info.EquipmentModel where t_logistics.PONO=? and  t_commodity_info.Model=?";
						ResultSet rs3 = db.Query(sql1, new Object[]{rs.getObject(4),equipmentList.get(i).get("Model")});
						while(rs3.next()){
							map.put("RMBQuotes", rs3.getObject(2));
							map.put("USDQuotes", rs3.getObject(3));
						}
						n+=Integer.parseInt(rs.getObject(2).toString());
					}else{
						n-=Integer.parseInt(rs.getObject(2).toString());
						map.put("RMBQuotes", rs.getObject(6));
						map.put("USDQuotes", rs.getObject(7));
					}
					map.put("OperationCode", rs.getObject(3));
					map.put("ContractNo", rs.getObject(4));
					map.put("OperatingTime", rs.getObject(5));
					
					list.add(map);
				}
				String Inventory1 = equipmentList.get(i).get("Inventory1").toString()==null?equipmentList.get(i).get("InitialQuantity").toString()==null?"0":equipmentList.get(i).get("InitialQuantity").toString():equipmentList.get(i).get("Inventory1").toString();
				equipmentList.get(i).put("Inventory2",Integer.parseInt(Inventory1)+n);
				inventoryInfoMap.put(equipmentList.get(i).get("ID").toString(), list);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		db.closed();
		return inventoryInfoMap;
	}

	@Override
	public boolean addInventoryInfo(HttpServletRequest request) {
		
		return InventoryInfoDao.InsertInventoryInfo(request);
	}

	@Override
	public String getModel(int id) {
		return new InventoryInfoDao().getModel(id);
	}



}

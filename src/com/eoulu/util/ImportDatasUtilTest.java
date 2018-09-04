package com.eoulu.util;

import java.util.List;
import java.util.Map;

public class ImportDatasUtilTest {

	public static String exchangeSupplier() {// 合并供应商
		DBUtil db = new DBUtil();
		long time1 = System.currentTimeMillis();
		System.out.println(time1);
		String sql = "SELECT DISTINCT Supplier FROM t_commodity_info";
		List<Map<String, Object>> ls = db.QueryToList(sql, null);
		String sql2 = "SELECT ID FROM t_supplier WHERE `Name`=?";
		Object[] param2 = new Object[1];
		String sql3 = "insert into t_supplier (`Name`) VALUES (?)";
		String sql4 = "UPDATE t_commodity_info SET Supplier=? WHERE Supplier=?";
		Object[] param3 = new Object[2];
		for (int i = 1; i < ls.size(); i++) {
			DBUtil db2 = new DBUtil();
			String supplier = ls.get(i).get("Supplier").toString().trim();
			System.out.println(supplier);
			param2[0] = supplier;
			List<Map<String, Object>> ls2 = db2.QueryToList(sql2, param2);
			if (ls2.size() < 2) {
				DBUtil db3 = new DBUtil();
				int count = db3.executeUpdate(sql3, param2);
				if (count != 1) {
					System.out.println(supplier);
					break;
				}
			}
			DBUtil db3 = new DBUtil();
			ls2 = db3.QueryToList(sql2, param2);
			int id = Integer.parseInt(ls2.get(1).get("ID").toString());
			param3[0] = id;
			param3[1] = supplier;
			DBUtil db4 = new DBUtil();
			int count = db4.executeUpdate(sql4, param3);
			if (count < 1) {
				System.out.println(supplier + "===");
				break;
			}

		}
		long time2 = System.currentTimeMillis();
		System.out.println(time2 - time1);
		return "合并供应商表成功！";
	}
/*
	public static String exchangeData() {// 合并重复的数据
		DBUtil db = new DBUtil();
		String sql = "SELECT ID,Model,ItemCode,InitialQuantity,Supplier FROM t_equipment "
				+ "WHERE Model IN (SELECT DISTINCT Model FROM t_commodity_info) ";
		List<Map<String, Object>> ls = db.QueryToList(sql, null);
		System.out.println("找出重复的型号");
		String sql2 = "SELECT MIN(ID) FROM t_commodity_info WHERE Model=?";
		Object[] param2 = new Object[1];
		String sql3 = "UPDATE t_commodity_info SET ItemCode=?,InitialQuantity=?,EquipmentID=?,SupplierID=? WHERE ID=?";
		Object[] param3 = new Object[5];
		for (int i = 1; i < ls.size(); i++) {
			DBUtil db2 = new DBUtil();
			param2[0] = ls.get(i).get("Model").toString();
			int EquipmentID = Integer.parseInt(ls.get(i).get("ID").toString());
			String ItemCode = ls.get(i).get("ItemCode").toString();
			int InitialQuantity = Integer.parseInt(ls.get(i).get("InitialQuantity").toString());
			int SupplierID = 0;
			if (!ls.get(i).get("Supplier").toString().equals("--")
					&& !ls.get(i).get("Supplier").toString().equals("0")) {
				SupplierID = Integer.parseInt(ls.get(i).get("Supplier").toString());
			} else {
				SupplierID = 15;
			}
			List<Map<String, Object>> ls2 = db2.QueryToList(sql2, param2);
			if (ls2.size() > 1) {
				DBUtil db3 = new DBUtil();
				int id = Integer.parseInt(ls2.get(1).get("MIN(ID)").toString());
				System.out.println("根据型号取出t_commodity_info中最小ID");
				param3[0] = ItemCode;
				param3[1] = InitialQuantity;
				param3[2] = EquipmentID;
				param3[3] = SupplierID;
				param3[4] = id;
				// 根据型号将t_equipment中的ItemCode、InitialQuantity、Supplier、
				// EquipmentID取出来，根据最小ID更新至t_commodity_info的对应字段；
				int count = db3.executeUpdate(sql3, param3);
				if (count != 1) {
					System.out.println(id + "---" + param2[0]);
					break;
				}
			}
		}
		System.out.println("遍历结束！");
		return "合并型号表重复的数据";
	}

	public static String exchangeData2() {// 合并不重复的数据
		long time1 = System.currentTimeMillis();
		String sql = "SELECT Model,Remarks,EquipmentUnit,DeliveryTime,SourceArea,ItemCode,CommodityCategory,Supplier,InitialQuantity,ID FROM t_equipment"
				+ " WHERE Model Not IN (SELECT DISTINCT Model FROM t_commodity_info)";
		Object[] param = new Object[11];
		String sql2 = "insert into t_commodity_info (Model,CommodityName,Unit,DeliveryPeriod,"
				+ "ProducingArea,ItemCode,ProductCategory,Supplier,SupplierID,InitialQuantity,"
				+ "EquipmentID) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		DBUtil db = new DBUtil();
		List<Map<String, Object>> ls = db.QueryToList(sql, null);
		int c = 0;
		for (int i = 1; i < ls.size(); i++) {
			DBUtil db2 = new DBUtil();
			String Model = ls.get(i).get("Model").toString();
			String CommodityName = ls.get(i).get("Remarks").toString();
			String Unit = ls.get(i).get("EquipmentUnit").toString();
			String DeliveryPeriod = ls.get(i).get("DeliveryTime").toString();
			String ProducingArea = ls.get(i).get("SourceArea").toString();
			String ItemCode = ls.get(i).get("ItemCode").toString();
			String ProductCategory = ls.get(i).get("CommodityCategory").toString();
			String Supplier = ls.get(i).get("Supplier").toString();
			if (Supplier.equals("--") || Supplier.equals("0")) {
				Supplier = "15";
			}
			String InitialQuantity = ls.get(i).get("InitialQuantity").toString();
			String EquipmentID = ls.get(i).get("ID").toString();
			param[0] = Model;
			param[1] = CommodityName;
			param[2] = Unit;
			param[3] = DeliveryPeriod;
			param[4] = ProducingArea;
			param[5] = ItemCode;
			param[6] = ProductCategory;
			param[7] = Supplier;

			param[8] = Integer.parseInt(Supplier);
			param[9] = Integer.parseInt(InitialQuantity);
			param[10] = Integer.parseInt(EquipmentID);
			int count = db2.executeUpdate(sql2, param);
			if (count < 1) {
				System.out.println(Model);
				break;
			}
			c++;
		}
		System.out.println(System.currentTimeMillis() - time1);
		System.out.println("总数==" + c);
		return "合并产品型号表结束！";
	}
*/
	public static String Exist(String model){
		String sql = "SELECT ID from t_commodity_info WHERE Model=?";
		DBUtil sb = new DBUtil();
		List<Map<String,Object>> ls = sb.QueryToList(sql, new Object[]{model});
		String id = ls.size()>1?ls.get(1).get("ID").toString():"false";
		return id;
	}
	
	public static void exchangeCommodity(){
		long time = System.currentTimeMillis();
		String sql = "SELECT ID EquipmentID,Model,ItemCode,InitialQuantity,Supplier SupplierID,"
				+ "Remarks CommodityName,EquipmentUnit Unit,DeliveryTime DeliveryPeriod,SourceArea ProducingArea,"
				+ "CommodityCategory ProductCategory FROM t_equipment ";
		String sql2 = "UPDATE t_commodity_info SET ItemCode=?,InitialQuantity=?,EquipmentID=?,SupplierID=? WHERE ID=?";
		String sql3 = "insert into t_commodity_info (Model,CommodityName,Unit,DeliveryPeriod,"
				+ "ProducingArea,ItemCode,ProductCategory,SupplierID,InitialQuantity,"
				+ "EquipmentID) VALUES (?,?,?,?,?,?,?,?,?,?)";
		List<Map<String,Object>> list = new DBUtil().QueryToList(sql, null);
		System.out.println("list的长度"+(list.size()-1));
 		for(int i=1;i<list.size();i++){
 			int EquipmentID = Integer.parseInt(list.get(i).get("EquipmentID").toString()); 
 			String Model = list.get(i).get("Model").toString();
			String CommodityName = list.get(i).get("CommodityName").toString();
			String Unit = list.get(i).get("Unit").toString();
			String DeliveryPeriod = list.get(i).get("DeliveryPeriod").toString();
			String ProducingArea = list.get(i).get("ProducingArea").toString();
			String ItemCode = list.get(i).get("ItemCode").toString();
			String ProductCategory = list.get(i).get("ProductCategory").toString();
			int SupplierID = Integer.parseInt(list.get(i).get("SupplierID")=="--"?"15":list.get(i).get("SupplierID").toString());
			int InitialQuantity = Integer.parseInt(list.get(i).get("InitialQuantity")=="0"?"15":list.get(i).get("InitialQuantity").toString());
			String ID  = Exist(Model);
			if(!ID.equals("false")){
				int id = new DBUtil().executeUpdate(sql2, new Object[]{ItemCode,InitialQuantity,EquipmentID,SupplierID,Integer.parseInt(ID)});
				if(id<1){
					System.out.println("更新失败！"+id);
					break;
				}
			}else{
				Object[] param = new Object[]{Model,CommodityName,Unit,DeliveryPeriod,ProducingArea,ItemCode,ProductCategory,SupplierID,InitialQuantity,EquipmentID};
				int count = new DBUtil().executeUpdate(sql3, param);
				if(count<1){
					System.out.println("添加失败！"+Model);
					break;
				}
			}
			
 		}
		System.out.println((System.currentTimeMillis()-time)+"jieshu!");
		
	}
	
	public static String exchangeOrderInfo() {// 订单信息表
		DBUtil db = new DBUtil();
		String sql = "SELECT ID,EquipmentModel from t_order_info WHERE EquipmentModel<>0 AND EquipmentModel<>''";
		System.out.println("找出订单信息中所有有型号的");
		List<Map<String, Object>> ls = db.QueryToList(sql, null);
		String sql2 = "SELECT ID from t_commodity_info WHERE EquipmentID=?";
		System.out.println("根据订单信息中的EquipmentID找到对应的商品管理ID");
		Object[] param2 = new Object[1];
		String sql3 = "UPDATE t_order_info SET EquipmentModel=? WHERE ID=?";
		System.out.println("根据订单信息表的ID修改外键EquipmentModel");
		Object[] param3 = new Object[2];
		int c = 0;
		for (int i = 1; i < ls.size(); i++) {
			int InfoID = Integer.parseInt(ls.get(i).get("ID").toString());
			int EquipmentModel = Integer.parseInt(ls.get(i).get("EquipmentModel").toString());
			param2[0] = EquipmentModel;
			DBUtil db2 = new DBUtil();
			List<Map<String, Object>> ls2 = db2.QueryToList(sql2, param2);
			System.out.println(EquipmentModel);
			int CommodityID = Integer.parseInt(ls2.get(1).get("ID").toString());
			param3[0] = CommodityID;
			param3[1] = InfoID;
			DBUtil db3 = new DBUtil();
			int count = db3.executeUpdate(sql3, param3);
			if (count < 1) {
				System.out.println(InfoID + "---" + EquipmentModel);
				break;
			}
			c++;
		}
		System.out.println("修改总条数:" + c);
		return "修改订单信息表";
	}

	public static String exchangeInventoryInfo() {// 库存信息表
		DBUtil db = new DBUtil();
		String sql = "select ID,EquipmentID FROM t_inventory_info WHERE EquipmentID<>'' AND EquipmentID IN (SELECT ID EquipmentID FROM t_equipment)";
		List<Map<String, Object>> ls = db.QueryToList(sql, null);
		System.out.println("库存表中外键不为0的ID、外键"+(ls.size()-1));
		
		String sql2 = "SELECT ID from t_commodity_info WHERE EquipmentID=?";
		System.out.println("根据外键找到商品管理的ID");
		Object[] param2 = new Object[1];
		String sql3 = "UPDATE t_inventory_info SET EquipmentID=? WHERE ID=?";
		System.out.println("根据库存ID，用商品管理的ID替换库存的外键");
		Object[] param3 = new Object[2];
		int c = 0;
		for (int i = 1; i < ls.size(); i++) {
			int id = Integer.parseInt(ls.get(i).get("ID").toString());//库存ID
			int EquipmentID = Integer.parseInt(ls.get(i).get("EquipmentID").toString());//外键
			DBUtil db2 = new DBUtil();
			param2[0] = EquipmentID;
			List<Map<String, Object>> ls2 = db2.QueryToList(sql2, param2);
			System.out.println(EquipmentID);
			int CommodityID = Integer.parseInt(ls2.get(1).get("ID").toString());//商品管理ID
			param3[0] = CommodityID;
			param3[1] = id;
			DBUtil db3 = new DBUtil();
			int count = db3.executeUpdate(sql3, param3);
			if (count < 1) {
				System.out.println("库存："+id + "---外键：" + EquipmentID);
				break;
			}
			c++;
		}
		System.out.println("修改总条数:" + c);
		return "修改库存信息表";
	}

	public static void main(String[] args) {
		/*
		 * 合并经历：最初产品型号表和商品管理表是重复的
		 * ---->去除产品型号表中重复的数据
		 * ---->更改订单信息表与库存表关联的产品型号外键
		 * --->去除商品管理表中重复的数据并更新
		 * --->更改报价系统中，关联商品管理表的外键
		 * ---->合并产品型号表
		 * ---->校验合并后的产品型号表
		 * ---->更改订单信息表外键
		 * ---->校验更改的订单信息表外键
		 * ---->更改库存信息表外键
		 * ---->校验更改的库存信息表外键
		 */
//		 exchangeSupplier();
//		exchangeCommodity();
//		 System.out.println(exchangeOrderInfo());
//		System.out.println(exchangeInventoryInfo());

		 
	}
}

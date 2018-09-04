package com.eoulu.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ExchangeCommodity {
	public static void main(String[] args) {

		String sql = "select Supplier,ID from t_commodity_info_test where ID>14440 and ID<14839";
		List<Map<String,Object>> ls = new DBUtil().QueryToList(sql, null);
		System.out.println("开始了");
//		String sql2 = "UPDATE t_commodity_info SET CommodityName=?,Model=?,Unit=?,DeliveryPeriod=?,ProducingArea=?,CostPrice=?,"
//				+ "DiscountCost=?,SellerPriceOne=?,SellerPriceTwo=?,SellerPriceThree=?,ProductCategory=?,"
//				+ "Item=?,ItemDescription=? WHERE ID=?";
		String sql2 = "UPDATE t_commodity_info SET Supplier=? WHERE ID=?";
		String sql3 = "SELECT ID FROM t_supplier WHERE `Name`=?";
		String sql4 = "insert into t_supplier (`Name`) values (?)";
		for(int i=1;i<ls.size();i++){
			int id = Integer.parseInt(ls.get(i).get("ID").toString());
			String Supplier = ls.get(i).get("Supplier").toString().trim();
			List<Map<String,Object>> list = new DBUtil().QueryToList(sql3, new Object[]{Supplier});
			int sup = 0;
			if(list.size()<2){
				int c = new DBUtil().executeUpdate(sql4, new Object[]{Supplier});
				List<Map<String,Object>> list2 = new DBUtil().QueryToList(sql3, new Object[]{Supplier});
				sup = Integer.parseInt(list2.get(1).get("ID").toString());
			}else{
				sup = Integer.parseInt(list.get(1).get("ID").toString());
			}
			int count = new DBUtil().executeUpdate(sql2,new Object[]{sup,id});
			if(count<1){
				System.out.println(ls.get(i));
				break;
			}
			/*
			int id = Integer.parseInt(ls.get(i).get("ID").toString());
			String CommodityName = ls.get(i).get("CommodityName").toString();
			String Model = ls.get(i).get("Model").toString();
			String Unit = ls.get(i).get("Unit").toString();
			String DeliveryPeriod = ls.get(i).get("DeliveryPeriod").toString();
			String ProducingArea = ls.get(i).get("ProducingArea").toString();
			double CostPrice = Double.parseDouble((ls.get(i).get("CostPrice").equals("")||ls.get(i).get("CostPrice").equals("--"))?"0":ls.get(i).get("CostPrice").toString());
			double DiscountCost = Double.parseDouble((ls.get(i).get("DiscountCost").equals("--") ||ls.get(i).get("DiscountCost").equals(""))?"0":ls.get(i).get("DiscountCost").toString());
			double SellerPriceOne = Double.parseDouble((ls.get(i).get("SellerPriceOne").equals("--") || ls.get(i).get("SellerPriceOne").equals(""))?"0":ls.get(i).get("SellerPriceOne").toString());
			double SellerPriceTwo = Double.parseDouble((ls.get(i).get("SellerPriceTwo").equals("--") || ls.get(i).get("SellerPriceTwo").equals(""))?"0":ls.get(i).get("SellerPriceTwo").toString());
			double SellerPriceThree = Double.parseDouble((ls.get(i).get("SellerPriceThree").equals("--") || ls.get(i).get("SellerPriceThree").equals(""))?"0":ls.get(i).get("SellerPriceThree").toString());
//			String Supplier = ls.get(i).get("Supplier").toString();
//			String OperatingTime = ls.get(i).get("OperatingTime").equals("--")?"00-00-0000":ls.get(i).get("OperatingTime").toString();
			String ProductCategory = ls.get(i).get("ProductCategory").toString();
//			String QuoteTime = ls.get(i).get("QuoteTime").equals("--")?"00-00-0000":ls.get(i).get("QuoteTime").toString();
			String Item = ls.get(i).get("Item").toString();
			String ItemDescription = ls.get(i).get("ItemDescription").toString();
//			String ItemCode = ls.get(i).get("ItemCode").toString();
//			int InitialQuantity = Integer.parseInt((ls.get(i).get("InitialQuantity")==null ||ls.get(i).get("InitialQuantity").equals("--")||ls.get(i).get("InitialQuantity").equals(""))?"0":ls.get(i).get("InitialQuantity").toString());
//			int EquipmentID = Integer.parseInt((ls.get(i).get("EquipmentID").equals("--") || ls.get(i).get("EquipmentID")==null ||  ls.get(i).get("EquipmentID").equals(""))?"0":ls.get(i).get("EquipmentID").toString());
//			int SupplierID = Integer.parseInt((ls.get(i).get("SupplierID").equals("--") || ls.get(i).get("SupplierID")==null || ls.get(i).get("SupplierID").equals("") )?"0":ls.get(i).get("SupplierID").toString());
			Object[] param = new Object[]{CommodityName,Model,Unit,DeliveryPeriod,ProducingArea,CostPrice,DiscountCost,SellerPriceOne,
					SellerPriceTwo,SellerPriceThree,ProductCategory,Item,ItemDescription,id};
			boolean flag = new DBUtil().executeUpdate(sql2, param)>=1?true:false;
			if(!flag){
				
				System.out.println(Arrays.toString(param));
				break;
			}
			*/
			
			
		}
		System.out.println("完事了");
	}
}

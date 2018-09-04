package com.eoulu.dao;

import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.util.DBUtil;

public class SalesStatisticsDao {

	public List<Map<String, Object>> getAllModelQuantity(Page page){

		List<Map<String, Object>> ls;
		
		String sql = "select a.Quantity,a.Model,a.Description from "
				+ "(SELECT SUM(t_order_info.Number) Quantity,t_commodity_info.Model ,t_commodity_info.CommodityName  "
				+ "Description FROM t_order_info LEFT JOIN t_commodity_info "
				+ "on t_order_info.EquipmentModel=t_commodity_info.ID "
				+ "LEFT JOIN t_order ON t_order.ID=t_order_info.OrderID "
				+ "where t_commodity_info.Model IS Not  NULL AND t_order.PageType=0 "
				+ "GROUP BY t_order_info.EquipmentModel ORDER BY Quantity desc ,t_commodity_info.Model) a where a.Quantity<>0 limit ?,?";
		
		DBUtil db = new DBUtil();
		Object[] parameter = new Object[]{(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		ls = db.QueryToList(sql, parameter);
		
		return ls; 
	}
	
	/**
	 * 总数量
	 * @return
	 */
	public int getAllCounts(){
		int counts = 0;
		DBUtil db = new DBUtil();
		String sql =  "select count(a.Model) from "
				+ "(SELECT SUM(t_order_info.Number) Quantity,t_commodity_info.Model  "
				+ " FROM t_order_info LEFT JOIN t_commodity_info "
				+ "on t_order_info.EquipmentModel=t_commodity_info.ID "
				+ "LEFT JOIN t_order ON t_order.ID=t_order_info.OrderID "
				+ "where t_commodity_info.Model IS Not  NULL  AND t_order.PageType=0 "
				+ "GROUP BY t_order_info.EquipmentModel ) a where a.Quantity<>0 ";
	
		List<Map<String, Object>> ls = db.QueryToList(sql, null);
	
		if(ls.size()>1){
			counts = Integer.parseInt(ls.get(1).get(ls.get(0).get("col1")).toString());
		}
		
		return counts;
	}
	/**
	 * 搜索
	 * @param sql
	 * @param parameter 
	 * @return
	 */
	public List<Map<String, Object>> getQueryList(String sql,Object[] parameter){
		
		DBUtil db = new DBUtil();
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);
		return ls;
		
	}
	
	public List<Map<String,Object>> QueryByTime(String startTime,String endTime){
		DBUtil db = new DBUtil();
		String sql = "";;
		Object[] param = new Object[]{ startTime, endTime};
		List<Map<String,Object>> ls = db.QueryToList(sql, param);
		
		return ls;
	}
	public int getHotProductCount(){
		DBUtil db = new DBUtil();
		String sql = " select COUNT(a.Model) from (SELECT SUM(t_order_info.Number) Quantity, t_commodity_info.ID,"
				+ " t_commodity_info.Model ,t_commodity_info.CommodityName Description ,t_commodity_info.CostPrice"
				+ " FROM t_commodity_info LEFT JOIN t_order_info  on t_order_info.EquipmentModel=t_commodity_info.ID "
				+ " LEFT JOIN t_order ON t_order.ID=t_order_info.OrderID where t_commodity_info.Model IS Not  NULL "
				+ " AND t_order.PageType=0 GROUP BY t_order_info.EquipmentModel ORDER BY Quantity desc ) a  where a.Quantity<>0 ";
		 List<Map<String,Object>>  ls = db.QueryToList(sql, null);
		 int count= 0 ;
		 if(ls.size()>1){
			 count = Integer.parseInt(ls.get(1).get("COUNT(a.Model)").toString());
		 }
			return count;	
	}
	public List<Map<String,Object>> getHotProduct(Page page){
		DBUtil db = new DBUtil();
		String sql = "  select a.Quantity,a.Model,a.Description,a.CostPrice,(a.CostPrice*a.Quantity) TotalPrice "
				+ "from (SELECT SUM(t_order_info.Number) Quantity, t_commodity_info.ID, t_commodity_info.Model ,"
				+ "t_commodity_info.CommodityName Description ,t_commodity_info.CostPrice  FROM"
				+ " t_commodity_info LEFT JOIN t_order_info  on t_order_info.EquipmentModel=t_commodity_info.ID "
				+ "LEFT JOIN t_order ON t_order.ID=t_order_info.OrderID where t_commodity_info.Model "
				+ "IS Not  NULL  AND t_order.PageType=0  GROUP BY t_order_info.EquipmentModel ORDER BY Quantity desc,t_commodity_info.Model ) a "
				+ " where a.Quantity<>0  limit ?,?";
		Object[] param = new Object[]{(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		List<Map<String,Object>> ls = db.QueryToList(sql, param);
		
		return ls;
	}
	public List<Map<String,Object>> getHotProductByTime(Page page,String startTime,String endTime,String classify){
		DBUtil db = new DBUtil();
		String sql = " select a.Quantity,a.Model,a.Description,(a.CostPrice*1.5) CostPrice,(a.CostPrice*1.5*a.Quantity) TotalPrice,a.ProductCategory  from "
				+ "(SELECT SUM(t_order_info.Number) Quantity,t_commodity_info.Model ,t_commodity_info.CommodityName Description,t_commodity_info.ProductCategory,"
				+ "t_commodity_info.CostPrice FROM t_order_info LEFT JOIN t_commodity_info on t_order_info.EquipmentModel=t_commodity_info.ID "
				+ " LEFT JOIN t_order ON t_order.ID=t_order_info.OrderID where t_commodity_info.Model IS Not  NULL  AND t_order.PageType=0 "
				+ " AND t_order.DateOfSign BETWEEN ? AND ? GROUP BY t_order_info.EquipmentModel ORDER BY Quantity desc,t_commodity_info.Model ) a  where a.Quantity<>0 limit ?,?";
		if(classify!=null && !classify.equals("")){
			sql = " select a.Quantity,a.Model,a.Description,(a.CostPrice*1.5) CostPrice,(a.CostPrice*1.5*a.Quantity) TotalPrice,a.ProductCategory  from "
				+ "(SELECT SUM(t_order_info.Number) Quantity,t_commodity_info.Model ,t_commodity_info.CommodityName Description,t_commodity_info.ProductCategory,"
				+ "t_commodity_info.CostPrice FROM t_order_info LEFT JOIN t_commodity_info on t_order_info.EquipmentModel=t_commodity_info.ID "
				+ " LEFT JOIN t_order ON t_order.ID=t_order_info.OrderID where t_commodity_info.Model IS Not  NULL  AND t_order.PageType=0 "
				+ " AND t_order.DateOfSign BETWEEN ? AND ? GROUP BY t_order_info.EquipmentModel ORDER BY Quantity desc,t_commodity_info.Model ) a  where a.Quantity<>0 ORDER BY ";
			sql += classify+" desc limit ?,?";
		}
		Object[] param = new Object[]{startTime, endTime,(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		List<Map<String,Object>> ls = db.QueryToList(sql, param);
		
		return ls;
	}
	
	public int getHotProductByTimeCount(Page page,String startTime,String endTime){
		long time1 = System.currentTimeMillis();
		DBUtil db = new DBUtil();
		String sql =  "select count(a.Model) from (SELECT SUM(t_order_info.Number) Quantity,"
				+ "t_commodity_info.Model  FROM t_order_info LEFT JOIN t_commodity_info on "
				+ "t_order_info.EquipmentModel=t_commodity_info.ID LEFT JOIN t_order ON "
				+ "t_order.ID=t_order_info.OrderID where t_commodity_info.Model IS Not  NULL  AND t_order.PageType=0 "
				+ "AND t_order.DateOfSign BETWEEN ? AND ? GROUP BY t_order_info.EquipmentModel "
				+ "ORDER BY Quantity desc ) a where a.Quantity<>0 ";
		Object[] param = new Object[]{startTime, endTime};
		long time3 = System.currentTimeMillis();
//		System.out.println("sqlqian::"+(time3-time1));
		List<Map<String,Object>> ls = db.QueryToList(sql, param);
		long time4 = System.currentTimeMillis();
//		System.out.println("sqlqian::"+(time4-time3));
		int count = 0;
		if(ls.size()>1){
			count = Integer.parseInt(ls.get(1).get("count(a.Model)").toString());
		}
		long time2 = System.currentTimeMillis();
//		System.out.println("tiaoshu:"+(time2-time1));
		return count;
	}
}

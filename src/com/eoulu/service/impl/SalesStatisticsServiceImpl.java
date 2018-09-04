package com.eoulu.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.dao.SalesStatisticsDao;
import com.eoulu.service.SalesStatisticsService;
import com.eoulu.util.DBUtil;

public class SalesStatisticsServiceImpl implements SalesStatisticsService {
	public static final Map<String, Object> classify_MAP; 

	static{
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("型号", "Model");
		
		classify_MAP = map;
	}
	@Override
	public List<Map<String, Object>> getSalesStatistics(Page page) {
		
		return new SalesStatisticsDao().getAllModelQuantity(page);
	}

	@Override
	public int getAllcounts() {
		
		return new SalesStatisticsDao().getAllCounts();
	}

	@Override
	public List<Map<String, Object>> getSalesStatisticsByPageInOne(String classify, Object parameter, Page page) {
		Object[] obj = null;
		switch(classify_MAP.get(classify).toString()){
	
		default: obj=new Object[1]; obj[0]="%"+parameter+"%";
		}
		String sql = "select a.Quantity,a.Model,a.Description from "
				+ "(SELECT SUM(t_order_info.Number) Quantity,t_commodity_info.Model ,t_commodity_info.CommodityName "
				+ "Description FROM t_order_info LEFT JOIN t_commodity_info "
				+ "on t_order_info.EquipmentModel=t_commodity_info.ID "
				+ "LEFT JOIN t_order ON t_order.ID=t_order_info.OrderID "
				+ "LEFT JOIN t_requirement_classify on t_order.ContractCategory=t_requirement_classify.ID "
				+ "where t_commodity_info.Model IS Not  NULL "
				+ "GROUP BY t_order_info.EquipmentModel ORDER BY Quantity desc ) a where a.Quantity<>0 ";
				
		for(int i=0 ; i<obj.length ; i++){
			sql += " and  "+classify_MAP.get(classify)+" like ?";
			if(i<obj.length-1){
				sql+=" or ";
			}
		}
		sql += " limit ?,?";
		Object[] param;
		if(obj.length == 0){
			param = new Object[2];
			param[0] = (page.getCurrentPage()-1)*page.getRows();
			param[1] = page.getRows();
		}else{
			param = new Object[obj.length+2];
			for(int i=0 ; i< obj.length ; i++){
				param[i] = obj[i];
			}
			param[obj.length] = (page.getCurrentPage()-1)*page.getRows();
			param[obj.length+1] = page.getRows();
		}
		System.out.println(sql);
		System.out.println(Arrays.toString(param));
		return new SalesStatisticsDao().getQueryList(sql, param);
		
	}

	@Override
	public int getCountByClassify(String classify, Object parameter) {
		
		Object[] obj = null;
		switch(classify_MAP.get(classify).toString()){
		
		default: obj=new Object[1]; obj[0]="%"+parameter+"%";
		}
		String sql = "select count(a.Model) from "
				+ "(SELECT SUM(t_order_info.Number) Quantity,t_commodity_info.Model ,t_commodity_info.CommodityName "
				+ "Description FROM t_order_info LEFT JOIN t_commodity_info "
				+ "on t_order_info.EquipmentModel=t_commodity_info.ID "
				+ "LEFT JOIN t_order ON t_order.ID=t_order_info.OrderID "
				+ "LEFT JOIN t_requirement_classify on t_order.ContractCategory=t_requirement_classify.ID "
				+ "where t_commodity_info.Model IS Not  NULL "
				+ "GROUP BY t_order_info.EquipmentModel ORDER BY Quantity desc ) a where a.Quantity<>0 ";
				
		sql += " and  a."+classify_MAP.get(classify)+" like ?";
		
		
		return new DBUtil().getCountsByName(sql, obj);
	}

	@Override
	public List<Map<String, Object>> getHotProduct(Page page) {
		return new SalesStatisticsDao().getHotProduct(page);
	}

	@Override
	public List<Map<String, Object>> getHotProductByTime(Page page, String startTime, String endTime,String classify) {
		return new SalesStatisticsDao().getHotProductByTime(page, startTime, endTime,classify);
	}

	@Override
	public int getHotProductCount() {
		return  new SalesStatisticsDao().getHotProductCount();
	}

	@Override
	public int getHotProductByTimeCount(Page page, String startTime, String endTime) {
		return  new SalesStatisticsDao().getHotProductByTimeCount(page, startTime, endTime);
	}

}

package com.eoulu.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.HardwareAdvances;
import com.eoulu.util.DBUtil;

public class HardwareAdvancesDao {
	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	public List<Map<String,Object>> getHardwareAdvances(Page page){
    
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();


		String sql= "select ID, Customer,`Status`,InstalledTime,ResponsibleAndProcess,OperatingTime"
				+ " from t_hardware_advances order by Status,InstalledTime  desc  limit ?,?";
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
		String sql = "select count(ID) ? from t_hardware_advances order by Status,InstalledTime desc";
		
		Object[] parameter = new Object[]{"AllCounts"};
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);
		
		if(ls.size()>1)
			counts = Integer.parseInt(ls.get(1).get(ls.get(0).get("col1")).toString());
		
		return counts;
	}

	/**
	 * 添加
	 * @param invoice
	 * @param db
	 * @return
	 */
	public boolean insert(HardwareAdvances hardware){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[5];
		String sql = "insert into t_hardware_advances (Customer,`Status`,InstalledTime,ResponsibleAndProcess,OperatingTime) values (?,?,?,?,?)";
		parameter[0] = hardware.getCustomer();
		parameter[1] = hardware.getStatus();
		parameter[2] = hardware.getInstalledTime();
		parameter[3] = hardware.getResponsibleAndProcess();
		parameter[4] = df.format(new Date());
		
		int i = 0;
		i = db.executeUpdate(sql, parameter);
//		try {
//			i = db.executeUpdateNotClose(sql, parameter);
//		} catch (SQLException e) {
//			
//			e.printStackTrace();
//		}
		if(i>=1){
			flag = true;
		}
		return flag;
	}
	/**
	 * 修改
	 * @param packing
	 * @return
	 */
	public boolean update(HardwareAdvances hardware){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[6];
		String sql = "update  t_hardware_advances set Customer=?,`Status`=?,InstalledTime=?,ResponsibleAndProcess=?,OperatingTime=? where ID=?";

		parameter[0] = hardware.getCustomer();
		parameter[1] = hardware.getStatus();
		parameter[2] = hardware.getInstalledTime();
		parameter[3] = hardware.getResponsibleAndProcess();
		parameter[4] = df.format(new Date());
		parameter[5] = hardware.getID();
		int i = 0;
//		try {
//			i = db.executeUpdateNotClose(sql, parameter);
//		} catch (SQLException e) {
//			
//			e.printStackTrace();
//		}
		
		i = db.executeUpdate(sql, parameter);
		if(i>=1){
			flag = true;
		}
		return flag;
	}
	
	public boolean delete(int id){
		
		DBUtil db = new DBUtil();
		boolean flag = false;
		
		String sql = "delete from t_hardware_advances where ID=?";
		int i = 0;
//		try {
//			i = db.executeUpdateNotClose(sql, null);
//		} catch (SQLException e) {
//			
//			e.printStackTrace();
//		}
		i = db.executeUpdate(sql, null);
		if(i>=1){
			flag = true;
		}
		return flag;
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
}

package com.eoulu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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


		String sql= "select ID, Customer,`Status`,InstalledTime,ResponsibleAndProcess,LatestProgress,OperatingTime"
				+ " from t_hardware_advances order by Status,InstalledTime  desc  limit ?,?";
		Object[] parameter = new Object[]{(page.getCurrentPage()-1)*page.getRows(),page.getRows()};

		ls = db.QueryToList(sql, parameter);
		return ls;
	}

	
	
	public List<Map<String,Object>> getCurrentProgress(int MachineID){
	    
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();


		String sql= "select ID,CurrentProgress,Date from t_hardware_progress where MachineID=? ORDER BY ID";
		Object[] parameter = new Object[]{MachineID};

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
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public int insert(HardwareAdvances hardware) throws NumberFormatException, Exception{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		Object[] parameter = new Object[6];
		String sql = "insert into t_hardware_advances (Customer,`Status`,InstalledTime,ResponsibleAndProcess,LatestProgress,OperatingTime) values (?,?,?,?,?,?)";
		parameter[0] = hardware.getCustomer();
		parameter[1] = hardware.getStatus();
		parameter[2] = hardware.getInstalledTime();
		parameter[3] = hardware.getResponsibleAndProcess();
		parameter[4] = hardware.getCurrentProgress();
		parameter[5] = df.format(new Date());
		
		int i = 0;
		i = Integer.parseInt(db.insertGetId(sql, parameter).toString());
		
		return i;
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
		Object[] parameter = new Object[7];
		String sql = "update  t_hardware_advances set Customer=?,`Status`=?,InstalledTime=?,ResponsibleAndProcess=?,LatestProgress=?,OperatingTime=? where ID=?";

		parameter[0] = hardware.getCustomer();
		parameter[1] = hardware.getStatus();
		parameter[2] = hardware.getInstalledTime();
		parameter[3] = hardware.getResponsibleAndProcess();
		parameter[4] = hardware.getCurrentProgress();
		parameter[5] = df.format(new Date());
		parameter[6] = hardware.getID();
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
	
	public boolean insertProgress(List<Map<String, String>> list,int MachineID){

		DBUtil util = new DBUtil();
		String sql1 = "DELETE FROM t_hardware_progress WHERE MachineID=?";
	
		
		Connection conn = util.getConnection();
		try {
			conn.setAutoCommit(false);
			util.executeUpdateNotClose(sql1, new Object[]{MachineID});
			String sql = "INSERT INTO t_hardware_progress(MachineID,CurrentProgress,Date) values(?,?,?)";
			PreparedStatement statement = conn.prepareStatement(sql);
			for(int i = 0;i < list.size();i ++){
				statement.setInt(1, MachineID);
				statement.setString(2, list.get(i).get("CurrentProgress"));
				statement.setString(3, list.get(i).get("Date"));
				statement.addBatch();
			}
			statement.executeBatch();
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}finally {
			util.closed();
		}
		
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

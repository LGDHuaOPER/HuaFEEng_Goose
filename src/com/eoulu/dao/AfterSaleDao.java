package com.eoulu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.AfterSale;
import com.eoulu.util.DBUtil;

public class AfterSaleDao {
	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	public List<Map<String,Object>> getAfterSale(Page page){
    
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();


		String sql= "select ID,CustomerName,CustomerUnit,ProjectStatus,EndDate,ServiceProject,"
				+ "OperatingTime from t_after_sale order by ProjectStatus,EndDate desc limit ?,?";
		Object[] parameter = new Object[]{(page.getCurrentPage()-1)*page.getRows(),page.getRows()};

		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	public List<Map<String,Object>> getCurrentProgress(int SaleID){
	    
			List<Map<String, Object>> ls = null;
			
			DBUtil db = new DBUtil();


			String sql= "select ID,SaleID,CurrentProgress,Date from t_after_sale_progress where SaleID=? ORDER BY ID";
			Object[] parameter = new Object[]{SaleID};

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
		String sql = "select count(ID) ? from t_after_sale order by ProjectStatus,EndDate desc";
		
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
	public int insert(AfterSale sale) throws NumberFormatException, Exception{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		Object[] parameter = new Object[7];
		String sql = "insert into t_after_sale (CustomerName,CustomerUnit,CurrentProgress,ProjectStatus,EndDate,ServiceProject,OperatingTime) values (?,?,?,?,?,?,?)";
		parameter[0] = sale.getCustomerName();
		parameter[1] = sale.getCustomerUnit();
		parameter[2] = sale.getCurrentProgress();
		parameter[3] = sale.getProjectStatus();
		parameter[4] = sale.getEndDate();
		parameter[5] = sale.getServiceProject();
		parameter[6] = df.format(new Date());
		
		int i = 0;

		i = Integer.parseInt(db.insertGetId(sql, parameter).toString());
	
	
		return i;
	}
	
	public int getMaxID(){
		DBUtil util = new DBUtil();
		String sql = "SELECT MAX(ID) MaxID from t_after_sale";
		List<Map<String, Object>> ls = util.QueryToList(sql, null);
		return Integer.parseInt(ls.get(1).get("MaxID").toString());	
	}
	
	/**
	 * 修改
	 * @param packing
	 * @return
	 */
	public boolean update(AfterSale sale){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[8];
		String sql = "update t_after_sale set CustomerName=?,CustomerUnit=?,CurrentProgress=?,ProjectStatus=?,EndDate=?,ServiceProject=?,OperatingTime=? where ID=?";


		parameter[0] = sale.getCustomerName();
		parameter[1] = sale.getCustomerUnit();
		parameter[2] = sale.getCurrentProgress();
		parameter[3] = sale.getProjectStatus();
		parameter[4] = sale.getEndDate();
		parameter[5] = sale.getServiceProject();
		parameter[6] = df.format(new Date());
		parameter[7] = sale.getID();
		int i = 0;
		i = db.executeUpdate(sql, parameter);
		if(i>=1){
			flag = true;
		}
		return flag;
	}
	public boolean deleteProgress(int saleID){
		DBUtil util = new DBUtil();
		String sql = "DELETE FROM t_after_sale_progress WHERE SaleID=?";
		Object[] param = new Object[]{saleID};
		int result = util.executeUpdate(sql, param);
		return result > 0?true:false;
		
	}
	
	public boolean insertProgress(List<Map<String, String>> list,int SaleID){

		DBUtil util = new DBUtil();
		String sql1 = "DELETE FROM t_after_sale_progress WHERE SaleID=?";
	
		
		Connection conn = util.getConnection();
		try {
			conn.setAutoCommit(false);
			util.executeUpdateNotClose(sql1, new Object[]{SaleID});
			String sql = "INSERT INTO t_after_sale_progress(SaleID,CurrentProgress,Date) values(?,?,?)";
			PreparedStatement statement = conn.prepareStatement(sql);
			for(int i = 0;i < list.size();i ++){
				statement.setInt(1, SaleID);
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
		
		String sql = "delete from t_after_sale where ID=?";
		int i = 0;
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
	
	public List<Map<String, Object>> getNotSolvedCount(){
		String sql = "select count(ID) Count from t_after_sale where ProjectStatus = '1'";
		DBUtil dbUtil = new DBUtil();
		List<Map<String, Object>> ls = dbUtil.QueryToList(sql, null);
		return ls;
	}
}

package com.eoulu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.MachineDetails;
import com.eoulu.util.DBUtil;

public class MachineDetailsDao {

	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	public List<Map<String,Object>> getMachineDetails(Page page){
    
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql= "select t_machine_details.ID,t_customer.CustomerName CustomerUnit,t_customer.Contact CustomerName,t_machine_details.Model,"
				+ "t_machine_details.SN,t_machine_details.ContractNO,t_machine_details.InstalledTime,t_machine_details.CustomerID,"
				+ "t_machine_details.Status,t_machine_details.LatestProgress,t_machine_details.Responsible "
				+ "from t_machine_details left join t_customer on t_customer.ID =t_machine_details.CustomerID "
				+ "order by Status,InstalledTime DESC limit ?,?";
			
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
		String sql = "select count(ID) ? from t_machine_details order by InstalledTime desc";
		
		Object[] parameter = new Object[]{"AllCounts"};
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);
		
		if(ls.size()>1)
			counts = Integer.parseInt(ls.get(1).get(ls.get(0).get("col1")).toString());
		
		return counts;
	}
	
	public List<Map<String,Object>> getCurrentProgress(int MachineID){
	    
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();


		String sql= "select ID,CurrentProgress,Date from t_machine_progress where MachineID=? ORDER BY ID";
		Object[] parameter = new Object[]{MachineID};

		ls = db.QueryToList(sql, parameter);
		return ls;
	}

	/**
	 * 添加
	 * @param invoice
	 * @param db
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public int insert(MachineDetails machine) throws NumberFormatException, Exception{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		Object[] parameter = new Object[9];
		String sql = "insert into t_machine_details (Model,SN,ContractNO,InstalledTime,"
				+ "OperatingTime,CustomerID,Status,LatestProgress,Responsible) values (?,?,?,?,?,?,?,?,?)";
	
		parameter[0] = machine.getModel();
		parameter[1] = machine.getSN();
		parameter[2] = machine.getContractNO();
		parameter[3] = machine.getInstalledTime();
		parameter[4] = df.format(new Date());
		parameter[5] = machine.getCustomerID();
		parameter[6] = machine.getStatus();
		parameter[7] = machine.getCurrentProgress();
		parameter[8] = machine.getResponsible();
		
		int i = 0;
		i = Integer.parseInt(db.insertGetIdNotClose(sql, parameter).toString());

		return i;
	}
	/**
	 * 修改
	 * @param packing
	 * @return
	 * @throws SQLException 
	 */
	public boolean update(MachineDetails machine) throws SQLException{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[10];
		String sql = "update  t_machine_details set Model=?,SN=?,ContractNO=?,InstalledTime=?,"
				+ "OperatingTime=?,CustomerID=?,Status=?,LatestProgress=?,Responsible=? where ID=?";

		
		parameter[0] = machine.getModel();
		parameter[1] = machine.getSN();
		parameter[2] = machine.getContractNO();
		parameter[3] = machine.getInstalledTime();
		parameter[4] = df.format(new Date());
		parameter[5] = machine.getCustomerID();
		parameter[6] = machine.getStatus();
		parameter[7] = machine.getCurrentProgress();
		parameter[8] = machine.getResponsible();
		parameter[9] = machine.getID();
		int i = 0;
		i = db.executeUpdateNotClose(sql, parameter);
		if(i>=1){
			flag = true;
		}
		return flag;
	}
	
	public boolean insertProgress(List<Map<String, String>> list,int MachineID){

		DBUtil util = new DBUtil();
		String sql1 = "DELETE FROM t_machine_progress WHERE MachineID=?";
	
		
		Connection conn = util.getConnection();
		try {
			conn.setAutoCommit(false);
			util.executeUpdateNotClose(sql1, new Object[]{MachineID});
			String sql = "INSERT INTO t_machine_progress(MachineID,CurrentProgress,Date) values(?,?,?)";
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
		Object[] param = new Object[]{id};
		String sql = "delete from t_machine_details where ID=?";
		int i = 0;
		i = db.executeUpdate(sql, param);
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
	
	/**
	 * 根据合同号获取合同页面的合同配置
	 */
	public List<Map<String,Object>> getConfigure(String ContractNO){
	    
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();


		String sql= "select t_order_info.ID,t_order_info.ExceptDate,t_order_info.StockNumber,t_order_info.LogisticsNumber,t_commodity_info.CommodityName Remarks,"
				+ "t_order_info.OrderID,t_commodity_info.Model EquipmentModel,t_order_info.Number,t_order_info.Date,t_order_info.DeliveryNumber,"
				+ "t_order_status.Status from t_order_info left join t_commodity_info on t_order_info.EquipmentModel=t_commodity_info.ID left join t_order_status on "
				+ "t_order_info.Status=t_order_status.ID left join t_order on t_order.ID = t_order_info.OrderID where t_order.ContractNo=?";
		Object[] parameter = new Object[]{ContractNO};

		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	
}

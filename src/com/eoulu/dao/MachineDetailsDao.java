package com.eoulu.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.MachineDetails;
import com.eoulu.entity.Shipment;
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
				+ "t_customer.CustomerLevel,VisitTime "
				+ "from t_machine_details left join t_customer on t_customer.ID =t_machine_details.CustomerID left join "
				+ "(select t_visiting_record.MachineDetailsID MachineDetailsID,MAX(t_visiting_record.VisitTime) VisitTime " + 
				"from t_visiting_record GROUP BY t_visiting_record.MachineDetailsID) a on a.MachineDetailsID=t_machine_details.ID" + 
				" order BY case " + 
				"when CustomerLevel='A' then 1 " + 
				"when CustomerLevel='B' then 2 " + 
				"when CustomerLevel='C' then 3 " + 
				"when CustomerLevel='--' then 4 " + 
				"WHEN  CustomerLevel IS NULL then 5 end ,if(VisitTime is null,InstalledTime,VisitTime) desc limit ?,?";
			
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

	/**
	 * 添加
	 * @param invoice
	 * @param db
	 * @return
	 */
	public boolean insert(MachineDetails machine){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[6];
		String sql = "insert into t_machine_details (Model,SN,ContractNO,"
				+ "InstalledTime,OperatingTime,CustomerID) values (?,?,?,?,?,?)";
	
		parameter[0] = machine.getModel();
		parameter[1] = machine.getSN();
		parameter[2] = machine.getContractNO();
		parameter[3] = machine.getInstalledTime();
		parameter[4] = df.format(new Date());
		parameter[5] = machine.getCustomerID();
		
		int i = 0;
		i = db.executeUpdate(sql, parameter);
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
	public boolean update(MachineDetails machine){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[7];
		String sql = "update  t_machine_details set Model=?,SN=?,ContractNO=?,"
				+ "InstalledTime=?,OperatingTime=?,CustomerID=? where ID=?";

		
		parameter[0] = machine.getModel();
		parameter[1] = machine.getSN();
		parameter[2] = machine.getContractNO();
		parameter[3] = machine.getInstalledTime();
		parameter[4] = df.format(new Date());
		parameter[5] = machine.getCustomerID();
		parameter[6] = machine.getID();
		int i = 0;
		i = db.executeUpdate(sql, parameter);
		if(i>=1){
			flag = true;
		}
		return flag;
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

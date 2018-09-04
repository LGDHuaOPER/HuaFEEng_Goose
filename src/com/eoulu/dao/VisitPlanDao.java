package com.eoulu.dao;

import java.util.List;
import java.util.Map;

import com.eoulu.util.DBUtil;

public class VisitPlanDao {

	public List<Map<String, Object>> getVisitPlanByPage(String page) {
		DBUtil db = new DBUtil();
		String sql = "select t_machine_details.ID,t_machine_details.ContractNO,t_machine_details.InstalledTime," + 
				"t_machine_details.Model,t_machine_details.SN,t_customer.CustomerName,t_customer.Contact,a.VisitTime,t_customer.CustomerLevel " + 
				"from t_machine_details left join t_customer on t_machine_details.CustomerID=t_customer.ID  " + 
				"left join (select t_visiting_record.MachineDetailsID MachineDetailsID,MAX(t_visiting_record.VisitTime) VisitTime " + 
				"from t_visiting_record GROUP BY t_visiting_record.MachineDetailsID) a on a.MachineDetailsID=t_machine_details.ID" + 
				" order BY case " + 
				"when CustomerLevel='A' then 1 " + 
				"when CustomerLevel='B' then 2 " + 
				"when CustomerLevel='C' then 3 " + 
				"when CustomerLevel='--' then 4 " + 
				"WHEN  CustomerLevel IS NULL then 5 end ,if(VisitTime is null,InstalledTime,VisitTime) desc limit ?,10";
		Object[] param = new Object[] {(Integer.parseInt(page)-1)*10};
		return db.QueryToList(sql, param);
	}

	public List<Map<String, Object>> getVisitPlanByPageAndContent(String page, String content) {
		DBUtil db = new DBUtil();
		String sql = "select t_machine_details.ID,t_machine_details.ContractNO,t_machine_details.InstalledTime," + 
				"t_machine_details.Model,t_machine_details.SN,t_customer.CustomerName,t_customer.Contact,a.VisitTime,t_customer.CustomerLevel " + 
				"from t_machine_details left join t_customer on t_machine_details.CustomerID=t_customer.ID  " + 
				"left join (select t_visiting_record.MachineDetailsID MachineDetailsID,MAX(t_visiting_record.VisitTime) VisitTime " + 
				"from t_visiting_record GROUP BY t_visiting_record.MachineDetailsID) a on a.MachineDetailsID=t_machine_details.ID" + 
				" where t_customer.Contact like ? or t_customer.CustomerName like ? "+
				" order BY case " + 
				"when CustomerLevel='A' then 1 " + 
				"when CustomerLevel='B' then 2 " + 
				"when CustomerLevel='C' then 3 " + 
				"when CustomerLevel='--' then 4 " + 
				"WHEN  CustomerLevel IS NULL then 5 end ,if(VisitTime is null,InstalledTime,VisitTime) desc limit ?,10";
		Object[] param = new Object[] {"%"+content+"%","%"+content+"%",(Integer.parseInt(page)-1)*10};
		List<Map<String, Object>> a = db.QueryToList(sql, param);
		return a;
	}

	public int getVisitPlanCount() {
		DBUtil db = new DBUtil();
		String sql = "select count(ID) count from t_machine_details ";
		Object[] param = null;
		return Integer.parseInt(db.QueryToList(sql, param).get(1).get("count").toString());
	}

	public int getVisitPlanCountByContent(String content) {
		DBUtil db = new DBUtil();
		String sql = "select count(t_machine_details.ID) count from t_machine_details"
				+ " left join t_customer on t_machine_details.CustomerID=t_customer.ID "
				+ "where t_customer.Contact like ? or t_customer.CustomerName like ? ";
		Object[] param = new Object[] {"%"+content+"%","%"+content+"%"};
		int a = Integer.parseInt(db.QueryToList(sql, param).get(1).get("count").toString());
		System.out.println(a);
		return a ;
	}

	public boolean inset(int ID, String Name, String time, String Engineer, String Details) {
		DBUtil db = new DBUtil();
		String sql ="INSERT INTO t_visiting_record (MachineDetailsID,VisitName,VisitTime,Engineer,Details) values(?,?,?,?,?)";
		Object[] param = new Object[] {ID,Name,time,Engineer,Details};
		return db.executeUpdate(sql, param)>0?true:false;
	}

	public List<Map<String, Object>> GetVisitPlan(int ID) {
		DBUtil db = new DBUtil();
		String sql ="select * from t_visiting_record where MachineDetailsID=?";
		Object[] param = new Object[] {ID};
		return db.QueryToList(sql, param);
	}

}

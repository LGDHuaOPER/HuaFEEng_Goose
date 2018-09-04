package com.eoulu.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.RoutineVisit;
import com.eoulu.entity.RoutineVisitProject;
import com.eoulu.util.DBUtil;

public class RoutineVisitDao {

	public List<Map<String, Object>> getAllData(Page page) {
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql = "select * from t_routine_visit order by AddDate ASC limit ?,?";
	
		Object[] param = new Object[] { (page.getCurrentPage() - 1) * page.getRows(), page.getRows() };
		ls = db.QueryToList(sql, param);

		return ls;
	}
	
	public int getAllCounts() {
		DBUtil db = new DBUtil();
		String sql = "select count(ID) ? from t_routine_visit  ";
		Object[] param = new Object[] { "AllCounts" };
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		int counts = 0;
		if (ls.size() > 1) {
			counts = Integer.parseInt(ls.get(1).get(ls.get(0).get("col1")).toString());
		}
		return counts;
	}
	
	public boolean insert(RoutineVisit factory) {
		DBUtil db = new DBUtil();
		SimpleDateFormat dfg = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String sql = "insert into t_routine_visit (CustomerUnit,EndUser,EndTel,ToEngineer,ModelAndSN,Instrument,"
				+ "VisitDate,CheckContent,OperatingTime,AddDate,Conclusion) values (?,?,?,?,?,?,?,?,?,?,?)";
		Object[] param = new Object[11];
		param[0] = factory.getCustomerUnit();
		param[1] = factory.getEndUser();
		param[2] = factory.getEndTel();
		param[3] = factory.getToEngineer();
		param[4] = factory.getModelAndSN();
		param[5] = factory.getInstrument();
		param[6] = factory.getVisitDate();
		param[7] = factory.getCheckContent();
		param[8] = dfg.format(new Date());
		param[9] = df.format(new Date());
		param[10] = factory.getConclusion();
		boolean flag = false;
		int temp = db.executeUpdate(sql, param);
		if (temp >= 1) {
			flag = true;
		}
		return flag;
	}
	public boolean update(RoutineVisit factory) {
		DBUtil db = new DBUtil();
		SimpleDateFormat dfg = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "update t_routine_visit set CustomerUnit=?,EndUser=?,EndTel=?,ToEngineer=?,ModelAndSN=?,Instrument=?,"
				+ "VisitDate=?,CheckContent=?,OperatingTime=?,Conclusion=? where ID=?";
		Object[] param = new Object[11];
		param[0] = factory.getCustomerUnit();
		param[1] = factory.getEndUser();
		param[2] = factory.getEndTel();
		param[3] = factory.getToEngineer();
		param[4] = factory.getModelAndSN();
		param[5] = factory.getInstrument();
		param[6] = factory.getVisitDate();
		param[7] = factory.getCheckContent();
		param[8] = dfg.format(new Date());
		param[9] = factory.getConclusion();
		param[10] = factory.getID();
		
		boolean flag = false;
		int temp = db.executeUpdate(sql, param);
		if (temp >= 1) {
			flag = true;
		}
		return flag;
	}
	
	public List<Map<String, Object>> getAllProject(int id) {
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql = "select * from t_routine_visit_project where VisitID=?";
	
		Object[] param = new Object[] { id };
		ls = db.QueryToList(sql, param);

		return ls;
	}
	
	public boolean insertProject(RoutineVisitProject factory) {
		DBUtil db = new DBUtil();
		String sql = "insert into t_routine_visit_project (Project,CheckSituation,Evolve,EvolveAnother,VisitID) values (?,?,?,?,?)";
		Object[] param = new Object[5];
		param[0] = factory.getProject();
		param[1] = factory.getCheckSituation();
		param[2] = factory.getEvolve();
		param[3] = factory.getEvolveAnother();
		param[4] = factory.getVisitID();
		
		boolean flag = false;
		int temp = db.executeUpdate(sql, param);
		if (temp >= 1) {
			flag = true;
		}
		return flag;
	}
	
	public boolean updateProject(RoutineVisitProject factory) {
		DBUtil db = new DBUtil();
		String sql = "update t_routine_visit_project set Project=?,CheckSituation=?,Evolve=?,EvolveAnother=? where ID=?";
		Object[] param = new Object[5];
		param[0] = factory.getProject();
		param[1] = factory.getCheckSituation();
		param[2] = factory.getEvolve();
		param[3] = factory.getEvolveAnother();
		param[4] = factory.getID();
		
		boolean flag = false;
		int temp = db.executeUpdate(sql, param);
		if (temp >= 1) {
			flag = true;
		}
		return flag;
	}
	
	public boolean deleteProject(int id) {
		DBUtil db = new DBUtil();
		String sql = "delete from t_routine_visit_project where ID=?";
		Object[] param = new Object[]{id};
		
		boolean flag = false;
		int temp = db.executeUpdate(sql, param);
		if (temp >= 1) {
			flag = true;
		}
		return flag;
	}
	
	public List<Map<String, Object>>  getPerData(int id) {
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql = "select * from t_routine_visit where ID=?";
	
		Object[] param = new Object[] { id};
		ls = db.QueryToList(sql, param);

		return ls;
	}
	
	public int getVisitID(String model,String content,String visitDate){
		DBUtil db = new DBUtil();
		List<Map<String, Object>> ls = null;
		
		String sql="select ID from t_routine_visit where ModelAndSN=? and CheckContent=? and VisitDate=?";
		Object[] parameter = new Object[]{model,content,visitDate};
		
		ls = db.QueryToList(sql, parameter,true);
		int id = 0;
		if(ls.size()>1){
			id = Integer.parseInt(ls.get(1).get("ID").toString());
		}
		return id;
	}
	
}

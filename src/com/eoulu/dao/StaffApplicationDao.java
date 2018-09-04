package com.eoulu.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.StaffApplication;
import com.eoulu.util.DBUtil;

public class StaffApplicationDao {

	public List<Map<String, Object>> getAllData(Page page,String user,String authority,String startTime,String endTime) {
		DBUtil db = new DBUtil();
		Object[] param = null;
		String sql = "";
		if(authority.equals("no")){
			sql = "select * FROM t_staff_application where RealName=? ";
			param = new Object[] { user,(page.getCurrentPage() - 1)*10, page.getRows() };
			if(!startTime.equals("")){
				sql=sql.concat(" AND DATE_FORMAT(StartTime,'%Y-%m') between ? and ?");
				param = new Object[] { user,startTime,endTime,(page.getCurrentPage() - 1)*10, page.getRows()};
			}
		}else if(authority.equals("all")){
			sql = "select * FROM t_staff_application ";
			param = new Object[] {(page.getCurrentPage() - 1)*10, page.getRows() };
			if(!startTime.equals("")){
				sql=sql.concat(" where DATE_FORMAT(StartTime,'%Y-%m') between ? and ?");
				param = new Object[] {startTime,endTime, (page.getCurrentPage() - 1)*10, page.getRows()};
			}
		}else{
			sql = "select * from t_staff_application where RealName in(Select StaffName from t_staff "
					+ "where Department = (select Department from t_staff where StaffName=?)) ";
				
			param = new Object[] { user,(page.getCurrentPage() - 1)*10, page.getRows() };
			if(!startTime.equals("")){
				sql=sql.concat(" AND DATE_FORMAT(StartTime,'%Y-%m') between ? and ?");
				param = new Object[] { user,startTime,endTime,(page.getCurrentPage() - 1)*10, page.getRows()};
			}
		}
		sql = sql.concat(" ORDER BY ID DESC limit ?,?");
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		return ls;
	}

	public int getCounts(String user,String authority,String startTime,String endTime) {
		DBUtil db = new DBUtil();
		Object[] param = null;
		String sql = "";
		if(authority.equals("no")){
			sql = "select count(ID) FROM t_staff_application where RealName=?";
			param = new Object[] { user};
			if(!startTime.equals("")){
				sql=sql.concat(" AND DATE_FORMAT(StartTime,'%Y-%m') between ? and ?");
				param = new Object[] { user,startTime,endTime};
			}
		}else if(authority.equals("all")){
			sql = "select count(ID) FROM t_staff_application";
			if(!startTime.equals("")){
				sql=sql.concat(" WHERE DATE_FORMAT(StartTime,'%Y-%m') between ? and ?");
				param = new Object[] { startTime,endTime};
			}
		}else{
			sql = "select count(ID) from t_staff_application where RealName in(Select StaffName from t_staff "
					+ "where Department = (select Department from t_staff where StaffName=?)) ";
			param = new Object[] { user};
			if(!startTime.equals("")){
				sql=sql.concat(" AND DATE_FORMAT(StartTime,'%Y-%m') between ? and ?");
				param = new Object[] { user,startTime,endTime};
			}
		}
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		int count = 0;
		if (ls.size() > 1) {
			count = Integer.parseInt(ls.get(1).get("count(ID)").toString());
		}
		return count;
	}
	
	public List<Map<String, Object>> getExcelData(String user,String authority,String startTime,String endTime) {
		DBUtil db = new DBUtil();
		Object[] param = null;
		String sql = "";
		if(authority.equals("no")){
			sql = "select @rowno:=@rowno+1 as rowno,r.* from t_staff_application r,(select @rowno:=0) t where RealName=? ";
			param = new Object[] { user };
			if(!startTime.equals("")){
				sql=sql.concat(" AND DATE_FORMAT(StartTime,'%Y-%m') between ? and ?");
				param = new Object[] { user,startTime,endTime};
			}
		}else if(authority.equals("all")){
			sql = "select @rowno:=@rowno+1 as rowno,r.* from t_staff_application r,(select @rowno:=0) t ";
		
			if(!startTime.equals("")){
				sql=sql.concat(" where DATE_FORMAT(StartTime,'%Y-%m') between ? and ?");
				param = new Object[] {startTime,endTime};
			}
		}else{
			sql = "select @rowno:=@rowno+1 as rowno,r.* from t_staff_application r,(select @rowno:=0) t where RealName in(Select StaffName from t_staff "
					+ "where Department = (select Department from t_staff where StaffName=?)) ";
				
			param = new Object[] { user};
			if(!startTime.equals("")){
				sql=sql.concat(" AND DATE_FORMAT(StartTime,'%Y-%m') between ? and ?");
				param = new Object[] { user,startTime,endTime};
			}
		}
		sql = sql.concat(" ORDER BY ID");
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		return ls;
	}

	public boolean insert(StaffApplication staff){
		DBUtil db = new DBUtil();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "insert into t_staff_application (RealName,Department,StartTime,EndTime,Classify,Reason,"
				+ "Review,FailedReason,CopyList,MailContent,ToList,OperatingTime,MailContentText,Eliminate,Password) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] param = new Object[15];
		param[0] = staff.getRealName();
		param[1] = staff.getDepartment();
		param[2] = staff.getStartTime();
		param[3] = staff.getEndTime();
		param[4] = staff.getClassify();
		param[5] = staff.getReason();
		param[6] = staff.getReview();
		param[7] = staff.getFailedReason();
		param[8] = staff.getCopyList();
		param[9] = staff.getMailContent();
		param[10] = staff.getToList();
		param[11] = df.format(new Date());
		param[12] = staff.getMailContentText();
		param[13] = staff.getEliminate();
		param[14] = staff.getPassword();
		int count = db.executeUpdate(sql, param);
		boolean flag = false;
		if(count>0){
			flag = true;
		}
		return flag;
	}
	public boolean update(StaffApplication staff){
		System.out.println(staff);
		DBUtil db = new DBUtil();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "update t_staff_application set RealName=?,Department=?,StartTime=?,EndTime=?,Classify=?,Reason=?,"
				+ "Review=?,FailedReason=?,CopyList=?,MailContent=?,OperatingTime=?,ToList=?,MailContentText=? where ID=?";
		Object[] param = new Object[14];
		param[0] = staff.getRealName();
		param[1] = staff.getDepartment();
		param[2] = staff.getStartTime();
		param[3] = staff.getEndTime();
		param[4] = staff.getClassify();
		param[5] = staff.getReason();
		param[6] = staff.getReview();
		param[7] = staff.getFailedReason();
		param[8] = staff.getCopyList();
		param[9] = staff.getMailContent();
		param[10] = df.format(new Date());
		param[11] = staff.getToList();
		param[12] = staff.getMailContentText();
		param[13] = staff.getID();

		int count = db.executeUpdate(sql, param);
		boolean flag = false;
		if(count>0){
			flag = true;
		}
		return flag;
	}
	
	public boolean updateReview(String content,int id,String reason){
		DBUtil db = new DBUtil();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "update t_staff_application set Review=?,ReviewTime=?,FailedReason=? where ID=?";
		Object[] param = new Object[4];
		param[0] = content;
		param[1] = df.format(new Date());
		param[2] = reason;
		param[3] = id;
		System.out.println(content);
		System.out.println(id);
		System.out.println(reason);
		int count = db.executeUpdate(sql, param);
		boolean flag = false;
		if(count>0){
			flag = true;
		}
		return flag;
	}
	
	public List<Map<String, Object>> getDataByID(int id){
		DBUtil db = new DBUtil();
		String sql = "select ID,RealName,Department,Classify,Reason,StartTime,EndTime,Review,ToList,CopyList,Password,MailContentText FROM t_staff_application where ID=?";
		Object[] param = new Object[] { id };
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		return ls;
	}
	
	public boolean delete (int id){
		DBUtil db = new DBUtil();
		String sql = "delete from t_staff_application where ID=?";
		Object[] param = new Object[] { id };
		int count = db.executeUpdate(sql, param);
		return count>0?true:false;
	}
	
	public boolean updateEliminate(int id,String EliminateTime){
		DBUtil db = new DBUtil();
		String sql = "update t_staff_application set Eliminate = ? where ID=?";
		Object[] param = new Object[] { EliminateTime,id };
		int count = db.executeUpdate(sql, param);
		return count>0?true:false;
		
		
	}
	

}

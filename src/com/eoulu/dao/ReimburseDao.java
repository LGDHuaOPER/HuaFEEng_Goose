package com.eoulu.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.Reimburse;
import com.eoulu.util.DBUtil;

public class ReimburseDao {
	
	public List<Map<String, Object>> getAllData(Page page,String startTime,String endTime) {
		DBUtil db = new DBUtil();
		Object[] param = null;
	
	
		String sql = "select ID,Name,Department,TotalAmount,FilingDate,Pass,BillScreenshot,"
				+ "ElectronicInvoice,TravelPaper,Others from t_reimburse ";
		if(!startTime.equals("")){
			sql += "where DATE_FORMAT(FilingDate,'%Y-%m') between ? and ? order by ID DESC limit ?,?";
			param = new Object[] { startTime,endTime,(page.getCurrentPage() - 1)*10, page.getRows() };
		}else{
			sql += "order by ID DESC limit ?,?";
			param = new Object[] {(page.getCurrentPage() - 1)*10, page.getRows() };

		}
		
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		return ls;
	}
	
	public List<Map<String, Object>> getOnlyData(Page page,String startTime,String endTime,String email) {
		DBUtil db = new DBUtil();
		Object[] param = null;
	
	
		String sql = "select ID,Name,Department,TotalAmount,FilingDate,Pass,BillScreenshot,"
				+ "ElectronicInvoice,TravelPaper,Others from t_reimburse where Name = (select StaffName "
				+ "from t_staff where StaffMail = ?)";
		if(!startTime.equals("")){
			sql += " and DATE_FORMAT(FilingDate,'%Y-%m') between ? and ? order by ID DESC limit ?,?";
			param = new Object[] { email,startTime,endTime,(page.getCurrentPage() - 1)*10, page.getRows() };
		}else{
			sql += " order by ID DESC limit ?,?";
			param = new Object[] {email,(page.getCurrentPage() - 1)*10, page.getRows() };

		}
		
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		return ls;
	}

	public int getCounts(String startTime,String endTime) {
		DBUtil db = new DBUtil();
		Object[] param = null;
		String sql = "select count(ID) Count from t_reimburse ";
		if(!startTime.equals("")){
			sql += "where DATE_FORMAT(FilingDate,'%Y-%m') between ? and ? ";
			param = new Object[] { startTime,endTime};
		}
	
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		int count = 0;
		if (ls.size() > 1) {
			count = Integer.parseInt(ls.get(1).get("Count").toString());
		}
		return count;
	}
	
	public int getOnlyCounts(String startTime,String endTime,String email) {
		DBUtil db = new DBUtil();
		Object[] param = new Object[]{email};
		String sql = "select count(ID) Count from t_reimburse where Name = (select StaffName "
				+ "from t_staff where StaffMail = ?)";
		if(!startTime.equals("")){
			sql += " and DATE_FORMAT(FilingDate,'%Y-%m') between ? and ? ";
			param = new Object[] { email,startTime,endTime};
		}
	
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		int count = 0;
		if (ls.size() > 1) {
			count = Integer.parseInt(ls.get(1).get("Count").toString());
		}
		return count;
	}
	
	
	public List<Map<String,Object>> getDetails(int RequestID){
		String sql = "select ID,Type,Amount,MainContent,CustomerName,City,Time,Attachment from t_reimburse_details "
				+ "where RequestID=?";
		DBUtil dbUtil = new DBUtil();
		return dbUtil.QueryToList(sql, new Object[]{RequestID});
		
	}
	
	public List<Map<String,Object>> getTravel(int RequestID){
		String sql = "select ID,TravelPlace,MainContent,TravelTime,Days from t_reimburse_travel "
				+ "where RequestID=?";
		DBUtil dbUtil = new DBUtil();
		return dbUtil.QueryToList(sql, new Object[]{RequestID});
		
	}
	
	public int insertRequest(Reimburse reimburse, DBUtil db) throws NumberFormatException, Exception{
		String sql = "insert into t_reimburse(Name,Department,TotalAmount,FilingDate,Pass) values(?,?,?,?,?)";
		Object[] param = new Object[5];
		param[0] = reimburse.getName();
		param[1] = reimburse.getDepartment();
		param[2] = reimburse.getTotalAmount();
		param[3] = reimburse.getFilingDate();
		param[4] = "未审核";

		int result = Integer.parseInt(db.insertGetIdNotClose(sql, param).toString());
		return result;
	
	}
	
	public boolean updateRequest(Reimburse reimburse, DBUtil db) throws SQLException{
		String sql = "update t_reimburse set Name=?,Department=?,TotalAmount=?,Pass=? where ID=?";
		Object[] param = new Object[5];
		param[0] = reimburse.getName();
		param[1] = reimburse.getDepartment();
		param[2] = reimburse.getTotalAmount();
		param[3] = reimburse.getPass();
		param[4] = reimburse.getID();
		int result = db.executeUpdateNotClose(sql, param);
		if (result > 0){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean insertDetails(Map<String, Object> detail, DBUtil db) throws SQLException{
		String sql = "insert into t_reimburse_details(Type,Amount,MainContent,CustomerName,City,Time,RequestID) values(?,?,?,?,?,?,?)";
		Object[] param = new Object[7];
		
		param[0] = detail.get("Type");
		param[1] = detail.get("Amount");
		param[2] = detail.get("MainContent");
		param[3] = detail.get("CustomerName");
		param[4] = detail.get("City");
		param[5] = detail.get("Time");
		param[6] = detail.get("RequestID");
		
		int result = db.executeUpdateNotClose(sql, param);
		if (result > 0){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean insertTravel(Map<String, Object> travel, DBUtil db) throws SQLException{
		String sql = "insert into t_reimburse_travel(TravelPlace,MainContent,TravelTime,Days,RequestID) values(?,?,?,?,?)";
		Object[] param = new Object[5];
		param[0] = travel.get("TravelPlace");
		param[1] = travel.get("MainContent");
		param[2] = travel.get("TravelTime");
		param[3] = travel.get("Days");
		param[4] = travel.get("RequestID");

		int result = db.executeUpdateNotClose(sql, param);
		if (result > 0){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean deleteDetails(int RequestID,DBUtil db) throws SQLException{
		String sql = "delete from t_reimburse_details where RequestID=?";
		int result = db.executeUpdateNotClose(sql,new Object[]{RequestID} );
		if (result > 0){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean deleteTravels(int RequestID,DBUtil db) throws SQLException{
		String sql = "delete from t_reimburse_travel where RequestID=?";
		int result = db.executeUpdateNotClose(sql,new Object[]{RequestID} );
		if (result > 0){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean saveFileName(Reimburse reimburse,DBUtil db) throws SQLException{
		String sql = "update t_reimburse set ElectronicInvoice=?,TravelPaper=?,Others=?"
				+ " where ID = ?";
		Object[] param = new Object[4];

		param[0] = reimburse.getElectronicInvoice();
		param[1] = reimburse.getTravelPaper();
		param[2] = reimburse.getOthers();
		param[3] = reimburse.getID();
		int result = db.executeUpdateNotClose(sql,param);
		if (result > 0){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean updateReview(int ID,String state){
		String sql = "update t_reimburse set Pass = ? where ID = ?";
		DBUtil dbUtil = new DBUtil();
		int result = dbUtil.executeUpdate(sql, new Object[]{state,ID});
		return result > 0?true:false;
	}
	
	public boolean addNoticeMail(int month,String publishDate,String state){
		String sql = "insert into t_reimburse_mail(Month,PublishDate,State) values(?,?,?)";
		DBUtil dbUtil = new DBUtil();
		int result = dbUtil.executeUpdate(sql, new Object[]{month,publishDate,state});
		return result > 0?true:false;
	}
	
	public List<Map<String, Object>> getInfo(int ID){
		String sql = "select Name,FilingDate from t_reimburse where ID=?";
		DBUtil dbUtil = new DBUtil();
		return dbUtil.QueryToList(sql, new Object[]{ID});
	}
	
	public List<Map<String, Object>> getList(Page page){
		String sql = "select ID,Month,PublishDate,State from t_reimburse_mail order by ID desc limit ?,? ";
		DBUtil dbUtil = new DBUtil();
		return dbUtil.QueryToList(sql, new Object[]{(page.getCurrentPage() - 1)*10, page.getRows()});
		
	}
	
	public List<Map<String, Object>> getListCount(){
		String sql = "select count(ID) Count from t_reimburse_mail ";
		DBUtil dbUtil = new DBUtil();
		return dbUtil.QueryToList(sql, null);
		
	}
	
	public List<Map<String, Object>> getAllID(String startTime,String endTime){
		String sql = "select ID from t_reimburse";
		Object[] param = null;
		if(!startTime.equals("")){
			sql += " where DATE_FORMAT(FilingDate,'%Y-%m') between ? and ?";
			param = new Object[] { startTime,endTime};
		}
		DBUtil dbUtil = new DBUtil();
		return dbUtil.QueryToList(sql, param);
	}
	
	
	public boolean updateAttachment(int detailID,String fileName,DBUtil db) throws SQLException{
		String sql = "update t_reimburse_details set Attachment = ? where ID=?";
		int result = db.executeUpdateNotClose(sql, new Object[]{fileName,detailID});
		return result > 0?true:false;
		
	}



}

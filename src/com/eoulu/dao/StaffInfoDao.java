package com.eoulu.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.StaffInfo;
import com.eoulu.util.DBUtil;

public class StaffInfoDao {

	public List<Map<String, Object>> getAllData(Page page) {
		String sql = "SELECT * FROM t_staff ORDER BY ID DESC LIMIT ?,?";
		DBUtil db = new DBUtil();
		Object[] param = new Object[] { (page.getCurrentPage() - 1) * page.getRows(), page.getRows() };
		List<Map<String, Object>> ls = db.QueryToList(sql, param);

		return ls;
	}
	public List<StaffInfo> getAllDataForExcel(){
		String sql = " SELECT @rowno:=@rowno+1 as rowno,r.* from t_staff r,(select @rowno:=0) t ORDER BY ID ASC";
		DBUtil db = new DBUtil();
		ResultSet rs = db.Query(sql);
		List<StaffInfo> list = new ArrayList<>();
		try {
			while(rs.next()){
				StaffInfo staffInfo = new StaffInfo();
				staffInfo.setID(rs.getInt("rowno"));
				staffInfo.setStaffName(rs.getString("StaffName"));
				staffInfo.setStaffCode(rs.getString("StaffCode"));
				staffInfo.setStaffMail(rs.getString("StaffMail"));

				staffInfo.setGender(rs.getString("Gender"));
				staffInfo.setJob(rs.getString("Job"));
				staffInfo.setEntryDate(rs.getString("EntryDate"));
				staffInfo.setLinkTel(rs.getString("LinkTel"));
				staffInfo.setIDCard(rs.getString("IDCard"));
				staffInfo.setNation(rs.getString("Nation"));
				staffInfo.setNativePlace(rs.getString("NativePlace"));
				staffInfo.setPoliticalStatus(rs.getString("PoliticalStatus"));
				staffInfo.setGraduateInstitutions(rs.getString("GraduateInstitutions"));
				staffInfo.setMajor(rs.getString("Major"));
				staffInfo.setWorkPlace(rs.getString("WorkPlace"));
				staffInfo.setDepartment(rs.getString("Department"));
				staffInfo.setEducationalBackground(rs.getString("EducationalBackground"));
				staffInfo.setPassport(rs.getString("Passport"));
				staffInfo.setDetailAddress(rs.getString("DetailAddress"));
				staffInfo.setAccountNumber(rs.getString("AccountNumber"));
				staffInfo.setDepositBank(rs.getString("DepositBank"));
				
				list.add(staffInfo);	
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		return list;
		
		
		
	}

	public int getAllCounts() {
		String sql = "SELECT Count(ID) FROM t_staff ";
		DBUtil db = new DBUtil();
		Object[] param = null;
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		int count = 0;
		if (ls.size() > 1) {
			count = Integer.parseInt(ls.get(1).get("Count(ID)").toString());
		}
		return count;
	}
	public List<Map<String, Object>> singleQuery(Page page,String column,String content){
		String sql = "SELECT * FROM t_staff WHERE " + column + " like ? ORDER BY ID DESC LIMIT ?,?";
		DBUtil dbUtil = new DBUtil();
		Object[] param = new Object[3];
		param[0] = "%"+content+"%";
		param[1] = (page.getCurrentPage() - 1) * page.getRows();
		param[2] = page.getRows();
		List<Map<String, Object>> ls = dbUtil.QueryToList(sql, param);
		return ls;
						
	}
	
	public int getSingleQueryCount(String column,String content){
		String sql = "SELECT Count(ID) FROM t_staff WHERE " + column + " like ?";
		DBUtil dbUtil = new DBUtil();
		Object[] param = new Object[1];
	
		param[0] = "%"+content+"%";
		List<Map<String, Object>> ls = dbUtil.QueryToList(sql, param);
		int count = 0;
		if (ls.size() > 1) {
			count = Integer.parseInt(ls.get(1).get("Count(ID)").toString());
		}
		return count;
	}

	public List<Map<String,Object>> maxQuery(Page page,String column1,String content1,String column2,String content2){
		String sql = "SELECT * FROM t_staff WHERE " + column1 + " like ? AND " + column2 + " like ? ORDER BY ID DESC LIMIT ?,?";
		DBUtil dbUtil = new DBUtil();
		Object[] param = new Object[4];
	
		param[0] = "%"+content1+"%";
		param[1] = "%"+content2+"%";
		param[2] = (page.getCurrentPage() - 1) * page.getRows();
		param[3] = page.getRows();
		List<Map<String, Object>> ls = dbUtil.QueryToList(sql, param);
		return ls;
	}
	public int getMaxQueryCount(String column1,String content1,String column2,String content2){
		String sql = "SELECT Count(ID) FROM t_staff WHERE " + column1 + " like ? AND " + column2 + " like ?";
		DBUtil dbUtil = new DBUtil();
		Object[] param = new Object[2];

		param[0] = "%"+content1+"%";
		param[1] = "%"+content2+"%";
		List<Map<String, Object>> ls = dbUtil.QueryToList(sql, param);
		int count = 0;
		if (ls.size() > 1) {
			count = Integer.parseInt(ls.get(1).get("Count(ID)").toString());
		}
		return count;
	}

	public boolean insert(StaffInfo info) {
		boolean flag = false;
		String sql = "INSERT INTO t_staff (StaffName,IDCard,Nation,NativePlace,PoliticalStatus,LinkTel,"
				+ "GraduateInstitutions,Major,DetailAddress,EntryDate,Job,Department,WorkPlace,StaffCode,"
				+ "Other,EducationalBackground,Passport,Gender,StaffMail,AccountNumber,DepositBank,Remarks,"
				+ "IDCardFile,PassportFile) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] param = new Object[24];
		param[0] = info.getStaffName();
		param[1] = info.getIDCard();
		param[2] = info.getNation();
		param[3] = info.getNativePlace();
		param[4] = info.getPoliticalStatus();
		param[5] = info.getLinkTel();
		param[6] = info.getGraduateInstitutions();
		param[7] = info.getMajor();
		param[8] = info.getDetailAddress();
		param[9] = info.getEntryDate();
		param[10] = info.getJob();
		param[11] = info.getDepartment();
		param[12] = info.getWorkPlace();
		param[13] = info.getStaffCode();
		param[14] = info.getOther();
		param[15] = info.getEducationalBackground();
		param[16] = info.getPassport();
		param[17] = info.getGender();
		param[18] = info.getStaffMail();
		param[19] = info.getAccountNumber();
		param[20] = info.getDepositBank();
		param[21] = info.getRemarks();
		param[22] = info.getIDCardFile();
		param[23] = info.getPassportFile();
				
		DBUtil db = new DBUtil();
		int count = db.executeUpdate(sql, param);
		if (count > 0) {
			flag = true;
		}
		return flag;
	}
	public boolean update(StaffInfo info) {
		boolean flag = false;
		String sql = "update t_staff set StaffName=?,IDCard=?,Nation=?,NativePlace=?,PoliticalStatus=?,LinkTel=?,"
				+ "GraduateInstitutions=?,Major=?,DetailAddress=?,EntryDate=?,Job=?,Department=?,WorkPlace=?,StaffCode=?,"
				+ "Other=?,EducationalBackground=?,Passport=?,Gender=?,StaffMail=?,AccountNumber=?,DepositBank=?,Remarks=?,"
				+ "IDCardFile=?,PassportFile=? where ID=?";
		Object[] param = new Object[25];
		param[0] = info.getStaffName();
		param[1] = info.getIDCard();
		param[2] = info.getNation();
		param[3] = info.getNativePlace();
		param[4] = info.getPoliticalStatus();
		param[5] = info.getLinkTel();
		param[6] = info.getGraduateInstitutions();
		param[7] = info.getMajor();
		param[8] = info.getDetailAddress();
		param[9] = info.getEntryDate();
		param[10] = info.getJob();
		param[11] = info.getDepartment();
		param[12] = info.getWorkPlace();
		param[13] = info.getStaffCode();
		param[14] = info.getOther();
		param[15] = info.getEducationalBackground();
		param[16] = info.getPassport();
		param[17] = info.getGender();
		param[18] = info.getStaffMail();
		param[19] = info.getAccountNumber();
		param[20] = info.getDepositBank();
		param[21] = info.getRemarks();
		param[22] = info.getIDCardFile();
		param[23] = info.getPassportFile();
		param[24] = info.getID();
		DBUtil db = new DBUtil();
		int count = db.executeUpdate(sql, param);
		if (count > 0) {
			flag = true;
		}
		return flag;
	}
	public boolean delete(int ID){
		boolean flag = false;
		DBUtil db = new DBUtil();
		String sql = "delete from t_staff where ID = ?";
		Object[] param = new Object[1];
		param[0] = ID;
		int count = db.executeUpdate(sql, param);
		if (count > 0) {
			flag = true;
		}
		return flag;
		
	}
	
	public List<Map<String,Object>> getTelAndName(String email){
		String sql = "select LinkTel,StaffName from t_staff where StaffMail = ?";
		DBUtil db = new DBUtil();
		Object[] param = new Object[]{email};
		List<Map<String,Object>> ls = db.QueryToList(sql, param);
		return ls;
		
	}
	
	public List<Map<String,Object>> getStaffName(String keyWord){
		String sql = "select StaffName from t_staff where StaffName like ?";
		DBUtil db = new DBUtil();
		Object[] param = new Object[]{"%"+keyWord+"%"};
		List<Map<String,Object>> ls = db.QueryToList(sql, param);
		return ls;
	}
	
	public List<Map<String,Object>> getNameANDMail(String keyWord){
		String sql = "select StaffName,StaffMail from t_staff where StaffName like ? or StaffMail like ?";
		DBUtil db = new DBUtil();
		Object[] param = new Object[]{"%"+keyWord+"%",keyWord+"%"};
		List<Map<String,Object>> ls = db.QueryToList(sql, param);
		return ls;
	}
	
	public List<Map<String,Object>> getMail(String name){
		String sql = "select StaffMail from t_staff where StaffName = ?";
		DBUtil db = new DBUtil();
		System.out.println("name"+name);
		Object[] param = new Object[]{name};
		List<Map<String,Object>> ls = db.QueryToList(sql, param);
		return ls;
		
	}
	

	
	

	
}

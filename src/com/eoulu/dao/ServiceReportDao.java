package com.eoulu.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.ServiceReport;
import com.eoulu.util.DBUtil;
import com.mysql.jdbc.Connection;

public class ServiceReportDao {
	private static Map<String,String> map;
	static{
		map = new HashMap<>();
		map.put("报告编号", "Number");
		map.put("项目", "Project");
		map.put("最终用户名称", "CustomerTitle");
		map.put("最终用户姓名", "CustomerName");
		map.put("联系方式", "LinkInfo");
		map.put("业务员姓名", "StaffName");
		
	}
	public List<Map<String, Object>> getAllData(Page page,String type,String column1,String content1,String column2,String content2){
		String sql = "select ID,Number,Project,CustomerTitle,CustomerName,LinkInfo,StaffName,ContractNo,ProductVersion,"
				+ "FileName from t_service_report ";
		Object[] parameter = new Object[]{(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		if(type.equals("singleSelect")){
			sql += "where "+map.get(column1)+" like ?";
			parameter = new Object[]{"%"+content1+"%",(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		}else if(type.equals("mixSelect")){
			sql += "where "+map.get(column1)+" like ? and "+map.get(column2)+" like ?";
			parameter = new Object[]{"%"+content1+"%","%"+content2+"%",(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		}
		sql += " order by OperateTime desc limit ?,?";
		return new DBUtil().QueryToList(sql, parameter);
				
	}
	
	public int getCounts(String type,String column1,String content1,String column2,String content2){
		String sql = "select count(ID) Count from t_service_report ";
		Object[] parameter = null;
		if(type.equals("singleSelect")){
			sql += "where "+map.get(column1)+" like ?";
			parameter = new Object[]{"%"+content1+"%"};
		}else if(type.equals("mixSelect")){
			sql += "where "+map.get(column1)+" like ? and "+map.get(column2)+" like ?";
			parameter = new Object[]{"%"+content1+"%","%"+content2+"%"};
		}
		List<Map<String, Object>> list = new DBUtil().QueryToList(sql, parameter);
		return Integer.parseInt(list.get(1).get("Count").toString());
	}
	
	public boolean insert(ServiceReport report){
		String sql = "insert into t_service_report(Number,Project,CustomerTitle,CustomerName,LinkInfo,StaffName,ContractNo,ProductVersion,"
				+ "OperateTime,FileName) values(?,?,?,?,?,?,?,?,?,?)";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Object[] param = new Object[10];
		param[0] = report.getNumber();
		param[1] = report.getProject();
		param[2] = report.getCustomerTitle();
		param[3] = report.getCustomerName();
		param[4] = report.getLinkInfo();
		param[5] = report.getStaffName();
		param[6] = report.getContractNo();
		param[7] = report.getProductVersion();
		param[8] = format.format(new Date());
		param[9] = report.getFileName();
		
		int result = new DBUtil().executeUpdate(sql, param);
		return result > 0?true:false;
		
	}
	
	public boolean update(ServiceReport report){
		String sql = "update t_service_report set Number = ?,Project=?,CustomerTitle=?,CustomerName=?,LinkInfo=?,"
				+ "StaffName=?,ContractNo=?,ProductVersion=?,OperateTime=?,FileName = ?  where ID=?";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Object[] param = new Object[11];
		param[0] = report.getNumber();
		param[1] = report.getProject();
		param[2] = report.getCustomerTitle();
		param[3] = report.getCustomerName();
		param[4] = report.getLinkInfo();
		param[5] = report.getStaffName();
		param[6] = report.getContractNo();
		param[7] = report.getProductVersion();
		param[8] = format.format(new Date());
		param[9] = report.getFileName();
		param[10] = report.getID();
		
		int result = new DBUtil().executeUpdate(sql, param);
		return result > 0?true:false;
		
	}
	
	public List<Map<String, Object>> getStaffInfo(String email){
		String sql = "select StaffCode,StaffName from t_staff where StaffMail = ?";
		return new DBUtil().QueryToList(sql, new Object[]{email});
	}
	
	public List<Map<String,Object>> getNumber(String staffName){
		String sql = "select Number from t_service_report where ID=(select max(ID) from t_service_report "
				+ "where StaffName = ?)";
		return new DBUtil().QueryToList(sql, new Object[]{staffName});
		
	}
	
	public List<Map<String, Object>> preview(int reportID){
		String sql = "select ID,ServiceItem,Isfinished,Remarks,ConfirmedSignature,ConfirmDate from "
				+ "t_report_preview where ReportID=?";
		return new DBUtil().QueryToList(sql, new Object[]{reportID});
				
	}
	
	public boolean savePreview(List<Map<String,Object>> list,int reportID){
		DBUtil dbUtil = new DBUtil();
		String sql1 = "delete from t_report_preview where ReportID=?";
		String sql2 = "insert into t_report_preview(ReportID,ServiceItem,Isfinished,Remarks,"
				+ "ConfirmedSignature,ConfirmDate) values(?,?,?,?,?,?)";
		Connection connection = dbUtil.getConnection();
		try {
			connection.setAutoCommit(false);
			dbUtil.executeUpdateNotClose(sql1, new Object[]{reportID});
			for(int i = 0;i < list.size();i ++){
				dbUtil.executeUpdateNotClose(sql2, new Object[]{reportID,list.get(i).get("ServiceItem"),
						list.get(i).get("Isfinished"),list.get(i).get("Remarks"),list.get(i).get("ConfirmedSignature"),
						list.get(i).get("ConfirmDate")});
			}
			connection.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
		
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}finally {
			dbUtil.closed();
		}
	}
	
	public boolean deleteRecord(int ID){
		String sql = "delete from t_service_report where ID=?";
		int result = new DBUtil().executeUpdate(sql, new Object[]{ID});
		return result>0?true:false;
	}
	
	
	

}

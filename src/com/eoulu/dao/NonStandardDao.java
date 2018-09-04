package com.eoulu.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.NonStandard;
import com.eoulu.util.DBUtil;

public class NonStandardDao {
private static Map<String, String> map;
	
	static{
		map = new HashMap<>();
		map.put("项目名称", "ProjectName");
		map.put("客户名称", "t_customer.CustomerName");
		map.put("负责人", "ResponsibleMan");
		map.put("项目阶段", "ProjectStage");
		map.put("发布时间", "PublishDate");
		map.put("阶段预计完成时间", "StageExpectedDate");
		map.put("项目预计完成时间", "ProjectExpectedDate");
		map.put("阶段实际完成时间","StageActualDate");
	}
		
	
	public List<Map<String, Object>> getDataByPage(Page page,String type,String column1,String content1,String column2,String content2){
		DBUtil dbUtil = new DBUtil();
		String sql = "select t_non_standard_project.ID,CustomerID,t_customer.CustomerName,ProjectName,ProjectStage,Issuer,ResponsibleMan,PublishDate,"
				+ "StageExpectedDate,ProjectExpectedDate,ProjectProgress,StageActualDate,isPublished from t_non_standard_project"	
				+ " left join t_customer on t_non_standard_project.CustomerID=t_customer.ID ";
		
		Object[] parameter = new Object[]{(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		if(type.equals("singleSelect")){
			sql += "where "+map.get(column1)+" like ?";
	
			parameter = new Object[]{"%"+content1+"%",(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		}else if(type.equals("mixSelect")){
			sql += "where "+map.get(column1)+" like ? and "+map.get(column2)+" like ?";
			parameter = new Object[]{"%"+content1+"%","%"+content2+"%",(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		}
		sql += " order by StageActualDate,PublishDate,StageExpectedDate desc limit ?,?";
		
		List<Map<String, Object>> list = dbUtil.QueryToList(sql, parameter);
		return list;
	}
	
	public int getCounts(String type,String column1,String content1,String column2,String content2){
		DBUtil dbUtil = new DBUtil();
		String sql = "select COUNT(t_non_standard_project.ID) Count from t_non_standard_project "
				+ "left join t_customer on t_non_standard_project.CustomerID=t_customer.ID ";
			
		Object[] parameter = null;
		if(type.equals("singleSelect")){
			sql += "where "+map.get(column1)+" like ?";
			parameter = new Object[]{"%"+content1+"%"};
		}else if(type.equals("mixSelect")){
			sql += "where "+map.get(column1)+" like ? and "+map.get(column1)+" like ?";
			parameter = new Object[]{"%"+content1+"%","%"+content2+"%"};
		}
		
		List<Map<String, Object>> list = dbUtil.QueryToList(sql, parameter);
		return Integer.parseInt(list.get(1).get("Count").toString());
	}
	
	public int insert(NonStandard nonStandard){
		DBUtil dbUtil = new DBUtil();
		String sql = "insert into t_non_standard_project (CustomerID,ProjectName,ProjectStage,Issuer,ResponsibleMan,PublishDate,"
				+ "StageExpectedDate,ProjectExpectedDate,ProjectProgress,StageActualDate,isPublished) values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] param = new Object[11];
		param[0] = nonStandard.getCustomerID();
		param[1] = nonStandard.getProjectName();
		param[2] = nonStandard.getProjectStage();
		param[3] = nonStandard.getIssuer();
		param[4] = nonStandard.getResponsibleMan();
		param[5] = nonStandard.getPublishDate();
		param[6] = nonStandard.getStageExpectedDate();
		param[7] = nonStandard.getProjectExpectedDate();
		param[8] = nonStandard.getProjectProgress();
		param[9] = nonStandard.getStageActualDate();
		param[10] = "未发布";
		int result = 0;
		try {
			result = Integer.parseInt(dbUtil.insertGetId(sql, param).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return result;
		
	}
	public int getMaxID(){
		DBUtil dbUtil = new DBUtil();
		String sql = "select MAX(ID) Max from t_non_standard_project";
		int ID = Integer.parseInt(dbUtil.QueryToList(sql,null).get(1).get("ID").toString());
		return ID;
		
	}
	
	
	public boolean update(NonStandard nonStandard){
		DBUtil dbUtil = new DBUtil();
		String sql = "update t_non_standard_project set CustomerID=?,ProjectName=?,ProjectStage=?,Issuer=?,ResponsibleMan=?,PublishDate=?,"
				+ "StageExpectedDate=?,ProjectExpectedDate=?,ProjectProgress=?,StageActualDate=? where ID = ?";
		Object[] param = new Object[11];
		param[0] = nonStandard.getCustomerID();
		param[1] = nonStandard.getProjectName();
		param[2] = nonStandard.getProjectStage();
		param[3] = nonStandard.getIssuer();
		param[4] = nonStandard.getResponsibleMan();
		param[5] = nonStandard.getPublishDate();
		param[6] = nonStandard.getStageExpectedDate();
		param[7] = nonStandard.getProjectExpectedDate();
		param[8] = nonStandard.getProjectProgress();
		param[9] = nonStandard.getStageActualDate();
		
		param[10] = nonStandard.getID();
		int result = dbUtil.executeUpdate(sql, param);
		return result>0?true:false;
		
	}
	public boolean updateStatus(int ID){
		DBUtil dbUtil = new DBUtil();
		String sql = "update t_non_standard_project set isPublished = '已发布' where ID=?";
		int result = dbUtil.executeUpdate(sql, new Object[]{ID});
		return result > 0?true:false;
	}
	public boolean updateRemindStatus(int ID){
		DBUtil dbUtil = new DBUtil();
		String sql = "update t_non_standard_project set isReminded = 'yes' where ID=?";
		int result = dbUtil.executeUpdate(sql, new Object[]{ID});
		return result > 0?true:false;
	}
	
	public List<NonStandard> getRemindProject(){
		DBUtil dbUtil = new DBUtil();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String today = format.format(new Date());
		
		String sql = "select ID,ResponsibleMan,ProjectName,PublishDate,StageExpectedDate,ProjectProgress,ProjectStage,StageActualDate from "
				+ "t_non_standard_project where StageActualDate = '' and StageExpectedDate > ? and isReminded = 'no'";
		List<Map<String, Object>> list = dbUtil.QueryToList(sql, new Object[]{today});
		List<NonStandard> projects = new ArrayList<>();
		for(int i = 1;i < list.size();i ++){
			NonStandard nonStandard = new NonStandard();
			nonStandard.setID(Integer.parseInt(list.get(i).get("ID").toString()));
			nonStandard.setResponsibleMan(list.get(i).get("ResponsibleMan").toString());
			nonStandard.setProjectName(list.get(i).get("ProjectName").toString());
			nonStandard.setPublishDate(list.get(i).get("PublishDate").toString());
			nonStandard.setStageExpectedDate(list.get(i).get("StageExpectedDate").toString());
			nonStandard.setProjectProgress(list.get(i).get("ProjectProgress").toString());
			nonStandard.setProjectStage(list.get(i).get("ProjectStage").toString());
			nonStandard.setStageActualDate(list.get(i).get("StageActualDate").toString());
			projects.add(nonStandard);
		}
		return projects;
		
	}
	
	public boolean deleteProject(int ID){
		String sql = "delete from t_non_standard_project where ID = ?";
		int result = new DBUtil().executeUpdate(sql, new Object[]{ID});
		return result>0?true:false;
	}
	
	public List<Map<String, Object>> getProjectName(String key){
		String sql = "select ProjectName from t_non_standard_project where ProjectName like ?";
		return new DBUtil().QueryToList(sql, new Object[]{"%"+key+"%"});
	}
	
	


}

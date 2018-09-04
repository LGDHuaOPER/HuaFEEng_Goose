package com.eoulu.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.ProjectDocument;
import com.eoulu.entity.SoftwareProject;
import com.eoulu.util.DBUtil;

public class SoftwareProjectDao {
	
	private static Map<String, String> Classify_Map;
	
	static{
		Classify_Map = new HashMap<>();
		Classify_Map.put("产品", "Product");
		Classify_Map.put("开发说明书", "Instructions");
		Classify_Map.put("UI规范", "UISpecification");
		Classify_Map.put("优先级", "Priority");
		Classify_Map.put("提交人", "Submitter");
		Classify_Map.put("前端", "Front");
		Classify_Map.put("后台", "Back");
		Classify_Map.put("UI设计", "UIDesigner");
		Classify_Map.put("主导人", "Leader");
		Classify_Map.put("提交时间", "SubmissionTime");
		Classify_Map.put("规划时间", "PlanningTime");
		Classify_Map.put("交付时间", "LeadTime");
		Classify_Map.put("延期时间", "DelayTime");
		Classify_Map.put("状态", "State");
		Classify_Map.put("备注", "Remark");
		Classify_Map.put("序号", "IndexID");

	}
	
	
	public List<Map<String, Object>> getAllData(Page page,String column,String content,String order) {
		String sql = "SELECT * FROM t_software_project ";
		Object[] param = new Object[] { (page.getCurrentPage() - 1) * page.getRows(), page.getRows() };

		if(!column.equals("")){
			switch (column) {
			case "延期时间":
				if(!content.equals("")){
					sql += "where "+Classify_Map.get(column)+ " = ?";
					param = new Object[] { content,(page.getCurrentPage() - 1) * page.getRows(), page.getRows() };
				}
				break;
			case "序号":
				if(!content.equals("")){
					sql += "where "+Classify_Map.get(column)+ " = ?";
					param = new Object[] { content,(page.getCurrentPage() - 1) * page.getRows(), page.getRows() };
				}
	
				break;

			default:
				sql += "where "+Classify_Map.get(column)+ " like ?";
				param = new Object[] { "%"+content+"%",(page.getCurrentPage() - 1) * page.getRows(), page.getRows() };

				break;
			}
			
			sql += " ORDER BY "+Classify_Map.get(column)+" "+order+" LIMIT ?,?";
		
			
		}else{
			sql += "ORDER BY ID DESC LIMIT ?,?";
		}
		DBUtil db = new DBUtil();
		List<Map<String, Object>> ls = db.QueryToList(sql, param);

		return ls;
	}
	public List<SoftwareProject> getDataForExcel(){
		String sql = "SELECT * FROM t_software_project ORDER BY ID DESC";
		DBUtil db = new DBUtil();
		ResultSet rs = db.Query(sql);
		List<SoftwareProject> list = new ArrayList<>();
		try {
			while(rs.next()){
				SoftwareProject project = new SoftwareProject();
				project.setID(rs.getInt("ID"));
				project.setBack(rs.getString("Back"));
				project.setDelayTime(rs.getInt("DelayTime"));
				project.setFront(rs.getString("Front"));
				project.setInstructions(rs.getString("Instructions"));
				project.setLeader(rs.getString("Leader"));
				project.setLeadTime(rs.getString("LeadTime"));
				project.setPlanningTime(rs.getString("PlanningTime"));
				project.setPriority(rs.getString("Priority"));
				project.setProduct(rs.getString("Product"));
				project.setRemark(rs.getString("Remark"));
				project.setState(rs.getString("State"));
				project.setSubmissionTime(rs.getString("SubmissionTime"));
				project.setSubmitter(rs.getString("Submitter"));
				project.setUiDesigner(rs.getString("UIDesigner"));
				project.setUiSpecification(rs.getString("UISpecification"));
				list.add(project);		
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int insert(SoftwareProject project) throws NumberFormatException, Exception{

		DBUtil db = new DBUtil();
		String sql = "INSERT INTO t_software_project (Product,Instructions,UISpecification,Priority,Submitter,Front,Back,UIDesigner,Leader,SubmissionTime,"
				+ "PlanningTime,LeadTime,DelayTime,State,Remark,IndexID) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] param = new Object[16];
		param[0] = project.getProduct();
		param[1] = project.getInstructions();
		param[2] = project.getUiSpecification();
		param[3] = project.getPriority();
		param[4] = project.getSubmitter();
		param[5] = project.getFront();
		param[6] = project.getBack();
		param[7] = project.getUiDesigner();
		param[8] = project.getLeader();
		param[9] = project.getSubmissionTime();
		param[10] = project.getPlanningTime();
		param[11] = project.getLeadTime();
		param[12] = project.getDelayTime();
		param[13] = project.getState();
		param[14] = project.getRemark();
		param[15] = getIndex() + 1;
		int result = Integer.parseInt(db.insertGetId(sql, param).toString());
		
		return result;
		
	}
	
	public boolean update(SoftwareProject project){
		boolean flag = false;

		DBUtil db = new DBUtil();
		String sql = "UPDATE t_software_project set Product=?, Instructions=?,UISpecification=?,Priority=?,Submitter=?,Front=?,Back=?,UIDesigner=?,Leader=?,"
				+ "PlanningTime=?,LeadTime=?,DelayTime=?,State=?,Remark=? WHERE ID=?";
		Object[] param = new Object[15];
	
		param[0] = project.getProduct();
		param[1] = project.getInstructions();
		param[2] = project.getUiSpecification();
		param[3] = project.getPriority();
		param[4] = project.getSubmitter();
		param[5] = project.getFront();
		param[6] = project.getBack();
		param[7] = project.getUiDesigner();
		param[8] = project.getLeader();
		param[9] = project.getPlanningTime();
		param[10] = project.getLeadTime();
		param[11] = project.getDelayTime();
		param[12] = project.getState();
		param[13] = project.getRemark();
		param[14] = project.getID();
		int result = db.executeUpdate(sql, param);

		flag = result>0?true:false;

		return flag;
		
		
	}
	
	public int getAllCounts(String column,String content) {
		DBUtil db = new DBUtil();
		String sql = "select count(ID) ? from t_software_project ";
		Object[] param = new Object[] { "AllCounts"};
		
		if(!column.equals("")){
			switch (column) {
			case "延期时间":
				if(!content.equals("")){
					sql += "where "+Classify_Map.get(column)+ " = ?";
					param = new Object[] {"AllCounts", content };	
				}
				break;
			case "序号":
				if(!content.equals("")){
					sql += "where "+Classify_Map.get(column)+ " = ?";
					param = new Object[] {"AllCounts", content };	
				}
				break;

			default:
				sql += "where "+Classify_Map.get(column)+ " like ?";
				param = new Object[] {"AllCounts", "%"+content+"%" };

				break;
			}
		}
			
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		int counts = 0;
		if (ls.size() > 1) {
			counts = Integer.parseInt(ls.get(1).get(ls.get(0).get("col1")).toString());
		}
		return counts;
	}
	
	
	
    public List<Map<String, Object>> getSoftwareStaff(){
    	DBUtil db = new DBUtil();
    	String sql = "select StaffName from t_staff where Department = '软件部'";
    	Object[] param = null;
    	List<Map<String, Object>> ls = db.QueryToList(sql, param);
    	return ls;
    	
    }
    public List<Map<String, Object>> getDataById(int id){
    	DBUtil db = new DBUtil();
    	String sql = "select * from t_software_project where ID = ?";
    	Object[] param = {id};
    	List<Map<String, Object>> ls = db.QueryToList(sql, param);
    	return ls;			
    }
    
    public boolean addPath(ProjectDocument document){
    	DBUtil db = new DBUtil();
    	String sql1 = "select ProjectID from t_project_document where ProjectID = ? AND Classify = ?";
    	Object[] param1 = {document.getProjectId(),document.getClassify()};
    	List<Map<String, Object>> ls = db.QueryToList(sql1,param1);
    	String sql = "";
    	Object[] param = new Object[3];
    	if(ls.size() > 1){
    		sql = "update t_project_document set Path = ? where ProjectID = ? AND Classify = ?";
        	param[0] = document.getPath();
        	param[1] = document.getProjectId();
        	param[2] = document.getClassify();
    	}else{
    		sql = "insert into t_project_document(ProjectID,Path,Classify) values(?,?,?)";
        	param[0] = document.getProjectId();
        	param[1] = document.getPath();
        	param[2] = document.getClassify();
    	}
    	DBUtil db1 = new DBUtil();
    	int result = db1.executeUpdate(sql, param);
    	return result>0?true:false;
    }
    public boolean updatePath(ProjectDocument document){
    	DBUtil db = new DBUtil();
    	String sql1 = "select ProjectID from t_project_document where ProjectID = ? AND Classify = ?";
    	Object[] param1 = {document.getProjectId(),document.getClassify()};
    	List<Map<String, Object>> ls = db.QueryToList(sql1,param1);
    	String sql = "";
    	Object[] param = new Object[3];
    	if(ls.size() > 1){
    		sql = "update t_project_document set Path = ? where ProjectID = ? AND Classify = ?";
        	param[0] = document.getPath();
        	param[1] = document.getProjectId();
        	param[2] = document.getClassify();
    	}else{
    		sql = "insert into t_project_document(ProjectID,Path,Classify) values(?,?,?)";
        	param[0] = document.getProjectId();
        	param[1] = document.getPath();
        	param[2] = document.getClassify();
    	}
    	DBUtil db1 = new DBUtil();
    	int result = db1.executeUpdate(sql, param);
    	return result>0?true:false;
    }
    public int getMaxID(){
    	DBUtil db = new DBUtil();
    	String sql = "select auto_increment ID from information_schema.TABLES where table_name='t_software_project' and TABLE_SCHEMA='logistics'";
    	List<Map<String, Object>> ls = db.QueryToList(sql,null);
    	return Integer.parseInt(ls.get(1).get("ID").toString());
    	
    }
    public List<Map<String, Object>> getPath(int id,String classify){
    	DBUtil db = new DBUtil();
    	String sql = "select Path from t_project_document where ProjectID = ? and Classify = ?";
    	Object[] param = {id,classify};
    	List<Map<String, Object>> ls = db.QueryToList(sql,param);
    	return ls;
    }
    
    public int getIndex(){
    	DBUtil db = new DBUtil();
    	String sql = "select Max(IndexID) IndexID from t_software_project";
    	List<Map<String, Object>> list = db.QueryToList(sql, null);
    	if(list.size()>1){
    		return Integer.parseInt(list.get(1).get("IndexID").toString());
    	}else{
    		return 1;
    	}
    }
   

}

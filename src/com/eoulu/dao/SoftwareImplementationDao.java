package com.eoulu.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.SoftwareImplementation;
import com.eoulu.entity.SoftwareImplementationServiceContent;
import com.eoulu.util.DBUtil;

public class SoftwareImplementationDao {
	
	public List<Map<String,Object>> getAllData(Page page,String content,String column,String order){
		String sql  = "SELECT t_software_implementation.ID,t_software_implementation.InquiryID,t_customer.CustomerName,t_customer.Contact,t_customer.ContactInfo1,t_area.AreaName,"
				+"t_software_implementation.Machine,t_software_implementation.SoftwareVersion,t_software_implementation.Type,"
				+"t_software_implementation.InstallTime,t_software_implementation.ContinueTime,"
				+"t_software_implementation.Engineer,t_software_implementation.OperatingTime,c.ProgressPercent "
				+"FROM  t_software_implementation Left JOIN (SELECT round(a.count/b.count*100) ProgressPercent,b.ImplementionID ImplementionID from "
				+ "(SELECT count(ID) Count,ImplementionID from t_software_implementation_service_content where CompleteTime != '0000-00-00' group by ImplementionID)a "
				+"RIGHT JOIN (SELECT count(ID) Count,ImplementionID from t_software_implementation_service_content group by ImplementionID)b ON a.ImplementionID=b.ImplementionID)c "
				+"ON c.ImplementionID=t_software_implementation.ID LEFT JOIN t_customer ON t_software_implementation.CustomerID=t_customer.ID "
				+"LEFT JOIN t_area ON t_customer.AreaName = t_area.ID ";
				

		Object[] param = new Object[]{(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
	
		if(!content.equals("")){
			sql += " WHERE t_customer.CustomerName LIKE ? OR t_customer.Contact LIKE ? ";
			param = new Object[]{"%"                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        +content+"%","%"+content+"%",(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		}
		
		if(!column.equals("")){
			switch(column){
				case "progressStatus":column="c.ProgressPercent";break;
				case "CustomerName":column="t_customer."+column;break;
				case "Contact":column="t_customer."+column;break;
				case "AreaName":column="t_area."+column;break;
				default:column="t_software_implementation."+column;
			}
			sql += " ORDER BY "+column+" "+order;
			
		}else{
			sql += " ORDER BY t_software_implementation.OperatingTime DESC ";
		}
		sql += " LIMIT ?,?";
		
		return new DBUtil().QueryToList(sql, param);
	}
	
	public List<Map<String,Object>> getDataByID(int ID){
		String sql  = "SELECT t_software_implementation.ID,t_customer.CustomerName,t_customer.Contact,t_area.AreaName,"
				+ "t_software_implementation.Machine,t_software_implementation.SoftwareVersion,t_software_implementation.Type,"
				+ "t_software_implementation.InstallTime,t_software_implementation.ContinueTime,"
				+ "t_software_implementation.Engineer "
				+ "FROM t_software_implementation LEFT JOIN t_customer ON t_software_implementation.CustomerID=t_customer.ID "
				+ "LEFT JOIN t_area ON t_customer.AreaName = t_area.ID where t_software_implementation.ID=?";
		return new DBUtil().QueryToList(sql, new Object[]{ID});
	}
	
	public List<Map<String, Object>> getServiceContent(int ImplementionID){
		String sql = "select Content,CompleteTime from t_software_implementation_service_content where ImplementionID=? ORDER BY ID DESC";
		List<Map<String,Object>> list = new DBUtil().QueryToList(sql, new Object[]{ImplementionID});
		
	
		return list;
	}
	public int getFinishedCount(int ImplementionID){
		String sql = "select count(ID) Count from t_software_implementation_service_content where ImplementionID=? and CompleteTime != '0000-00-00' group by ImplementionID";
		List<Map<String,Object>> list = new DBUtil().QueryToList(sql, new Object[]{ImplementionID});
		if(list.size()>1){
			return Integer.parseInt(list.get(1).get("Count").toString());
		}else {
			return 0;
		}
	}
	public int getAllCounts(String content,String column){
		String sql = "SELECT COUNT(t_software_implementation.ID) FROM t_software_implementation LEFT JOIN t_customer ON t_software_implementation.CustomerID=t_customer.ID ";
		Object[] param = null;
		if(!content.equals("")){
			sql += " WHERE t_customer.CustomerName LIKE ? OR t_customer.Contact LIKE ? ";
			param = new Object[]{"%"+content+"%","%"+content+"%"};
		}
		List<Map<String,Object>> ls =  new DBUtil().QueryToList(sql, param);
		return ls.size()>1?Integer.parseInt(ls.get(1).get("COUNT(t_software_implementation.ID)").toString()):0;
	}

	public boolean insert(int id,int CustomerID,String preSalesTable){
		String sql = "INSERT INTO t_software_implementation (InquiryID,OperatingTime,PreSalesTable,CustomerID) values (?,?,?,?)";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return new DBUtil().executeUpdate(sql, new Object[]{id,df.format(new Date()),preSalesTable,CustomerID})>0?true:false;
	}
	
	public boolean updateDetail(SoftwareImplementation impl){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "update t_software_implementation set Remarks=?,MachineCode=?,SN=?,RegistrationCode=?,Email=?,"
				+ "InstallationReport=?,ImplementHandbook=?,TechnologyProtocol=?,PreSalesTable=?,ScriptBackup=?,OperatingTime=? where ID=?";
		Object[] param = new Object[12];
		param[0] = impl.getRemarks();
		param[1] = impl.getMachineCode();
		param[2] = impl.getSN();
		param[3] = impl.getRegistrationCode();
		param[4] = impl.getEmail();
		param[5] = impl.getInstallationReport();
		param[6] = impl.getImplementHandbook();
		param[7] = impl.getTechnologyProtocol();
		param[8] = impl.getPreSalesTable();
		param[9] = impl.getScriptBackup();
		param[10] = df.format(new Date());
		param[11] = impl.getID();
		return new DBUtil().executeUpdate(sql, param)>0?true:false;
	}
	public boolean update(SoftwareImplementation impl){
		String sql = "update t_software_implementation set Machine=?,SoftwareVersion=?,Type=?,InstallTime=?,ContinueTime=?,Engineer=? where ID=?";
		Object[] param = new Object[7];
		param[0] = impl.getMachine();
		param[1] = impl.getSoftwareVersion();
		param[2] = impl.getType();
		param[3] = impl.getInstallTime();
		param[4] = impl.getContinueTime();
		param[5] = impl.getEngineer();
		param[6] = impl.getID();
		return new DBUtil().executeUpdate(sql, param)>0?true:false;
	}
	
	public boolean insert(SoftwareImplementation impl){
		String sql = "insert into  t_software_implementation(Machine,SoftwareVersion,Type,InstallTime,ContinueTime,Engineer,CustomerID) values(?,?,?,?,?,?,?)";
		Object[] param = new Object[7];
		param[0] = impl.getMachine();
		param[1] = impl.getSoftwareVersion();
		param[2] = impl.getType();
		param[3] = impl.getInstallTime();
		param[4] = impl.getContinueTime();
		param[5] = impl.getEngineer();
		param[6] = impl.getCustomerID();
		
		return new DBUtil().executeUpdate(sql, param)>0?true:false;
	}
	
	public List<Map<String,Object>> getOperatePreview(int id){//修改中非服务内容的预览
		String sql = "SELECT t_software_implementation.MachineCode,t_software_implementation.SN,t_software_implementation.RegistrationCode,"
				+ "t_software_implementation.Email,t_software_implementation.InstallationReport,"
				+ "t_software_implementation.ImplementHandbook,t_software_implementation.TechnologyProtocol,"
				+ "t_software_implementation.Remarks,"
				+ "t_software_implementation.PreSalesTable,t_software_implementation.ScriptBackup,t_customer.ContactInfo1 FROM t_software_implementation "
				+ " LEFT JOIN t_customer ON t_software_implementation.CustomerID=t_customer.ID"
				+ "  WHERE t_software_implementation.ID=?";
		return new DBUtil().QueryToList(sql, new Object[]{id});
	}
	
	public List<Map<String,Object>> getDetailRecord(int id){//详细记录的预览
		String sql = "SELECT t_software_implementation.MachineCode,t_software_implementation.SN,t_customer.ContactInfo1,"
				+ "t_software_implementation.Email,t_software_implementation.InstallationReport,"
				+ "t_software_implementation.ImplementHandbook,t_software_implementation.PreSalesTable,t_software_implementation.ScriptBackup FROM t_software_implementation "
				+ " LEFT JOIN t_customer ON t_software_implementation.CustomerID=t_customer.ID"
				+ " WHERE t_software_implementation.ID=?";
		return new DBUtil().QueryToList(sql, new Object[]{id});
	}
	
	public boolean insertService(SoftwareImplementationServiceContent service){//服务内容的添加
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String sql = "insert into t_software_implementation_service_content (ServiceItem,Content,RegisterTime,CompleteTime,ExpectedDate,Priority,ResponsibleMan,RequirmentClassify,Description,ServiceReport,ImplementionID) values (?,?,?,?,?,?,?,?,?,?,?)";
		Object[] param = new Object[]{service.getServiceItem(),service.getContent(),df.format(new Date()),service.getCompleteTime(),service.getExpectedDate(),service.getPriority(),service.getResponsibleMan(),service.getRequirmentClassify(),service.getDescription(),service.getServiceReport(),service.getImplementionID()};
		return new DBUtil().executeUpdate(sql, param)>0?true:false;
	}
	
	public boolean updateService(SoftwareImplementationServiceContent service){//服务内容的修改
		String sql = "update t_software_implementation_service_content SET ServiceItem=?,Content=?,CompleteTime=?,ExpectedDate=?,Priority=?,ResponsibleMan=?,RequirmentClassify=?,Description=?,ServiceReport=? WHERE ID=?";
		Object[] param = new Object[]{service.getServiceItem(),service.getContent(),service.getCompleteTime(),service.getExpectedDate(),service.getPriority(),service.getResponsibleMan(),service.getRequirmentClassify(),service.getDescription(),service.getServiceReport(),service.getID()};
		return new DBUtil().executeUpdate(sql, param)>0?true:false;
	}
	
	public List<Map<String,Object>> getPreview(int implementionID){//修改中的预览
		String sql = "SELECT ServiceItem,Content,ExpectedDate,Priority,ResponsibleMan,RequirmentClassify,Description,RegisterTime,CompleteTime,ServiceReport,ID ServiceID FROM t_software_implementation_service_content where ImplementionID=?";
		return new DBUtil().QueryToList(sql, new Object[]{implementionID});
	}
	
	public boolean delete(int id){
		String sql = "delete from t_software_implementation where ID=?";
		return new DBUtil().executeUpdate(sql, new Object[]{id})>0?true:false;
	}
	public boolean deleteService(int id){
		String sql = "delete from t_software_implementation_service_content where ID=?";
		return new DBUtil().executeUpdate(sql, new Object[]{id})>0?true:false;
	}
	
	public List<Map<String,Object>> getServiceID(int id){
		String sql = "SELECT ID FROM t_software_implementation_service_content WHERE ImplementionID=?";
		return new DBUtil().QueryToList(sql, new Object[]{id});
	}
}

package com.eoulu.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;
import com.eoulu.dao.MailConfigDao;
import com.eoulu.dao.SoftwareImplementationDao;
import com.eoulu.dao.StaffInfoDao;
import com.eoulu.entity.SoftwareImplementation;
import com.eoulu.entity.SoftwareImplementationServiceContent;
import com.eoulu.service.SoftwareImplementationService;
import com.eoulu.util.JavaMailToolsUtil;
import com.eoulu.util.MethodUtil;
import com.eoulu.util.SendMailUtil;

public class SoftwareImplementationServiceImpl implements SoftwareImplementationService{

	@Override
	public List<Map<String, Object>> getAllData(Page page, String content, String column, String order) {
		SoftwareImplementationDao dao = new SoftwareImplementationDao();
		List<Map<String, Object>> list = dao.getAllData(page, content, column, order);
		for(int i = 1;i < list.size();i ++){
			int implementionID = Integer.parseInt(list.get(i).get("ID").toString());
			List<Map<String, Object>> serviceContent =  dao.getServiceContent(implementionID);
			String progressStatus = "";
			String completeTime = "";
		

			if(serviceContent.size()>1){
				progressStatus = serviceContent.get(1).get("Content").toString();
				completeTime = serviceContent.get(1).get("CompleteTime").toString();
			
			}
		
			list.get(i).put("progressStatus", progressStatus);
			list.get(i).put("completeTime", completeTime);
			//list.get(i).put("ProgressPercent", ProgressPercent+"%");
		}
	
		return list;
	}

	@Override
	public int getAllCounts(String content, String column) {
		
		return new SoftwareImplementationDao().getAllCounts(content, column);
	}

	@Override
	public String operate(HttpServletRequest request) {
		SoftwareImplementation impl = new SoftwareImplementation();
		SoftwareImplementationDao dao = new SoftwareImplementationDao();
	
		int id = Integer.parseInt(request.getParameter("ID")==null?"0":request.getParameter("ID"));
		int customerID = Integer.parseInt(request.getParameter("CustomerID")==null?"0":request.getParameter("CustomerID"));
		String machine = request.getParameter("Machine")==null?"":request.getParameter("Machine");
		String softwareVersion = request.getParameter("SoftwareVersion")==null?"":request.getParameter("SoftwareVersion");
		String type = request.getParameter("Type")==null?"":request.getParameter("Type");
		String installTime = request.getParameter("InstallTime")==null?"0000-00-00":request.getParameter("InstallTime");
		
		String continueTime = request.getParameter("ContinueTime")==null?"0000-00-00":request.getParameter("ContinueTime");
		String engineer = request.getParameter("Engineer")==null?"":request.getParameter("Engineer");
	
	
		String machineCode = request.getParameter("MachineCode")==null?"":request.getParameter("MachineCode");
		String SN = request.getParameter("SN")==null?"":request.getParameter("SN");
		String registrationCode = request.getParameter("RegistrationCode")==null?"":request.getParameter("RegistrationCode");
		String email = request.getParameter("Email")==null?"":request.getParameter("Email");
		String installationReport = request.getParameter("InstallationReport")==null?"":request.getParameter("InstallationReport");
		String implementHandbook = request.getParameter("ImplementHandbook")==null?"":request.getParameter("ImplementHandbook");
		String technologyProtocol = request.getParameter("TechnologyProtocol")==null?"":request.getParameter("TechnologyProtocol");
		String preSalesTable = request.getParameter("PreSalesTable")==null?"":request.getParameter("PreSalesTable");
		String scriptBackup = request.getParameter("ScriptBackup")==null?"":request.getParameter("ScriptBackup");
		impl.setID(id);
		impl.setMachine(machine);
		impl.setSoftwareVersion(softwareVersion);
		impl.setType(type);
		impl.setInstallTime(installTime.equals("")?"0000-00-00":installTime);
		impl.setContinueTime(continueTime.equals("")?"0000-00-00":continueTime);
		impl.setEngineer(engineer);
	

		impl.setMachineCode(machineCode);
		impl.setSN(SN);
		impl.setRegistrationCode(registrationCode);
		impl.setEmail(email);
		impl.setInstallationReport(installationReport);
		impl.setImplementHandbook(implementHandbook);
		impl.setTechnologyProtocol(technologyProtocol);
		impl.setCustomerID(customerID);
		impl.setPreSalesTable(preSalesTable);
		impl.setScriptBackup(scriptBackup);
		String result = "";
		switch (request.getParameter("classify")) {
		case "Modify":
			result = dao.update(impl)?"提交成功！":"提交失败！";
			break;

		case "Preview":
			result = dao.updateDetail(impl)?"提交成功！":"提交失败！";
			String[] ids = request.getParameterValues("ImplementationID[]")==null?new String[0]:request.getParameterValues("ImplementationID[]");
			delete(ids, id);
			if(ids.length>0){
				String[] serviceItem = request.getParameterValues("ServiceItem[]");
				String[] content = request.getParameterValues("Content[]");
				String[] completeTime = request.getParameterValues("CompleteTime[]");
				String[] expectedDate = request.getParameterValues("ExpectedDate[]");
				String[] priority = request.getParameterValues("Priority[]");
				String[] responsibleMan = request.getParameterValues("ResponsibleMan[]");
				String[] requirmentClassify = request.getParameterValues("RequirmentClassify[]");
				String[] description = request.getParameterValues("Description[]");
				String[] serviceReport = request.getParameterValues("ServiceReport[]");
				for(int i=0;i<ids.length;i++){
					SoftwareImplementationServiceContent con = new SoftwareImplementationServiceContent();
					
					con.setContent(content[i]);
					con.setServiceItem(serviceItem[i]);
					con.setImplementionID(id);
					con.setCompleteTime(completeTime[i].equals("")?"0000-00-00":completeTime[i]);
					con.setID(Integer.parseInt(ids[i]));
					con.setExpectedDate(expectedDate[i].equals("")?"0000-00-00":expectedDate[i]);
					con.setPriority(priority[i]);
					con.setResponsibleMan(responsibleMan[i]);
					con.setRequirmentClassify(requirmentClassify[i]);
					con.setServiceReport(serviceReport[i]);
					con.setDescription(description[i]);
					
					if(ids[i].equals("0")){
						if(!dao.insertService(con)){
							result = "服务登记内容添加失败！";
							break;
						}
					}else{
						if(!dao.updateService(con)){
							result = "服务登记内容修改失败！";
							break;
						}
					}
				}
				if(result.equals("提交成功！")){
					String userEmail = request.getSession().getAttribute("email").toString().trim();
					StaffInfoDao dao2 = new StaffInfoDao();
					List<Map<String,Object>> ls = dao2.getTelAndName(userEmail);
					String tel = "NA";
					String submitter = "NA";
					if(ls.size()>1){
						tel = ls.get(1).get("LinkTel")=="--"?"NA":ls.get(1).get("LinkTel").toString();
						submitter = ls.get(1).get("StaffName")=="--"?"NA":ls.get(1).get("StaffName").toString();
					}
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String submissionTime = df.format(new Date());
					List<Map<String, Object>> data = dao.getDataByID(id);

					StringBuilder sBuilder = new StringBuilder();
					sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>您好！</span><br><br>");
					sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>"
					+submitter +"于"+submissionTime+"更新软件实施管理项目，</span><br>");
					//sBuilder.append(content);
					sBuilder.append("<table border='1' style='font-family:微软雅黑;border-color:#999;border-collapse:collapse;font-size: 14px;color:black;box-sizing:border-box;text-align:center;'><tr style='background:#ccc;height:30px;font-weight:bold;'><td>序号</td><td>客户单位</td><td>客户名称</td>"
							+ "<td>区域</td><td>机台</td><td>软件版本</td>"
							+ "<td>类型</td><td>装机时间</td><td>续期时间</td><td>进度</td><td>工程师</td>"
							+ "</tr><tr style='background:rgba(228,232,235,0.8);height:30px;'><td>1</td>"
							+"<td>"+data.get(1).get("CustomerName").toString()+"</td><td>"+data.get(1).get("Contact").toString()
							+"</td><td>"+data.get(1).get("AreaName").toString()+"</td><td>"+data.get(1).get("Machine").toString()
							+"</td><td>"+data.get(1).get("SoftwareVersion").toString()+"</td><td>"+data.get(1).get("Type").toString()
							+"</td><td>"+data.get(1).get("InstallTime").toString()+"</td><td>"+data.get(1).get("ContinueTime").toString()
							+"</td><td>"+content[content.length-1]+"</td><td>"+data.get(1).get("Engineer").toString()
							+"</tr></table><br><br>");
					sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>请知悉！</span><br>");
				
					String mailContent = new MethodUtil().getEmailSign(sBuilder.toString(), "NA");
					String subject="Eoulu:软件部实施管理更新";
				
					boolean success = sendRemindEmailConfig(subject, mailContent, null);
					if(success){
						result = "提交成功，邮件发送成功";
						 
					}else{
						return "提交成功，邮件发送失败！";
					}
				}
			}
			break;
		case "Add":
			result = dao.insert(impl)?"提交成功！":"提交失败！";
			break;
		}
		return result;
	}
	
	public boolean sendRemindEmailConfig(String subject,String content,String[] fileList){
		String[] to = null;
		String[] copyto = null;
		Properties pro = new Properties();
		String user;
		String uname;
		String pwd;
	
		try {
			pro.load(SendMailUtil.class.getResourceAsStream("email.properties"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		user = pro.getProperty("SEND_USER");
		uname = pro.getProperty("SEND_UNAME");
		pwd = pro.getProperty("SEND_PWD");
		
		MailConfigDao dao = new MailConfigDao();
		List<Map<String, Object>> config = dao.getConfig("implementation");
		String toList = "";
		String copyList = "";
		if(config.size()>1){
			toList = config.get(1).get("ToList").toString();
			copyList = config.get(1).get("CopyList").toString();
		}
		to = toList.split(";");
		copyto = copyList.split(";");
		return new JavaMailToolsUtil(user, uname, pwd).doSendHtmlEmail(subject, content, fileList, to, copyto);
	}

	@Override
	public Map<String, Object> getOperatePreview(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		SoftwareImplementationDao dao = new SoftwareImplementationDao();
		int id = Integer.parseInt(request.getParameter("ID")==null?"0":request.getParameter("ID"));
		map.put("datas", dao.getOperatePreview(id));
		map.put("services", dao.getPreview(id));
		return map;
	}

	@Override
	public List<Map<String, Object>> getPreview(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("ID")==null?"0":request.getParameter("ID"));
		return new SoftwareImplementationDao().getDetailRecord(id);
	}

	public void delete(String[] ids,int id){
		SoftwareImplementationDao dao = new SoftwareImplementationDao();
		List<Map<String,Object>> ls = dao.getServiceID(id);
		for(int i=1;i<ls.size();i++){
			boolean temp = true;
			for(int j=0;j<ids.length;j++){
				if(ls.get(i).get("ID").equals(ids[j])){
					temp = false;
					break;
				}
			}
			if(temp){
				System.out.println(dao.deleteService(Integer.parseInt(ls.get(i).get("ID").toString()))?"删除成功":"删除失败");
			}
		}
	}
	
}

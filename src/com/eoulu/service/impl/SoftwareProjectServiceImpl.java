package com.eoulu.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.dao.SoftwareProjectDao;
import com.eoulu.dao.StaffInfoDao;
import com.eoulu.entity.ProjectDocument;
import com.eoulu.entity.SoftwareProject;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.SoftwareProjectService;
import com.eoulu.util.DocumentUploadUtilY;
import com.eoulu.util.ExportExcel;
import com.eoulu.util.JavaMailToolsUtil;
import com.eoulu.util.MethodUtil;

public class SoftwareProjectServiceImpl implements SoftwareProjectService {

	@Override
	public List<Map<String, Object>> getAllData(Page page,String column,String content,String order) {
		// TODO Auto-generated method stub
		
		return new SoftwareProjectDao().getAllData(page,column,content,order);
	}

	@Override
	public int getAllCounts(String column,String content) {
		return new SoftwareProjectDao().getAllCounts(column,content);
	}

	@Override
	public String insert(HttpServletRequest request) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String product = request.getParameter("Product") == null ? "" : request.getParameter("Product");
		String instructions = request.getParameter("Instructions") == null ? "" : request.getParameter("Instructions");
		String uiSpecification = request.getParameter("UISpecification") == null ? "" : request.getParameter("UISpecification");
		String instructionsPath = request.getParameter("InstructionsPath") == null ? "" : request.getParameter("InstructionsPath");
		String uiSpecificationPath = request.getParameter("UISpecificationPath") == null ? "" : request.getParameter("UISpecificationPath");
		String priority = request.getParameter("Priority") == null ? "" : request.getParameter("Priority");
		String front = request.getParameter("Front") == null ? "" : request.getParameter("Front");
		String back = request.getParameter("Back") == null ? "" : request.getParameter("Back");
		String uiDesigner = request.getParameter("UIDesigner") == null ? "" : request.getParameter("UIDesigner");
		String leader = request.getParameter("Leader") == null ? "" : request.getParameter("Leader");
		String submissionTime = sdf.format(new Date()); 
		String planningTime = request.getParameter("PlanningTime") == null ? ""
				: request.getParameter("PlanningTime");
		
		
		String state = request.getParameter("State") == null ? ""
				: request.getParameter("State");
		String remark = request.getParameter("Remark") == null ? ""
				: request.getParameter("Remark").trim();
		String password = request.getParameter("Password") == null ? ""
				: request.getParameter("Password").trim();
		String email = request.getSession().getAttribute("email").toString().trim();
		//String content = request.getParameter("Content") == null?"":request.getParameter("Content");
		String leadTime = "";
		if(state.equals("完成")){
			leadTime =  sdf.format(new Date());
		}
		
		int delayTime = 0;
		try{
			if(leadTime.equals("")){
				delayTime = differentDays(sdf.parse(planningTime),new Date());
			}else{
				delayTime = differentDays(sdf.parse(planningTime),sdf.parse(leadTime));
			}
		}catch(ParseException e){
			e.printStackTrace();
		}

		StaffInfoDao dao2 = new StaffInfoDao();
		List<Map<String,Object>> ls = dao2.getTelAndName(email);
		String tel = "NA";
		String submitter = "NA";
		if(ls.size()>1){
			tel = ls.get(1).get("LinkTel")=="--"?"NA":ls.get(1).get("LinkTel").toString();
			submitter = ls.get(1).get("StaffName")=="--"?"NA":ls.get(1).get("StaffName").toString();
		}
		

		SoftwareProject project = new SoftwareProject();
		project.setProduct(product);
		project.setInstructions(instructions);
		project.setUiSpecification(uiSpecification);
		project.setPriority(priority);
		project.setSubmitter(submitter);
		project.setFront(front);
		project.setBack(back);
		project.setUiDesigner(uiDesigner);
		project.setLeader(leader);
		project.setSubmissionTime(submissionTime);
		project.setPlanningTime(planningTime);
		project.setLeadTime(leadTime);
		project.setDelayTime(delayTime);
		project.setState(state);
		project.setRemark(remark);
		
		SoftwareProjectDao dao = new SoftwareProjectDao();
		int id = 0;
		try {
			id = dao.insert(project);
		} catch (Exception e) {
			e.printStackTrace();
		}  

		if (id>0) {
			if(instructionsPath!=null&&(!instructionsPath.equals(""))){
				ProjectDocument document = new ProjectDocument();
				document.setProjectId(id);
				document.setClassify("Instructions");
				document.setPath(instructionsPath);
				dao.updatePath(document);
			}
			if(uiSpecificationPath!=null&&(!uiSpecificationPath.equals(""))){
				ProjectDocument document = new ProjectDocument();
				document.setProjectId(id);
				document.setClassify("UISpecification");
				document.setPath(uiSpecificationPath);
				dao.updatePath(document);
			}
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "软件部-开发项目管理";
			String description = "新增-" + instructions;
			log.insert(request, JspInfo, description);
			
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>您好！</span><br><br>");
			sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>"
			+submitter +"在"+submissionTime+"新建软件项目，</span><br>");
			//sBuilder.append(content);
			sBuilder.append("<table border='1' style='font-family:微软雅黑;border-color:#333;border-collapse:collapse;font-size: 14px;color:black;'><tr><td>序号</td><td>项目概述</td><td>开发说明书</td>"
					+ "<td>UI规范</td><td>优先级</td>"
					+ "<td>提交人</td><td>前端</td><td>后台</td><td>UI设计</td><td>主导人</td><td>提交时间</td>"
					+ "<td>规划时间</td><td>交付时间</td><td>延期时间</td><td>状态</td><td>备注</td></tr><tr><td>1</td><td>"+product
					+"</td><td>"+instructions+"</td><td>"+uiSpecification+"</td><td>"+priority+"</td><td>"+submitter+"</td><td>"+front+"</td><td>"+back
					+"</td><td>"+uiDesigner+"</td><td>"+leader+"</td><td>"+submissionTime+"</td><td>"+planningTime
					+"</td><td>"+leadTime+"</td><td>"+delayTime+"</td><td>"+state+"</td><td>"+remark+"</td></tr></table><br><br>");
			sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>请知悉，谢谢！</span><br>");
		
			String mailContent = new MethodUtil().getStaffEmailSign(sBuilder.toString(), submitter,tel,email);
			JavaMailToolsUtil util = new JavaMailToolsUtil(email, email, password);
			String subject="Eoulu:软件部提醒——新建软件开发项目";
			String[] to = "songdaiao@eoulu.com".split(";");
			//String[] to = "sunmengying@eoulu.com".split(";");
			
			List<String> emailList = new ArrayList<>();
			//emailList.add(email);
			if(!front.equals("")){
				emailList.add(dao2.getMail(front).get(1).get("StaffMail").toString());
			}
			if(!back.equals("")){
				emailList.add(dao2.getMail(back).get(1).get("StaffMail").toString());
			}
			if(!uiDesigner.equals("")){
				emailList.add(dao2.getMail(uiDesigner).get(1).get("StaffMail").toString());
			}
			if(!leader.equals("")){
				emailList.add(dao2.getMail(leader).get(1).get("StaffMail").toString());
			}
			
			String[] copyto = emailList.toArray(new String[0]);
			
			boolean success = util.doSendHtmlEmail(subject, mailContent,null, to, copyto);
			if(success){
				return "项目新建成功，邮件发送成功！";
				 
			}else{
				return "项目新建成功，邮件发送失败！";
			}
		}else{
			return "项目新建失败！";
		}

	}

	@Override
	public String update(HttpServletRequest request) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int id = request.getParameter("ID") == null ? 0 : Integer.parseInt(request.getParameter("ID")); 
		String product = request.getParameter("Product") == null ? "" : request.getParameter("Product");
		String instructions = request.getParameter("Instructions") == null ? "" : request.getParameter("Instructions");
		String uiSpecification = request.getParameter("UISpecification") == null ? "" : request.getParameter("UISpecification");
		String instructionsPath = request.getParameter("InstructionsPath") == null ? "" : request.getParameter("InstructionsPath");
		String uiSpecificationPath = request.getParameter("UISpecificationPath") == null ? "" : request.getParameter("UISpecificationPath");
		String priority = request.getParameter("Priority") == null ? "" : request.getParameter("Priority");
		String front = request.getParameter("Front") == null ? "" : request.getParameter("Front");
		String back = request.getParameter("Back") == null ? "" : request.getParameter("Back");
		String uiDesigner = request.getParameter("UIDesigner") == null ? "" : request.getParameter("UIDesigner");
		String leader = request.getParameter("Leader") == null ? "" : request.getParameter("Leader");
		String submissionTime = sdf.format(new Date()); 
		String planningTime = request.getParameter("PlanningTime") == null ? "0000-00-00"
				: request.getParameter("PlanningTime");
		String state = request.getParameter("State") == null ? ""
				: request.getParameter("State");
		String remark = request.getParameter("Remark") == null ? ""
				: request.getParameter("Remark").trim();
		String password = request.getParameter("Password") == null ? ""
				: request.getParameter("Password").trim();
		String email = request.getSession().getAttribute("email").toString().trim();
		String leadTime = "";
		if(state.equals("完成")){
			leadTime =  sdf.format(new Date());
		}
		int delayTime = 0;
		try{
			if(leadTime.equals("")){
				delayTime = differentDays(sdf.parse(planningTime),new Date());
			}else{
				delayTime = differentDays(sdf.parse(planningTime),sdf.parse(leadTime));
			}
		}catch(ParseException e){
			e.printStackTrace();
		}

	
		StaffInfoDao dao2 = new StaffInfoDao();
		List<Map<String,Object>> ls = dao2.getTelAndName(email);
		String tel = "NA";
		String submitter = "NA";
		if(ls.size()>1){
			tel = ls.get(1).get("LinkTel")=="--"?"NA":ls.get(1).get("LinkTel").toString();
			submitter = ls.get(1).get("StaffName")=="--"?"NA":ls.get(1).get("StaffName").toString();
		}
		
		Map<String, String> properties = new LinkedHashMap<>();
		properties.put("Product", product);
		properties.put("Instructions", instructions);
		properties.put("UISpecification", uiSpecification);
		properties.put("Priority", priority);
		properties.put("Submitter", submitter);
		properties.put("Front", front);
		properties.put("Back", back);
		properties.put("UIDesigner", uiDesigner);
		properties.put("Leader", leader);
		properties.put("SubmissionTime", submissionTime);
		properties.put("PlanningTime", planningTime);
		properties.put("LeadTime", leadTime);
		properties.put("DelayTime", String.valueOf(delayTime));
		properties.put("State", state);
		properties.put("Remark", remark);

		
		
		List<String> change = new ArrayList<>();
		SoftwareProjectDao dao = new SoftwareProjectDao();
		List<Map<String, Object>> oldInfo = dao.getDataById(id);
	
		
	

		SoftwareProject project = new SoftwareProject();
		project.setID(id);
		project.setProduct(product);
		project.setInstructions(instructions);
		project.setUiSpecification(uiSpecification);
		project.setPriority(priority);
		project.setSubmitter(submitter);
		project.setFront(front);
		project.setBack(back);
		project.setUiDesigner(uiDesigner);
		project.setLeader(leader);
		project.setSubmissionTime(submissionTime);
		project.setPlanningTime(planningTime);
		project.setLeadTime(leadTime);
		project.setDelayTime(delayTime);
		project.setState(state);
		project.setRemark(remark);
		
	
		boolean flag = dao.update(project);
		if (flag) {
			if(instructionsPath!=null&&(!instructionsPath.equals(""))){
				ProjectDocument document = new ProjectDocument();
				document.setProjectId(id);
				document.setClassify("Instructions");
				document.setPath(instructionsPath);
				dao.updatePath(document);
			}
			if(uiSpecificationPath!=null&&(!uiSpecificationPath.equals(""))){
				ProjectDocument document = new ProjectDocument();
				document.setProjectId(id);
				document.setClassify("UISpecification");
				document.setPath(uiSpecificationPath);
				dao.updatePath(document);
			}
			
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "软件部-开发项目管理";
			String description = "修改-" + instructions;
			log.insert(request, JspInfo, description);
			
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>您好！</span><br><br>");
			sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>"
			+submitter +"在"+submissionTime+"修改了软件项目，</span><br>");
			sBuilder.append("<table border='1' style='font-family:微软雅黑;border-color:#333;border-collapse:collapse;"
					+ "font-size: 14px;color:black;'><tr><td>序号</td><td>项目概述</td><td>开发说明书</td><td>UI规范</td><td>优先级</td>"
					+ "<td>提交人</td><td>前端</td><td>后台</td><td>UI设计</td><td>主导人</td><td>提交时间</td>"
					+ "<td>规划时间</td><td>交付时间</td><td>延期时间</td><td>状态</td><td>备注</td></tr><tr><td>"+id+"</td>");
			
			Set<Map.Entry<String, String>> set = properties.entrySet();
			Iterator<Map.Entry<String, String>> iterator = set.iterator();
			while(iterator.hasNext()){
				Map.Entry<String, String> entry = iterator.next();
				if(entry.getKey().equals("SubmissionTime")){
					sBuilder.append("<td>"+oldInfo.get(1).get("SubmissionTime")+"</td>");
				}else{
					if(!entry.getValue().equals(oldInfo.get(1).get(entry.getKey()).toString())){
						sBuilder.append("<td style='color:red;'>"+entry.getValue()+"</td>");
					}else{
						sBuilder.append("<td>"+entry.getValue()+"</td>");
					}
				}
			}
			sBuilder.append("</tr><table><br><br>");
		
			
			/**
			<td>"+product
					+"</td><td>"+instructions+"</td><td>"+priority+"</td><td>"+submitter+"</td><td>"+front+"</td><td>"+back
					+"</td><td>"+uiDesigner+"</td><td>"+leader+"</td><td>"+submissionTime+"</td><td>"+planningTime
					+"</td><td>"+submissionTime+"</td><td>"+delayTime+"</td><td>"+state+"</td></tr></table><br><br>");*/
			sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>请知悉，谢谢！</span><br>");
		
			String mailContent = new MethodUtil().getStaffEmailSign(sBuilder.toString(), submitter,tel,email);
			JavaMailToolsUtil util = new JavaMailToolsUtil(email, email, password);
			String subject="Eoulu:软件部提醒——修改软件开发项目";
			String[] to = "songdaiao@eoulu.com".split(";");
			//String[] to = "sunmengying@eoulu.com".split(";");
			List<String> emailList = new ArrayList<>();
			//emailList.add(email);
			if(!front.equals("")){
				emailList.add(dao2.getMail(front).get(1).get("StaffMail").toString());
			}
			if(!back.equals("")){
				emailList.add(dao2.getMail(back).get(1).get("StaffMail").toString());
			}
			if(!uiDesigner.equals("")){
				emailList.add(dao2.getMail(uiDesigner).get(1).get("StaffMail").toString());
			}
			if(!leader.equals("")){
				emailList.add(dao2.getMail(leader).get(1).get("StaffMail").toString());
			}
			
			String[] copyto = emailList.toArray(new String[0]);
			boolean success = util.doSendHtmlEmail(subject, mailContent,null, to, copyto);
			if(success){
				return "项目修改成功，邮件发送成功！";
				 
			}else{
				return "项目修改成功，邮件发送失败！";
			}
		}else{
			return "项目修改失败！";
		}

	}
	
	

	@Override
	public List<Map<String, Object>> getSoftwareStaff() {
		SoftwareProjectDao dao = new SoftwareProjectDao();
		return dao.getSoftwareStaff();
	}
	
	public static int differentDays(Date date1,Date date2){ 
		int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
	    return days;
	}

	@Override
	public void exportExcel(HttpServletResponse resp) {
		ExportExcel<SoftwareProject> ee = new ExportExcel<SoftwareProject>();
		String[] headers = {"序号","产品","开发说明书","UI规范","优先级","提交人","前端","后台","UI设计","主导人","提交时间",
				"规划时间","交付时间","延期时间","状态","备注"};
		Map<String, String> map = new HashMap<>();
		map.put("序号","ID");
		map.put("产品","product");
		map.put("开发说明书", "instructions");
		map.put("UI规范", "uiSpecification");
		map.put("优先级", "priority");
		map.put("提交人", "submitter");
		map.put("前端", "front");
		map.put("后台", "back");
		map.put("UI设计", "uiDesigner");
		map.put("主导人", "leader");
		map.put("提交时间", "submissionTime");
		map.put("规划时间", "planningTime");
		map.put("交付时间", "leadTime");
		map.put("延期时间", "delayTime");
		map.put("状态", "state");
		map.put("备注", "remark");	
		SoftwareProjectDao dao = new SoftwareProjectDao();
		List<SoftwareProject> data = dao.getDataForExcel();
		String fileName = "软件项目开发管理统计";
	    ee.exportExcel(headers, data, fileName, resp,map);
	}

	@Override
	public Map<String, String> upload(HttpServletRequest request) {
		DocumentUploadUtilY upload = new DocumentUploadUtilY();
		String savePath = "E:\\LogisticsFile\\File\\Project\\"; 
		Map<String, String> map = new HashMap<>();
		map = upload.upload(request, savePath);
		/*
		int project_id = map.get("ID")==null?dao.getMaxID():Integer.parseInt(map.get("ID"));//要修改
		String classify = map.get("Classify")==null?"":map.get("Classify");
		String path = map.get("Path")==null?"":map.get("Path");
		String type = map.get("type")==null?"":map.get("type");
		System.out.println(type);
		ProjectDocument document = new ProjectDocument();
		document.setProjectId(project_id);
		document.setClassify(classify);
		document.setPath(path);
		switch(type){
		case "add":dao.addPath(document);break;
		case "update":dao.updatePath(document);break;
		}
		*/
		return map;
	
	}

	
	public String getPath(HttpServletRequest request) {
		String classify = request.getParameter("Classify") == null?"":request.getParameter("Classify");
		int id = request.getParameter("ID") == null?0:Integer.parseInt(request.getParameter("ID"));
		SoftwareProjectDao dao = new SoftwareProjectDao();
		String path = dao.getPath(id, classify).get(1).get("Path").toString();
		return path;
	}


	    
	

}

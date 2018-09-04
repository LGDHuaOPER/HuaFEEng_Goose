package com.eoulu.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;
import com.eoulu.dao.StaffInfoDao;
import com.eoulu.dao.WorkReportDao;
import com.eoulu.entity.WorkReport;
import com.eoulu.service.WorkReportService;
import com.eoulu.syn.ExportWorkLog;
import com.eoulu.util.JavaMailToolsUtil;
import com.eoulu.util.MethodUtil;
import com.eoulu.util.SendMailUtil;

public class WorkReportServiceImpl implements WorkReportService{

	@Override
	public boolean insert(WorkReport report) {
		WorkReportDao dao = new WorkReportDao();
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		report.setOperationTime(sdFormat.format(new Date()));
		return dao.insert(report);
	}

	@Override
	public boolean update(WorkReport report) {
		WorkReportDao dao = new WorkReportDao();
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		report.setOperationTime(sdFormat.format(new Date()));
		return dao.update(report);
	}

	@Override
	public List<Map<String, Object>> getDataByPage(Page page) {
	
		return new WorkReportDao().getDataByPage(page);
	}

	@Override
	public int getCounts() {
		return new WorkReportDao().getCounts();
	}

	@Override
	public boolean urge(String name) {
	
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
		String email = "";
		StaffInfoDao dao = new StaffInfoDao();
		List<Map<String, Object>> list = dao.getMail(name);
		if(list.size()>1){
			email = list.get(1).get("StaffMail").toString();
		}
		
		String[] to = new String[]{email};
		String subject = "Eoulu:更新工作计划提醒";
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>"+name.substring(name.length()-2)+"，你好！</span><br><br>");
		sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>请尽快更新工作计划！</span><br><br>");
		sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>祝好！</span><br>");
		MethodUtil util = new MethodUtil();
		String content = util.getEmailSign(sBuilder.toString(),"NA");
		return new JavaMailToolsUtil(user, uname, pwd).doSendHtmlEmail(subject, content, null, to, null);
	}
	
	


	@Override
	public String sendComments(int ID,String name, String text,String umail) {
		
		String email = "";
		StaffInfoDao dao = new StaffInfoDao();
		List<Map<String, Object>> list = dao.getMail(name);
		if(list.size()>1){
			email = list.get(1).get("StaffMail").toString();
		}
		String[] to = new String[]{email};
		String[] copyto = null;
		Properties pro = new Properties();
		Properties pro2 = new Properties();
		List<String> ls = new ArrayList<>();
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
		
		try {
			pro2.load(SendMailUtil.class.getResourceAsStream("sendComments.properties"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		int copytoCount = Integer.parseInt(pro2.getProperty("copyto"));
		copyto = new String[copytoCount+1];
		for(int i=0 ; i<copytoCount ; i++){
			int temp = i+1;
			String key = "CopyTo"+temp;
			ls.add(pro2.getProperty(key));
			
		}
		for(int i=0 ; i<ls.size();i++){
			copyto[i] = ls.get(i);
		}
		copyto[copytoCount] = umail;
		String subject = "Eoulu：工作汇报意见";
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>"+name.substring(name.length()-2)+"，你好！</span><br><br>");
		sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>"+text+"</span><br><br>");
		sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>祝好！</span><br>");
		MethodUtil util = new MethodUtil();
		String content = util.getEmailSign(sBuilder.toString(),"NA");
		boolean flag = new JavaMailToolsUtil(user, uname, pwd).doSendHtmlEmail(subject, content, null, to, copyto);
		if(flag){
			WorkReportDao dao2 = new WorkReportDao();
			dao2.updateComments(ID);
			return "操作成功";
		}else{
			return "操作失败";
		}
		
	
	}

	@Override
	public boolean sendLog(HttpServletRequest request) {
		ExportWorkLog util = new ExportWorkLog();
		String file = util.exportLog(request);
		String user = request.getSession().getAttribute("email").toString();
		int ID = request.getParameter("ID")==null?0:Integer.parseInt(request.getParameter("ID"));
		String password = request.getParameter("Password")==null?"":request.getParameter("Password");
		String toStr = request.getParameter("ToList");
		String copytoStr = request.getParameter("CopyToList");
		String name = request.getParameter("Name")==null?"":request.getParameter("Name").trim();
		StaffInfoDao dao = new StaffInfoDao();
		List<Map<String, Object>> list = dao.getTelAndName(user);
		String tel = "NA";
		if(list.size()>1){
			tel = list.get(1).get("LinkTel").toString();
		}
		String[] toList = toStr.split(";");
		String[] copytoList = copytoStr.split(";");
		String[] fileList = new String[]{file};
		String subject= "Eoulu："+ file.substring(file.lastIndexOf("\\")+1);
		MethodUtil util2 = new MethodUtil();
		String content = util2.getStaffEmailSign("\n",name,tel,user);
		boolean flag = new JavaMailToolsUtil(user, user, password).doSendHtmlEmail(subject, content, fileList, toList, copytoList);
		if(flag){
			WorkReportDao dao2 = new WorkReportDao();
			dao2.updateLogState(toStr, copytoStr, ID);
		}
		return flag;
	}
	

}

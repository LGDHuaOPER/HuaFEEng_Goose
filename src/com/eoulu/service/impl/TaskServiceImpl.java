package com.eoulu.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.eoulu.commonality.Page;
import com.eoulu.dao.StaffInfoDao;
import com.eoulu.dao.TaskDao;
import com.eoulu.entity.Task;
import com.eoulu.service.TaskService;
import com.eoulu.util.JavaMailToolsUtil;
import com.eoulu.util.MethodUtil;
import com.eoulu.util.SendMailUtil;

public class TaskServiceImpl implements TaskService{

	@Override
	public List<Map<String, Object>> getDataByPage(Page page,String type,String column1,String content1,String column2,String content2) {
		TaskDao dao = new TaskDao();
		return dao.getDataByPage(page, type, column1, content1, column2, content2);
	}

	@Override
	public boolean insert(Task task) {
		TaskDao dao = new TaskDao();
		return dao.insert(task);
	}

	@Override
	public int getCounts(String type, String column1, String content1, String column2, String content2) {
		
		return new TaskDao().getCounts(type, column1, content1, column2, content2);
	}

	@Override
	public boolean update(Task task) {
		// TODO Auto-generated method stub
		return new TaskDao().update(task);
	}
	public String sendMail(Task task){
		String subject = "Eoulu:"+task.getTitle();
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>"+task.getResponsibleMan()+"，你好!</span><br><br>");
		sBuilder.append("<table border='1' style='font-family:微软雅黑;border-color:#333;border-collapse:collapse;"
				+ "font-size: 14px;color:black;'><tr><td>任务名称</td><td>发布时间</td><td>任务期限时间</td><td>具体描述</td></tr>"
				+ "<tr><td>"+task.getTitle()+"</td><td>"+task.getPublishedDate()+"</td><td>"+task.getLimitDate()+"</td><td>"
				+task.getDescription()+"</td></tr></table><br>");

		MethodUtil util = new MethodUtil();
		String content = util.getEmailSign(sBuilder.toString(), "NA");
		StaffInfoDao dao = new StaffInfoDao();
		String mail = dao.getMail(task.getResponsibleMan()).get(1).get("StaffMail").toString();
		String[] to = mail.split(";");
	
	
		String result = MailConfig(subject, content, to);
		if(result.equals("发送成功！")){
			boolean flag = new TaskDao().updateStatus(task.getID());
		}
		return result;
			
		
	}
	public String  MailConfig(String subject,String content,String[] to){
	
		String[] copyto = null;
		Properties pro = new Properties();
		Properties pro2 = new Properties();
		List<String> list = new ArrayList<>();
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
			pro2.load(SendMailUtil.class.getResourceAsStream("tasks.properties"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		int copytoCount = Integer.parseInt(pro2.getProperty("copyto"));
		copyto = new String[copytoCount];
		for(int i=0 ; i<copytoCount ; i++){
			int temp = i+1;
			String key = "CopyTo"+temp;
			ls.add(pro2.getProperty(key));
			
		}
		for(int i=0 ; i<ls.size();i++){
			copyto[i] = ls.get(i);
		}
		return new JavaMailToolsUtil(user, uname, pwd).sendHtmlEmail(subject, content, null, to, copyto);
	}
	
	public List<Map<String,Object>> getStatistics(String year,String month,String type){
	
		String startTime = year + "-" + month + "-01";
		String endTime = year + "-" + month + "-30";
		if (month.equals("All")) {
			startTime = year + "-01-01";
			endTime = year + "-12-31";
		}
		if (month.equals("01") || month.equals("03") || month.equals("05") || month.equals("07") || month.equals("08")
				|| month.equals("10") || month.equals("12")) {
			endTime = year + "-" + month + "-31";
		}
		if (month.equals("02")) {
			if ((Integer.parseInt(year) % 100 == 0) && (Integer.parseInt(year) % 400 == 0)) {
				endTime = year + "-" + month + "-29";
			} else if ((Integer.parseInt(year) % 100 != 0) && (Integer.parseInt(year) % 4 == 0)) {
				endTime = year + "-" + month + "-29";
			} else {
				endTime = year + "-" + month + "-28";
			}
		}
		if(type.equals("Amount")){
			return new TaskDao().getStatistics(startTime, endTime);
		}else{
			return new TaskDao().getStatisticsByReview(startTime, endTime);
		}
		
	}
	

}

package com.eoulu.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.eoulu.commonality.Page;
import com.eoulu.dao.NonStandardDao;
import com.eoulu.dao.StaffInfoDao;
import com.eoulu.entity.NonStandard;
import com.eoulu.service.NonStandardService;
import com.eoulu.util.JavaMailToolsUtil;
import com.eoulu.util.MethodUtil;
import com.eoulu.util.SendMailUtil;

public class NonStandardServiceImpl implements NonStandardService{

	@Override
	public int insert(NonStandard nonStandard) {
		return new NonStandardDao().insert(nonStandard);
		
		
	}

	@Override
	public boolean update(NonStandard nonStandard) {
	
		return new NonStandardDao().update(nonStandard);
	}

	@Override
	public List<Map<String, Object>> getDataByPage(Page page, String type, String column1, String content1,
			String column2, String content2) {
		return new NonStandardDao().getDataByPage(page, type, column1, content1, column2, content2);
	}

	@Override
	public int getCounts(String type, String column1, String content1, String column2, String content2) {
		return new NonStandardDao().getCounts(type, column1, content1, column2, content2);
	}
	
	@Override
	public String sendMail(NonStandard nonStandard){
		String subject = "Eoulu:"+nonStandard.getProjectName();
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>"+nonStandard.getResponsibleMan()+"，你好!</span><br><br>");
		sBuilder.append("<table border='1' style='font-family:微软雅黑;border-color:#333;border-collapse:collapse;"
				+ "font-size: 14px;color:black;'><tr><td>项目名称</td><td>发布时间</td><td>阶段预计完成时间</td><td>具体描述</td></tr>"
				+ "<tr><td>"+nonStandard.getProjectName()+"</td><td>"+nonStandard.getPublishDate()+"</td><td>"+nonStandard.getStageExpectedDate()+"</td><td>"
				+nonStandard.getProjectStage()+":"+nonStandard.getProjectProgress()+"</td></tr></table><br>");

		MethodUtil util = new MethodUtil();
		String content = util.getEmailSign(sBuilder.toString(), "NA");
		StaffInfoDao dao = new StaffInfoDao();
		if(dao.getMail(nonStandard.getResponsibleMan()).size()==1){
			return "责任人邮箱获取失败，请检查输入是否正确";
			
		}else{
			String mail = dao.getMail(nonStandard.getResponsibleMan()).get(1).get("StaffMail").toString();
			
			String[] to = mail.split(";");
		
		
			String result = MailConfig(subject, content, to);
			if(result.equals("发送成功！")){
				boolean flag = new NonStandardDao().updateStatus(nonStandard.getID());
			}
			return result;
		}
			
		
	}
	public String  MailConfig(String subject,String content,String[] to){
	
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
			pro2.load(SendMailUtil.class.getResourceAsStream("NonStandardProject.properties"));
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
	public static void main(String[] args){
		NonStandard nonStandard = new NonStandard();
		nonStandard.setStageExpectedDate("2018-05-31");
		nonStandard.setProjectName("test");
		nonStandard.setResponsibleMan("孙梦颖");
		nonStandard.setProjectStage("test");
		nonStandard.setStageActualDate("");
		nonStandard.setPublishDate("2018-06-01");
		NonStandardService service = new NonStandardServiceImpl();
		service.insert(nonStandard);
		
	}

	@Override
	public List<NonStandard> getRemindProject() {
		return new NonStandardDao().getRemindProject();
	}

	@Override
	public boolean deleteProject(int ID) {
	
		return new NonStandardDao().deleteProject(ID);
	}

	@Override
	public List<Map<String, Object>> getProjectName(String key) {
		return new NonStandardDao().getProjectName(key);
	}


}

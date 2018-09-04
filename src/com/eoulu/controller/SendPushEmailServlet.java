package com.eoulu.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.util.StringUtil;

import com.alibaba.fastjson.asm.Type;
import com.eoulu.dao.OriginFactoryDao;
import com.eoulu.dao.StaffInfoDao;
import com.eoulu.entity.FormFactorMail;
import com.eoulu.service.OriginFactoryService;
import com.eoulu.service.impl.OriginFactoryServiceImpl;
import com.eoulu.util.JavaMailToolsUtil;
import com.eoulu.util.MethodUtil;
import com.eoulu.util.SendMailUtil;
import com.google.gson.Gson;

/**
 * Servlet implementation class SendPushShipDateEmailServlet
 */
@WebServlet("/SendPushEmail")
public class SendPushEmailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public SendPushEmailServlet() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String type = request .getParameter("Type") == null?"":request .getParameter("Type");
		String address = request .getParameter("Address") == null?"":request .getParameter("Address");
		String[] receptor = request.getParameterValues("Receptor[]");
		String[] copyList = request.getParameterValues("CopyList[]");
		String subject = request.getParameter("Subject")==null?"":request.getParameter("Subject");
		String content = request.getParameter("Content")==null?"":request.getParameter("Content");
		String sendContent = request.getParameter("sendContent")==null?"":request.getParameter("sendContent");
		int factoryID = request.getParameter("FactoryID")==null?0:Integer.parseInt(request.getParameter("FactoryID"));
		System.out.println(factoryID);
		StringBuilder sbBuilder = new StringBuilder();
		if(type.equals("Inform")){
			sbBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>");
		}else{
			sbBuilder.append("<span style='font-family:Arial;font-size:14px;'>");
		}
	
		sbBuilder.append(sendContent);
		sbBuilder.append("</span><br>");
		Properties pro = new Properties();
		pro.load(SendMailUtil.class.getResourceAsStream("./UrgeNotice.properties"));
		String email = pro.getProperty("SEND_USER");
		String password = pro.getProperty("SEND_PWD");
		StaffInfoDao st = new StaffInfoDao();
		List<Map<String,Object>> ls1 = st.getTelAndName(email);
		String tel = "NA";
		String username = "NA";
		if(ls1.size()>1){
			tel = ls1.get(1).get("LinkTel")=="--"?"NA":ls1.get(1).get("LinkTel").toString();
			username = ls1.get(1).get("StaffName")=="--"?"NA":ls1.get(1).get("StaffName").toString();
		}
		String MailContent = new MethodUtil().getStaffEmailSign(sbBuilder.toString(), username, tel, email);
		JavaMailToolsUtil util = new JavaMailToolsUtil(email, email, password);
		System.out.println(email+","+password+","+subject+","+MailContent+","+receptor+","+copyList);
		boolean flag = util.doSendHtmlEmail(subject, MailContent, null, receptor, copyList);
		String responseText="";
		if(flag){
			responseText = "邮件发送成功";
			OriginFactoryDao dao = new OriginFactoryDao();
	
			if(type.equals("Inform")){	
				dao.updateInform(factoryID, address);
			}else{
				dao.updatePushDate(factoryID, type);
			}

			FormFactorMail mail = new FormFactorMail();
			mail.setType(type);
			mail.setFactoryID(factoryID);
	
			mail.setReceptor(StringUtil.join(";", receptor));
			mail.setCopyList(StringUtil.join(";", copyList));
			mail.setSubject(subject);
			mail.setMainBody(content);
			
			if(!dao.saveMail(mail)){
				responseText += "，但保存失败";
			}
		}else{
			responseText = "邮件发送失败";
		}
		response.getWriter().write(responseText);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request .getParameter("Type") == null?"":request .getParameter("Type");
		int factoryID = request.getParameter("FactoryID") == null?0:Integer.parseInt(request.getParameter("FactoryID"));
		OriginFactoryService service = new OriginFactoryServiceImpl();
		System.out.println(service.getMail(factoryID, type));
		response.getWriter().write(new Gson().toJson(service.getMail(factoryID, type)));
	}

}

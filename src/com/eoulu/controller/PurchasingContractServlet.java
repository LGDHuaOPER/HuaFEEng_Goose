package com.eoulu.controller;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.LogInfoService;
import com.eoulu.service.OrderService;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.OrderServiceImpl;
import com.eoulu.util.JavaMailToolsUtil;
import com.eoulu.util.MethodUtil;
import com.eoulu.util.SendMailUtil;

@WebServlet("/PurchasingContract")
public class PurchasingContractServlet extends HttpServlet{

	private static final String rootURL = "E:\\LogisticsFile\\File\\";
	private static final long serialVersionUID = 1L;
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String contract = req.getParameter("Contract") == null?"":req.getParameter("Contract");
		String to = req.getParameter("To") == null?"":req.getParameter("To");
		String copyTo1 = req.getParameter("CopyTo1") == null?"":req.getParameter("CopyTo1");
		String copyTo2 = req.getParameter("CopyTo2") == null?"":req.getParameter("CopyTo2");
		String copyTo3 = req.getParameter("CopyTo3") == null?"":req.getParameter("CopyTo3");
		String[] toList = to.split(";");
		String[] copyToList = new String[]{copyTo1,copyTo2,copyTo3};
		String subject = req.getParameter("Subject") == null?"":req.getParameter("Subject");
		String input = req.getParameter("Input") == null?"":req.getParameter("Input");
		String contractPath = "";
		String[] fileList = null;
		if(!contract.equals("")){
			contractPath = rootURL + contract;
			fileList = new String[1];
			fileList[0] = contractPath;
		}
		MethodUtil mu = new MethodUtil();
		String content = mu.getEmailSign(input,"NA");
		Properties pro = new Properties();
		pro.load(SendMailUtil.class.getResourceAsStream("email.properties"));
		JavaMailToolsUtil util = new JavaMailToolsUtil(pro.getProperty("SEND_USER"),
				pro.getProperty("SEND_UNAME"),pro.getProperty("SEND_PWD"));
		boolean success = util.doSendHtmlEmail(subject, content, fileList, toList, copyToList);
		if(success){
			OrderService service = new OrderServiceImpl();
			int ID = req.getParameter("ID") == null?0:Integer.parseInt(req.getParameter("ID"));
			service.setPurchaseMail(ID);
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "库存采购";
			String description = "邮件-采购合同"+contract;
			log.insert(req, JspInfo, description);
		}
		resp.getWriter().write(success == true?"发送成功！":"发送失败！");	
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	

}

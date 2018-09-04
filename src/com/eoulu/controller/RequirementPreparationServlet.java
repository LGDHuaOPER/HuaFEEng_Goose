package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.entity.Requirement;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.RequirementService;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.RequirementServiceImpl;
import com.eoulu.util.MethodUtil;
import com.eoulu.util.SendPreparationEmailUtil;
import com.google.gson.Gson;

@WebServlet("/RequirementPreparation")
public class RequirementPreparationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public RequirementPreparationServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequirementService requirementService = new RequirementServiceImpl();
		boolean flag = false;
		Requirement requirements = new Requirement();
//		String Preparation = req.getParameter("Preparation");

//		if (Preparation.equals("yes")) {
			String emailContent = req.getParameter("Content");
			System.out.println(emailContent);
			String area = req.getParameter("Area");
			String company = req.getParameter("Company");
//			String attn = req.getParameter("Attn");
//			String tel = req.getParameter("Tel");
			String requirement = req.getParameter("Requirement");
//			String email = req.getParameter("Email");
//			String endUser = req.getParameter("EndUser");
//			String endAttn = req.getParameter("EndAttn");
//			String endTel = req.getParameter("EndTel");
//			String endEmail = req.getParameter("EndEmail");
//			String endRequirement = req.getParameter("EndRequirement");
			MethodUtil util1 = new MethodUtil();
			System.out.println("test:"+requirement+"-----"+company);
			StringBuffer sb = new StringBuffer();

			sb.append("<span style='font-family:Arial;font-size:14px;'>Hi, Wilson,</span><br>");
			sb.append("<br>");
			sb.append(
					"<span style='font-family:Arial;font-size:14px;'>Please find the new customer request as below.</span><br><br>");
			sb.append(emailContent);
	

			sb.append("<br><span style='font-family:Arial;font-size:14px;'>Thanks for your support.</span><br><br>");
			sb.append("<span style='font-family:Arial;font-size:14px;'>Thanks & Best regards.</span><br>");
			String content = util1.getStaffEmailSign(sb.toString(),"方源媛" ,"86-18061929959/15371826570", "fangyuanyuan@eoulu.com");
			String subject = "Eoulu:new customer request from " + company+","+requirement;
			String[] fileList = null;
			SendPreparationEmailUtil util = new SendPreparationEmailUtil();
			boolean success = util.doSendHtmlEmail(area, subject, content, fileList);
//			boolean success = false;
			if (success) {
				LogInfoService log = new LogInfoServiceImpl();
				String JspInfo = "需求页面";
				String description = "报备-"+company+"-"+requirement;
				log.insert(req, JspInfo, description);
				requirements.setPreparation("已报备");
				requirements.setID(Integer.parseInt(req.getParameter("ID")));
				if (requirementService.updatePreparation(requirements)) {
					flag = true;
				}
			}
//		} else {
//			requirements.setPreparation("未报备");
//			requirements.setID(Integer.parseInt(req.getParameter("ID")));
//			if (requirementService.updatePreparation(requirements)) {
//				flag = true;
//			}
//		}
		resp.getWriter().write(new Gson().toJson(flag));
	}

}

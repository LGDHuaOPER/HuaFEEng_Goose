package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.entity.Equipment;
import com.eoulu.entity.Requirement;
import com.eoulu.service.InformationBankService;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.RequirementService;
import com.eoulu.service.impl.InformationBankServiceImpl;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.RequirementServiceImpl;
import com.google.gson.Gson;

/**
 * ����ҳ��Ĳ����ӿڣ��ýӿڿ��Խ��������޸ġ�ɾ��������
 */
@WebServlet("/RequirementOperate")
public class RequirementOperateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RequirementOperateServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
System.out.println(12141354+"走进来了么");
		String classify = request.getParameter("classify");
		
		Requirement requirement = new Requirement();
		RequirementService requirementService = new RequirementServiceImpl();
		try{requirement.setID(Integer.parseInt(request.getParameter("id")));}catch(Exception e){}
		try{requirement.setSequenceNumber(request.getParameter("sequence_number"));}catch(Exception e){}
		try{requirement.setRequirementDate(request.getParameter("requirement_date"));}catch(Exception e){}
		try{requirement.setCustomerName(request.getParameter("customer_name"));}catch(Exception e){}
		try{requirement.setArea(Integer.parseInt(request.getParameter("area")));}catch(Exception e){}
		try{requirement.setBrand(request.getParameter("brand"));}catch(Exception e){}
		try{requirement.setRequirementContent(request.getParameter("requirement_content"));}catch(Exception e){}
		try{requirement.setRequirementClassify(request.getParameter("requirement_classify"));}catch(Exception e){}
		try{requirement.setDemandSources(request.getParameter("demand_sources"));}catch(Exception e){}
		try{requirement.setWhetherQuotes(request.getParameter("whether_quotes"));}catch(Exception e){}
		try{requirement.setSalesMan(Integer.parseInt(request.getParameter("sales_man")));}catch(Exception e){}
		try{requirement.setSingleProbability(request.getParameter("single_probability"));}catch(Exception e){}
		try{requirement.setExceptedPayTime(request.getParameter("excepted_pay_time"));}catch(Exception e){}
		try{requirement.setWhetherSingle(request.getParameter("whether_single"));}catch(Exception e){}
		try{requirement.setProgressStatus(request.getParameter("progress_status"));}catch(Exception e){}
		try{requirement.setFollowPlan(request.getParameter("follow_plan"));}catch(Exception e){}
		try{requirement.setQuotationNumber(request.getParameter("quotation_number"));}catch(Exception e){}
		try{requirement.setUSDQuotes(Integer.parseInt(request.getParameter("usd_quotes")));}catch(Exception e){}
		try{requirement.setRMBQuotes(Integer.parseInt(request.getParameter("rmb_quotes")));}catch(Exception e){}
		try{requirement.setProvince(request.getParameter("Province"));}catch(Exception e){}
		//try{requirement.setCustomerLevel(request.getParameter("CustomerLevel"));}catch(Exception e){}
		try{requirement.setPreparation("未报备");}catch(Exception e){}
		try{requirement.setRefNo(request.getParameter("RefNo"));}catch(Exception e){}
		requirement.setBusinessMan(request.getParameter("BusinessMan"));;
		System.out.println(request.getParameter("BusinessMan"));
		System.out.println(request.getParameter("RefNo"));
		boolean flag = false;
		switch(classify){
		case "新增":flag = requirementService.RequirementAdd(requirement,request);break;
		case "删除":flag = requirementService.RequirementDelete(requirement);break;
		case "修改":flag = requirementService.RequirementModify(requirement,request);break;
		}

		if(flag){
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "需求录入";
			String description =classify+"-合同号为"+requirementService.getCustomerName(Integer.parseInt(request.getParameter("customer_name")))+"的需求";
			log.insert(request, JspInfo, description);
		}


		response.getWriter().write(new Gson().toJson("{message:"+flag+"}"));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(12141354+"POST--走进来了么");
		doGet(request, response);
	}

}

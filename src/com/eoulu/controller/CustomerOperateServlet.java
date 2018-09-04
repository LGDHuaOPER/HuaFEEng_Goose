package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.entity.Customer;
import com.eoulu.entity.Equipment;
import com.eoulu.service.InformationBankService;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.RequirementService;
import com.eoulu.service.impl.InformationBankServiceImpl;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.RequirementServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class CustomerOperateServlet
 */
@WebServlet("/CustomerOperate")
public class CustomerOperateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CustomerOperateServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String classify = request.getParameter("classify");
		RequirementService service = new RequirementServiceImpl();

		Customer customer = new Customer();
		InformationBankService informationBankService = new InformationBankServiceImpl();

		try{customer.setID(Integer.parseInt(request.getParameter("id")));}catch(Exception e){}
		try{customer.setCustomerName(request.getParameter("customer_name"));}catch(Exception e){}
		try{customer.setContact(request.getParameter("contact"));}catch(Exception e){}
		try{customer.setContactInfo1(request.getParameter("contact_info1"));}catch(Exception e){}
		try{customer.setContactInfo2(request.getParameter("contact_info2"));}catch(Exception e){}
		try{customer.setContactAddress(request.getParameter("contact_address"));}catch(Exception e){}
		try{customer.setArea(request.getParameter("area"));}catch(Exception e){}
		try{customer.setZipCode(request.getParameter("zip_code"));}catch(Exception e){}
		try{customer.setEmail(request.getParameter("email"));}catch(Exception e){}
		try{customer.setCustomerClassify(request.getParameter("customer_classify"));}catch(Exception e){}
		try{customer.setShorthandCoding(request.getParameter("shorthand_coding"));}catch(Exception e){}
		try{customer.setFaxNumber(request.getParameter("fax_number"));}catch(Exception e){}
		try{customer.setEnglishName(request.getParameter("EnglishName"));}catch(Exception e){}
		try{customer.setWebsite(request.getParameter("Website"));;}catch(Exception e){}
		try{customer.setCustomerDepartment(request.getParameter("CustomerDepartment"));}catch(Exception e){}
		try{customer.setDepartmentEnglish(request.getParameter("DepartmentEnglish"));}catch(Exception e){}
		try{customer.setProductCategory(request.getParameter("ProductCategory"));}catch(Exception e){}
		try{customer.setCustomerLevel(request.getParameter("CustomerLevel"));}catch(Exception e){}
		String areaName = service.getArea(request.getParameter("area"));
		int areaNum = 0;
		switch(areaName){
		case"南方":areaNum = 2;break;
		case"北方":areaNum = 3;break;
		case"西南区":areaNum = 1;break;
		
		}
		customer.setCity(request.getParameter("City")==null?"":request.getParameter("City"));
		customer.setAreaName(areaNum);
		boolean flag = false;
		switch(classify){
		case "新增":flag = informationBankService.CustomerAdd(customer);break;
		case "删除":flag = informationBankService.CustomerDelete(customer.getID());break;
		case "修改":flag = informationBankService.CustomerModify(customer);break;
		}

		if(flag){
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "资料库-客户信息表";
			String description = classify+"-"+request.getParameter("customer_name")+"-"+request.getParameter("contact");
			log.insert(request, JspInfo, description);
		}
		response.getWriter().write(new Gson().toJson("{message:"+flag+"}"));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

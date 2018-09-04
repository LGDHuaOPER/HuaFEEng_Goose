package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.entity.Supplier;
import com.eoulu.entity.SupplierBank;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.SupplierService;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.SupplierServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class SupplierOperateServlet
 */
@WebServlet("/SupplierOperate")
public class SupplierOperateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SupplierOperateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String classify = request.getParameter("classify");
		SupplierService supplierService = new SupplierServiceImpl();
		boolean flag = false;
		
		Supplier supplier = new Supplier();
		try{supplier.setID(Integer.parseInt(request.getParameter("id")));}catch(Exception e){}
		try{supplier.setName(request.getParameter("name"));}catch(Exception e){}
		try{supplier.setContact(request.getParameter("Contact"));}catch(Exception e){}
		try{supplier.setContactInfo(request.getParameter("ContactInfo"));}catch(Exception e){}
		try{supplier.setAddress(request.getParameter("Address"));}catch(Exception e){}
		try{supplier.setEmail(request.getParameter("Email"));}catch(Exception e){}
		//try{supplier.setBankInfo(request.getParameter("BankInfo"));}catch(Exception e){}
		try{supplier.setProduct(request.getParameter("Product"));}catch(Exception e){}
		
		SupplierBank bank = new SupplierBank();
		try{bank.setSupplier(request.getParameter("name"));}catch(Exception e){}
		try{bank.setCompany(request.getParameter("company"));}catch(Exception e){}
		try{bank.setAccount(request.getParameter("account"));}catch(Exception e){}
		try{bank.setBank(request.getParameter("bank"));}catch(Exception e){}
		try{bank.setTaxCode(request.getParameter("taxCode"));}catch(Exception e){}
		try{bank.setSWIFTCode(request.getParameter("SWIFTCode"));}catch(Exception e){}
		
		
		switch(classify){
		case "新增":flag = supplierService.insertSupplier(supplier,bank);break;
		case "修改":flag = supplierService.modifySupplier(supplier,bank);break;
		}
		if(flag){
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "资料库-供应商";
			String description = classify+"-"+request.getParameter("name");
			log.insert(request, JspInfo, description);
		}
		
		response.getWriter().write(new Gson().toJson("{\"message\":\""+flag+"\"}"));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

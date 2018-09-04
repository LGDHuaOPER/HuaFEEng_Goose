package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.entity.Quotation;
import com.eoulu.entity.QuotationConfiguration;
import com.eoulu.service.QuotationService;
import com.eoulu.service.impl.QuotationServiceImpl;
import com.google.gson.Gson;

/**
 * ���۵������õĲ����ӿڣ����Զ����ý����޸ĺ�����
 */
@WebServlet("/QuotationConfOperate")
public class QuotationConfOperateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuotationConfOperateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String classify = request.getParameter("classify");
		QuotationService quotationService = new QuotationServiceImpl();
		boolean flag = false;
		QuotationConfiguration quotationConfiguration = new QuotationConfiguration();
		
		try{quotationConfiguration.setOrderID(Integer.parseInt(request.getParameter("OrderID")));}catch(Exception e){}
		try{quotationConfiguration.setNumber(Integer.parseInt(request.getParameter("Number")));}catch(Exception e){}
		try{quotationConfiguration.setLogisticsNumber(Integer.parseInt(request.getParameter("Number")));}catch(Exception e){}
		try{quotationConfiguration.setEquipmentModel(Integer.parseInt(request.getParameter("EquipmentModel")));}catch(Exception e){}
		try{quotationConfiguration.setRMBQuotes(Double.parseDouble(request.getParameter("RMBQuotes")));}catch(Exception e){}
		try{quotationConfiguration.setUSDQuotes(Double.parseDouble(request.getParameter("USDQuotes")));}catch(Exception e){}
		try{quotationConfiguration.setPaymentTerms(Integer.parseInt(request.getParameter("PaymentTerms")));}catch(Exception e){}
		try{quotationConfiguration.setPrice(Double.parseDouble(request.getParameter("Price")));}catch(Exception e){}
		//�޸ġ�ɾ��
		try{quotationConfiguration.setOrderInfoID(request.getParameter("OrderInfoID"));}catch(Exception e){}
		
		
		switch(classify){
		case "新增":flag = quotationService.insertQuotationConf(quotationConfiguration);break;
		case "删除":flag = quotationService.deleteQuotationConf(quotationConfiguration);break;
		case "修改":flag = quotationService.modifyQuotationConf(quotationConfiguration);break;
		}
		
		
		response.getWriter().write(new Gson().toJson("{message:"+flag+"}"));
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

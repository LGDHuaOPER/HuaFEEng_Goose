package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.entity.Quotation;
import com.eoulu.service.QuotationService;
import com.eoulu.service.impl.QuotationServiceImpl;
import com.google.gson.Gson;

/**
 * ���۵���Ϣ�Ĳ����ӿڣ������������޸ı��۵�����Ϣ
 */
@WebServlet("/QuotationOperate")
public class QuotationOperateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuotationOperateServlet() {
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
		Quotation quotation = new Quotation();
				
		//���챨�۵���
		try{String EmployeeNumber=request.getParameter("EmployeeNumber");
			String QuotationDate=request.getParameter("QuotationDate");//����20170717
			String array[]=QuotationDate.split("-");
			StringBuffer date=new StringBuffer(array[0]);
			for(int i=1;i<array.length;i++){
				date.append(String.format("%02d", Integer.parseInt(array[i])));
			}
			String QuotationID="QU"+EmployeeNumber+date+"001";
			quotation.setQuotationID(QuotationID);}catch(Exception e){}
		try{quotation.setContractType(request.getParameter("ContractType"));}catch(Exception e){}
		try{quotation.setQuotationDate(request.getParameter("QuotationDate"));}catch(Exception e){}
		//�汾����д���֣����ж�
		try{quotation.setVersion(Double.parseDouble(request.getParameter("Version")));}catch(Exception e){}
		try{quotation.setValid(request.getParameter("Valid"));}catch(Exception e){}
		try{quotation.setLeadTime(request.getParameter("LeadTime"));}catch(Exception e){}
		//��������д���֣����ж�
		try{quotation.setExchangeRate(Double.parseDouble(request.getParameter("ExchangeRate")));}catch(Exception e){}
		//��˰����д���֣����ж�
		try{quotation.setChargeDuty(Double.parseDouble(request.getParameter("ChargeDuty")));}catch(Exception e){}
		try{quotation.setPaymentTermID(Integer.parseInt(request.getParameter("paymentTerm")));}catch(Exception e){quotation.setPaymentTermID(12);}
		//����Order����Ϣ
		try{quotation.setCustomer(request.getParameter("Customer"));}catch(Exception e){}
		try{quotation.setContact(request.getParameter("Contact"));}catch(Exception e){}
		try{quotation.setContactInfo(request.getParameter("ContactInfo"));}catch(Exception e){}
		try{quotation.setSalesRepresentative(Integer.parseInt(request.getParameter("SalesRepresentative")));}catch(Exception e){}
		//�޸�ר��
		try{quotation.setID(Integer.parseInt(request.getParameter("ID")));}catch(Exception e){quotation.setID(0);}
		try{quotation.setQuotationID1(request.getParameter("QuotationID"));}catch(Exception e){quotation.setID(0);}
		try{quotation.setOrderID(Integer.parseInt(request.getParameter("OrderID")));}catch(Exception e){quotation.setID(0);}
		
		switch(classify){
		case "新增":flag = quotationService.insertQuotation(quotation);break;
		case "修改":flag = quotationService.modifyQuotation(quotation);break;
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

package com.eoulu.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.entity.PaymentRequest;
import com.eoulu.log.AccessStatistics;
import com.eoulu.service.PaymentRequestService;
import com.eoulu.service.impl.PaymentRequestServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class PaymentRequestServlet
 */
@WebServlet("/PaymentRequest")
public class PaymentRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaymentRequestServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loadType = request.getParameter("LoadType") == null?"":request.getParameter("LoadType");
		if(loadType.equals("data")){
			String currentPage = request.getParameter("CurrentPage");
			PaymentRequestService service = new PaymentRequestServiceImpl();
			Page page = new Page();
			page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
			page.setRows(10);
			page.setRecordCounts(service.getCounts());
			List<Map<String, Object>> list = service.getDataByPage(page);
			Map<String, Object> map = new HashMap<>();
			map.put("data", list);
			map.put("pageCount", page.getPageCounts());
			map.put("currentPage", currentPage);
			response.getWriter().write(new Gson().toJson(map));
		}else{
			new AccessStatistics().operateAccess(request, "付款申请");
			request.getRequestDispatcher("WEB-INF//PaymentRequest.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		String type = request.getParameter("Type");
		int ID = request.getParameter("ID")==null?0:Integer.parseInt(request.getParameter("ID"));
		 String Applicant = request.getParameter("Applicant");
		 String Department = request.getParameter("Department");
	     String ExpenseCategory = request.getParameter("ExpenseCategory");
		 double Amount = 0;
		 try{
			Amount = Double.parseDouble(request.getParameter("Amount").equals("")?"0":request.getParameter("Amount"));
		 }catch(NumberFormatException e){
			 response.getWriter().write("金额请填写为数字格式！");
		 }
	     String ExpenseItem = request.getParameter("ExpenseItem");
		 String ExpenseDetails = request.getParameter("ExpenseDetails");
	     String ApplicationDate = request.getParameter("ApplicationDate");
	     String PayDate = request.getParameter("PayDate");
		 String Attachment = request.getParameter("Attachment");
		 String Payee = request.getParameter("Payee");
		 String Account = request.getParameter("Account");
		 String DepositBank = request.getParameter("DepositBank");
		 String PaymentRemark = request.getParameter("PaymentRemark");
		 String StoreName = request.getParameter("StoreName");
		 String OrderNO = request.getParameter("OrderNO");
		 String Link = request.getParameter("Link");
		 String LinkRemark = request.getParameter("LinkRemark");

		 
		 PaymentRequest paymentRequest = new PaymentRequest();
		 paymentRequest.setID(ID);
		 paymentRequest.setApplicant(Applicant);
		 paymentRequest.setDepartment(Department);
		 paymentRequest.setExpenseCategory(ExpenseCategory);
		 paymentRequest.setAmount(Amount);
		 paymentRequest.setExpenseItem(ExpenseItem);
		 paymentRequest.setExpenseDetails(ExpenseDetails);
		 paymentRequest.setApplicationDate(ApplicationDate);
		 paymentRequest.setPayDate(PayDate);
		 paymentRequest.setAttachment(Attachment);
		 paymentRequest.setPayee(Payee);
		 paymentRequest.setAccount(Account);
		 paymentRequest.setDepositBank(DepositBank);
		 paymentRequest.setPaymentRemark(PaymentRemark);
		 paymentRequest.setStoreName(StoreName);
		 paymentRequest.setOrderNO(OrderNO);
		 paymentRequest.setLink(Link);
		 paymentRequest.setLinkRemark(LinkRemark);
		 
		 PaymentRequestService service = new PaymentRequestServiceImpl();
			
			
		 switch (type) {
		case "add":
			response.getWriter().write(service.insert(paymentRequest));
			
			break;

		case "update":
			response.getWriter().write(service.update(paymentRequest));
			break;
		}
	}

}

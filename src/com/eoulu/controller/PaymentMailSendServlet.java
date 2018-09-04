package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.entity.PaymentRequest;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.PaymentRequestService;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.PaymentRequestServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class PaymentMailSendServlet
 */
@WebServlet("/PaymentMailSend")
public class PaymentMailSendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaymentMailSendServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int ID = request.getParameter("ID")==null?0:Integer.parseInt(request.getParameter("ID"));
		PaymentRequestService service = new PaymentRequestServiceImpl();
		response.getWriter().write(new Gson().toJson(service.updatePayState(ID)));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int ID = request.getParameter("ID")==null?0:Integer.parseInt(request.getParameter("ID"));
		String Subject = request.getParameter("Subject");
		String ToList = request.getParameter("ToList");
		String CopyList = request.getParameter("CopyList");
		
		PaymentRequest paymentRequest = new PaymentRequest();
		paymentRequest.setID(ID);
		paymentRequest.setSubject(Subject);
		paymentRequest.setToList(ToList);
		paymentRequest.setCopyList(CopyList);
		
		PaymentRequestService service = new PaymentRequestServiceImpl();
		boolean result = service.sendMail(paymentRequest);
		if(result){
			LogInfoService service2 = new LogInfoServiceImpl();
			service2.insert(request, "付款申请","邮件-付款申请" );
		}
		response.getWriter().write(new Gson().toJson(result));
		
	}

}

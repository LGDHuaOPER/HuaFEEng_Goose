package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.PaymentRequestService;
import com.eoulu.service.impl.PaymentRequestServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class DeletePaymentAttachmentServlet
 */
@WebServlet("/DeletePaymentAttachment")
public class DeletePaymentAttachmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeletePaymentAttachmentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int ID = Integer.parseInt(request.getParameter("ID")==null?"0":request.getParameter("ID"));
		String fileName = request.getParameter("FileName");
		PaymentRequestService service = new PaymentRequestServiceImpl();
		response.getWriter().write(new Gson().toJson(service.deleteFile(ID, fileName)));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}

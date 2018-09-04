package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.BiddingDocumentService;
import com.eoulu.service.impl.BiddingDocumentServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class BiddingDocumentUploadServlet
 */
@WebServlet("/BiddingDocumentUpload")
public class BiddingDocumentUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BiddingDocumentUploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BiddingDocumentService service = new BiddingDocumentServiceImpl();
		response.getWriter().write(new Gson().toJson(service.batchUpload(request)));
	}

}

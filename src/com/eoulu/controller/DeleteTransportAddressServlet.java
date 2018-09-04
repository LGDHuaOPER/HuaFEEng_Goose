package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.LogInfoService;
import com.eoulu.service.TransportDirectiveService;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.TransportDirectiveServiceImpl;
import com.google.gson.Gson;
@WebServlet("/DeleteTransportAddress")
public class DeleteTransportAddressServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public DeleteTransportAddressServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		TransportDirectiveService service = new TransportDirectiveServiceImpl();
		boolean flag = service.deleteAddress(req);
		if(flag){
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "物流部-国内运输保险";
			String description = "删除";
			log.insert(req, JspInfo, description);
		}
		resp.getWriter().write(new Gson().toJson(flag));	
		}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
	

}

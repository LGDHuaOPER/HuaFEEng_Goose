package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.QuoteSystemService;
import com.eoulu.service.impl.QuoteSystemServiceImpl;
import com.google.gson.Gson;

import jdk.nashorn.internal.ir.RuntimeNode.Request;
@WebServlet("/GetStaffInfo")
public class GetStaffInfoServlet extends HttpServlet{


	private static final long serialVersionUID = 1L;
	public GetStaffInfoServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String department = req.getParameter("Department") == null?"":req.getParameter("Department");
		QuoteSystemService service = new QuoteSystemServiceImpl();
		String type = req.getParameter("Type");
		if(type.equals("common")){
			resp.getWriter().write(new Gson().toJson(service.getStaffInfo(department)));
		}else{
			String classify = req.getParameter("classify");
			String param = req.getParameter("param");
			resp.getWriter().write(new Gson().toJson(service.getStaffInfoByColumn(classify, param)));
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
	

}

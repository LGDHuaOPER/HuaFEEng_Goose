package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.PackingListService;
import com.eoulu.service.impl.PackingListServiceImpl;
import com.google.gson.Gson;
@WebServlet("/GetPackingOther")
public class GetPackingOtherServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public GetPackingOtherServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PackingListService service = new PackingListServiceImpl();
		int id = Integer.parseInt(req.getParameter("ID"));
		String contractNO = req.getParameter("PONO"); 
		resp.getWriter().write(new Gson().toJson(service.getOtherAll(id,contractNO)));
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

}

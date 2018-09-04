package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.LogInfoService;
import com.eoulu.service.QuoteSystemService;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.eoulu.service.impl.QuoteSystemServiceImpl;
import com.google.gson.Gson;
@WebServlet("/QuoteOtherDelete")
public class QuoteOtherDeleteServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public QuoteOtherDeleteServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QuoteSystemService service = new QuoteSystemServiceImpl();
		int id = Integer.parseInt(req.getParameter("ID"));
		boolean flag = service.deleteOtherTemp(id);
		if(flag){
			LogInfoService logs = new LogInfoServiceImpl();
			String JspInfo = "报价系统";
			String description = "删除-其他供应商PO的商品信息"+id;
			logs.insert(req, JspInfo, description);
		}
		resp.getWriter().write(new Gson().toJson(flag));
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
	

}
